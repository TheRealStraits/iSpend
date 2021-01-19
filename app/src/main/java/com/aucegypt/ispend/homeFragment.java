package com.aucegypt.ispend;

import android.content.Intent;
import android.os.Bundle;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.entryRecyclerView);
        entryList = new ArrayList<>();

        FloatingActionButton fab = view.findViewById(R.id.fabAddEntry);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddEntryActivity.class));
            }
        });

        setUserInfo();
        setAdapter(view);

        return view;
    }

    private void setUserInfo()
    {
        entryList.add(new EntryItem("Groceries", "Grocery shopping for the first week",
                "21 Jan, 2021 at 09:56 AM", "Cash", 25.62, "exp"));
        entryList.add(new EntryItem("Fuel", "Fuel refill for Mum's car",
                "21 Jan, 2021 at 11:50 AM", "Credit Card", 40.02, "save"));

    }

    private void setAdapter(View view)
    {
        entryRecyclerAdapter adapter = new entryRecyclerAdapter(entryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
