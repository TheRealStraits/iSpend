package com.aucegypt.ispend;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EntryItem newItem;
    private String title = new String();
    private String date = new String();
    private String time = new String();
    private String notes = new String();
    private String expSave = "save";
    private String method = "cash";
    private String value;
    private String store= new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_entry_activity);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.payMethod, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        EditText titleText = findViewById(R.id.editTitle);
        EditText valueText = findViewById(R.id.editValue);
        TextView dateText = findViewById(R.id.editDate);
        TextView timeText = findViewById(R.id.editTime);
        EditText notesText = findViewById(R.id.editNotes);
        EditText storeText = findViewById(R.id.editStore);

        ToggleButton toggleExp = findViewById(R.id.toggleExpSave);
        toggleExp.setGravity(Gravity.LEFT);
        toggleExp.setPadding(0, 0, 0, 0);

        Spinner spinner = findViewById(R.id.spinner);
        Button addButton = findViewById(R.id.addButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button pickDate = findViewById(R.id.pickDate);
        Button pickTime = findViewById(R.id.pickTime);

        //pickTime click listener
        pickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "Time Picker");
            }
        });

        //pickDate click listener
        pickDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        //Expense/Save Toggle Button click listener
        toggleExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleExp.isChecked()) {
                    expSave = "exp";
                }
                else {
                    expSave = "save";
                }
                Log.d("toggle", expSave);
            }
        });

        //Payment method Spinner adapter and listener
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                method = text;
                Log.d("method", method);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "You need to select a payment method", Toast.LENGTH_LONG);
            }
        });

        //Add entry button listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleText.getText().toString();
                notes = notesText.getText().toString();
                value = valueText.getText().toString();
                date = dateText.getText().toString();
                time = timeText.getText().toString();
                store = storeText.getText().toString();

                //Parser goes here
                String dates = parseDate(date);

                EntryDBHelper entryDBHelper = new EntryDBHelper(getApplicationContext());
                newItem = new EntryItem(title, notes, date, time, method, Double.parseDouble(value), expSave, store, dates);
                entryDBHelper.addEntry(newItem);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

        //cancel entry button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = findViewById(R.id.editDate);
        textView.setText(currentDate);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        String currentTime = hourOfDay + ":" + minute;
        TextView textView = findViewById(R.id.editTime);
        textView.setText(currentTime);
    }

    //Parse Dates from android format to SQLite format
    private String parseDate(String date)
    {
        String mDay = date.substring(0, date.indexOf(','));
        String temp = date.substring(mDay.length() + 1);
        String mDate = temp.substring(0, temp.indexOf(','));
        String mYear = temp.substring(mDate.length() + 2);

        int here = 0;
        String result = mYear + "-";
        for (int i = 0; i < mDate.length(); i++)
            if (mDate.charAt(i) == ' ')
                here = i;

        String mMonth = mDate.substring(0, here);

        String mmMonth = new String();
        switch (mMonth)
        {
            case " January": mmMonth = "01";
                break;
            case " February": mmMonth = "02";
                break;
            case " March": mmMonth = "03";
                break;
            case " April": mmMonth = "04";
                break;
            case " May": mmMonth = "05";
                break;
            case " June": mmMonth = "06";
                break;
            case " July": mmMonth = "07";
                break;
            case " August": mmMonth = "08";
                break;
            case " September": mmMonth = "09";
                break;
            case " October": mmMonth = "10";
                break;
            case " November": mmMonth = "11";
                break;
            case " December": mmMonth = "12";
                break;
        }

        mDay = mDate.substring(mMonth.length() + 1);
        if (mDay.length() == 1)
            mDay = "0" + mDay;

        result = result + mmMonth + "-" + mDay;
        Log.d("day", result);

        return result;

    }
}
