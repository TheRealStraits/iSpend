package com.aucegypt.ispend;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class homeFragment extends Fragment {

    private ArrayList<EntryItem> entryList;
    private RecyclerView recyclerView;
    private entryRecyclerAdapter.entryRecyclerViewClickListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.entryRecyclerView);
        entryList = new ArrayList<>();

        EntryDBHelper entryDBHelper = new EntryDBHelper(view.getContext());
        entryList = entryDBHelper.getAllEntries();

        FloatingActionButton fab = view.findViewById(R.id.fabAddEntry);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddEntryActivity.class));
            }
        });

        setAdapter(view);

        return view;
    }

    private void setAdapter(View view)
    {
        setOnClickListener(view);
        entryRecyclerAdapter adapter = new entryRecyclerAdapter(entryList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener(View view) {
        listener = new entryRecyclerAdapter.entryRecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(view.getContext(), EditEntryActivity.class);
                int selectedId = entryList.get(position).getId();
                intent.putExtra("entryID", selectedId);
                startActivity(intent);
            }
        };
    }
}
