package com.aucegypt.ispend;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class homeFragment extends Fragment {

    private ArrayList<EntryItem> entryList;
    private RecyclerView recyclerView;
    private entryRecyclerAdapter.entryRecyclerViewClickListener listener;
    String day = new String();
    String month = new String();
    String year = new String();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.entryRecyclerView);
        entryList = new ArrayList<>();

        EntryDBHelper entryDBHelper = new EntryDBHelper(view.getContext());
        entryList = entryDBHelper.getAllEntries();

        ArrayAdapter<CharSequence> spinnerDayAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinnerDay, android.R.layout.simple_spinner_item);
        spinnerDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> spinnerMonthAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinnerMonth, android.R.layout.simple_spinner_item);
        spinnerDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> spinnerYearAdapter = ArrayAdapter.createFromResource(view.getContext(), R.array.spinnerYear, android.R.layout.simple_spinner_item);
        spinnerDayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerDay = view.findViewById(R.id.spinnerDay);
        Spinner spinnerMonth = view.findViewById(R.id.spinnerMonth);
        Spinner spinnerYear = view.findViewById(R.id.spinnerYear);

        spinnerDay.setAdapter(spinnerDayAdapter);
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                day = text;
                Log.d("method", day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "You need to select a payment method", Toast.LENGTH_LONG);
            }
        });

        spinnerMonth.setAdapter(spinnerMonthAdapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                month = text;
                Log.d("method", month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "You need to select a payment method", Toast.LENGTH_LONG);
            }
        });

        spinnerYear.setAdapter(spinnerYearAdapter);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                year = text;
                Log.d("method", year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "You need to select a payment method", Toast.LENGTH_LONG);
            }
        });

        //Filter results button
        Button filter = view.findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String dateExtra = year + "-" + month + "-" + day;
                Log.d("try", dateExtra);
                entryList = entryDBHelper.getEntryWithDate(dateExtra);
                setAdapter(view);
            }
        });

        //Add receipt button
        Button addReceipt = view.findViewById(R.id.addReceipt);
        addReceipt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), OCRActivity.class));
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fabAddEntry);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddEntryActivity.class));
            }
        });

        //Used for debugging purposes
        /*
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        String test = "poopie";
        Log.d("poop", test.substring(3));
        Log.d("cal", Integer.toString(cal.get(Calendar.DAY_OF_MONTH)));
         */

        //filter click listener

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
