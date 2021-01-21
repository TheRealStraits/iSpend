package com.aucegypt.ispend;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import java.util.Calendar;

import static java.lang.Math.abs;

public class EditEntryActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EntryItem editedItem;
    private int itemID = 0;
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
        setContentView(R.layout.edit_entry_activity);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.payMethod, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        EditText titleText = findViewById(R.id.editTitle);
        EditText valueText = findViewById(R.id.editValue);
        TextView dateText = findViewById(R.id.editDate);
        TextView timeText = findViewById(R.id.editTime);
        EditText notesText = findViewById(R.id.editNotes);
        EditText storeText = findViewById(R.id.editStore);

        Bundle extras = getIntent().getExtras();
        itemID = extras.getInt("entryID");

        EntryDBHelper entryDBHelper = new EntryDBHelper(getApplicationContext());
        editedItem = entryDBHelper.getEntryWithID(itemID);

        titleText.setText(editedItem.getTitle());
        dateText.setText(editedItem.getDate());
        timeText.setText(editedItem.getTime());
        notesText.setText(editedItem.getNotes());
        storeText.setText(editedItem.getStore());
        valueText.setText(String.valueOf(abs(editedItem.getValue())));

        ToggleButton toggleExp = findViewById(R.id.toggleExpSave);
        toggleExp.setGravity(Gravity.LEFT);
        toggleExp.setPadding(0, 0, 0, 0);

        Spinner spinner = findViewById(R.id.spinner);
        Button submitButton = findViewById(R.id.submitButton);
        Button cancelButton = findViewById(R.id.cancelButton);
        Button deleteButton = findViewById(R.id.deleteEntryButton);
        Button pickDate = findViewById(R.id.pickDateEdit);
        Button pickTime = findViewById(R.id.pickTimeEdit);

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

        if (editedItem.getValue() > 0)
            toggleExp.setChecked(true);
        else
            toggleExp.setChecked(false);

        //Expense/Save toggle listener
        toggleExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleExp.isChecked()) {
                    expSave = "exp";
                }
                else {
                    expSave = "save";
                }
            }
        });

        //Spinner adapter and listener
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                method = text;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "You need to select a payment method", Toast.LENGTH_LONG);
            }
        });

        //submit button listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleText.getText().toString();
                notes = notesText.getText().toString();
                value = valueText.getText().toString();
                date = dateText.getText().toString();
                time = timeText.getText().toString();
                store = storeText.getText().toString();

                editedItem = new EntryItem(title, notes, date, time, method, Double.parseDouble(value), expSave, store);
                entryDBHelper.updateEntryWithID(editedItem, itemID);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });

        //cancel button listener
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //delete button listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryDBHelper.deleteEntryWithID(itemID);
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

}
