package com.aucegypt.ispend;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MotionEvent;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.aucegypt.ispend.EntryDBHelper.*;

import static android.graphics.Color.red;

public class StatsActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private float initialX, ts, te;
    private ArrayList<EntryItem> entryListWeek;
    private ArrayList<EntryItem> entryListMonth;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        BottomNavigationView bottomNavigation = (BottomNavigationView) findViewById(R.id.bottomNav);
        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.statItem);
        }
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        final Boolean[] finance_type = {false};
        int count = 0;



        viewFlipper = (ViewFlipper) findViewById(R.id.myViewFlipper);
        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this, android.R.anim.fade_out);

        Button btn1 = (Button) findViewById(R.id.weekly);
        Button btn2 = (Button) findViewById(R.id.monthly);
        Button btn3 = (Button) findViewById(R.id.save_exp);

        TextView textView1 = (TextView) findViewById(R.id.type1);
        TextView textView2 = (TextView) findViewById(R.id.type2);
        TextView textView3 = (TextView) findViewById(R.id.type3);

        TextView expenseSum = (TextView) findViewById(R.id.expense_num);
        TextView savingSum = (TextView) findViewById(R.id.save_num);
        TextView netBalance = (TextView) findViewById(R.id.net_num);

        if(count == 0){
            barView(true,false);
            barView(true,true);
            lineView(true,false);
            //lineView(true,true);
            float[] sums = barView(true, finance_type[0]);
            expenseSum.setText("- $" +String.format("%.2f",sums[0]));
            savingSum.setText("+ $" +String.format("%.2f",sums[1]));
            if(sums[0]+sums[1]>0) {
                netBalance.setText("+ $" +String.format("%.2f",Math.abs(sums[1] - sums[0])));
                netBalance.setTextColor(Color.GREEN);
            }
            else if(sums[0]+sums[1]<0){
                netBalance.setText("- $" +String.format("%.2f",Math.abs(sums[1] - sums[0])));
                netBalance.setTextColor(Color.RED);
            }
            else{
                netBalance.setText("$0.0");
                netBalance.setTextColor(Color.DKGRAY);
            }
            textView1.setText("this week:");
            textView2.setText("this week:");
            textView3.setText("this week:");
            btn1.setTextColor(Color.DKGRAY);
            btn2.setTextColor(Color.WHITE);
            btn3.setText("E");
            btn3.setBackgroundColor(Color.parseColor("#D32424"));
            count++;
        }

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finance_type[0]) {
                    finance_type[0] = false;
                    btn3.setText("E");
                    btn3.setBackgroundColor(Color.parseColor("#D32424"));
                    Toast.makeText(StatsActivity.this, "Choose Monthly or Weekly Expenditures.", Toast.LENGTH_SHORT).show();
                }
                else{
                    finance_type[0] = true;
                    btn3.setText("S");
                    btn3.setBackgroundColor(Color.parseColor("#25FF75"));
                    Toast.makeText(StatsActivity.this, "Choose Monthly or Weekly Savings.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn1.setOnClickListener( new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                textView1.setText("this week:");
                textView2.setText("this week:");
                textView3.setText("this week:");
                btn1.setTextColor(Color.DKGRAY);
                btn2.setTextColor(Color.WHITE);
                lineView(true, finance_type[0]);
                barView(true, finance_type[0]);
                float[] sums = barView(true, finance_type[0]);
                expenseSum.setText("- $" +String.format("%.2f",sums[0]));
                savingSum.setText("+ $" +String.format("%.2f",sums[1]));
                if(sums[1]-sums[0] > 0) {
                    netBalance.setText("+ $" +String.format("%.2f",Math.abs(sums[1] - sums[0])));
                    netBalance.setTextColor(Color.GREEN);
                }
                else if(sums[1]-sums[0] < 0){
                    netBalance.setText("- $" +String.format("%.2f",Math.abs(sums[1] - sums[0])));
                    netBalance.setTextColor(Color.RED);
                }
                else{
                    netBalance.setText("$0.00");
                    netBalance.setTextColor(Color.DKGRAY);
                }

            }
        });

        btn2.setOnClickListener( new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                textView1.setText("this month:");
                textView2.setText("this month:");
                textView3.setText("this month:");
                btn2.setTextColor(Color.DKGRAY);
                btn1.setTextColor(Color.WHITE);
                lineView(false, finance_type[0]);
                barView(false, finance_type[0]);

                float[] sums = barView(false, finance_type[0]);
                expenseSum.setText("- $" +String.format("%.2f",sums[0]));
                savingSum.setText("+ $" +String.format("%.2f",sums[1]));
                if(sums[1]-sums[0] > 0) {
                    netBalance.setText("+ $" +String.format("%.2f",Math.abs(sums[1] - sums[0])));
                    netBalance.setTextColor(Color.GREEN);
                }
                else if(sums[1]-sums[0] < 0){
                    netBalance.setText("- $" +String.format("%.2f",Math.abs(sums[1] - sums[0])));
                    netBalance.setTextColor(Color.RED);
                }
                else{
                    netBalance.setText("$0.0");
                    netBalance.setTextColor(Color.DKGRAY);
                }
            }
        });


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if(item.getItemId()==R.id.homeItem){
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("Home", true);
                        startActivity(intent);
                        return true;
                    }
                    else if(item.getItemId()==R.id.settingsItem){
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra("Home", false);
                        startActivity(intent);
                        return true;
                    }
                    return false;
                }
            };

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = touchevent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float finalX = touchevent.getX();
                if (initialX > finalX) {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;
 /*viewFlipper.setInAnimation(this, R.anim.in_right);
 viewFlipper.setOutAnimation(this, R.anim.out_left);*/
                    viewFlipper.showNext();
                } else {
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;
 /*viewFlipper.setInAnimation(this, R.anim.in_left);
 viewFlipper.setOutAnimation(this, R.anim.out_right);*/
                    viewFlipper.showPrevious();
                }
                break;
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public float []barView (boolean MonthlyOrWeekly, boolean ExpenseOrSave){
        BarChart barChart = findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();

        float sum_expense = 0, sum_saving = 0;

        String[] weeklyExpenseString = getResources().getStringArray(R.array.weekly_expenses);
        String[] weeklySavingString = getResources().getStringArray(R.array.weekly_savings);
        Float[] weeklyExpense = Arrays.stream(weeklyExpenseString).map(Float::valueOf).toArray(Float[]::new);
        //Float[] weeklySaving = Arrays.stream(weeklySavingString).map(Float::valueOf).toArray(Float[]::new);
        //ArrayList<String> weeklySaving = new ArrayList<>();
        //double[] weeklySaving = {0,0,0,0,0,0,0};


        String[] monthlyExpenseString = getResources().getStringArray(R.array.monthly_expenses);
        String[] monthlySavingString = getResources().getStringArray(R.array.monthly_savings);
        Float[] monthlyExpense = Arrays.stream(monthlyExpenseString).map(Float::valueOf).toArray(Float[]::new);
        Float[] monthlySaving = Arrays.stream(monthlySavingString).map(Float::valueOf).toArray(Float[]::new);

        entryListWeek = new ArrayList<>();

        EntryDBHelper entryDBHelper = new EntryDBHelper(getApplicationContext());
        entryListWeek = entryDBHelper.getAllEntries();




        if(MonthlyOrWeekly){

            float[] saving4day = {0,0,0,0,0,0,0};
            float[] expense4day = {0,0,0,0,0,0,0};

            if(ExpenseOrSave){


                final ArrayList<String> xAxisLabel = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH);

                for(int i = 0; i < 7; i++) { // Loop 7 times, a time for each day
                    //cal.add(Calendar.DAY_OF_MONTH, -i);
                    for (int j = 0; j < entryListWeek.size(); j++) { // Go through each record
                        String mDay= entryListWeek.get(j).getDateForm(); // Day is equal to entire date of that record
                        mDay = mDay.substring(8); // Day is equal to day of that record
                        mDay = mDay.replace("-","");
                        int dof = dayOfWeek-i;
                        int m = Integer.parseInt(mDay);
                        if (m == dof) { // If Day is equal to (Today - i)
                            String v = entryListWeek.get(j).getExpSav();
                            if(v.equals("save")){ // If ExpSav Column of that record is equal to "save", If it is a saving
                                saving4day[i] += entryListWeek.get(j).getValue(); // Record that value in
                            }

                        }
                    }
                }

                for(int i = 0 ;i < 7; i++){
                    xAxisLabel.add(Integer.toString(dayOfWeek - i)); //Add (Today - i) to xAxis Label
                    //  Log.d("Saving For Day", String.valueOf(saving4day[i]));
                    entries.add(new BarEntry(i, saving4day[i], "Anything"));
                    sum_saving += saving4day[i];
                }
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
                ts = sum_saving;
            }
            else{


                final ArrayList<String> xAxisLabel = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH);

                for(int i = 0; i < 7; i++) { // Loop 7 times, a time for each day
                    //cal.add(Calendar.DAY_OF_MONTH, -i);
                    for (int j = 0; j < entryListWeek.size(); j++) { // Go through each record
                        String mDay= entryListWeek.get(j).getDateForm(); // Day is equal to entire date of that record
                        mDay = mDay.substring(8); // Day is equal to day of that record
                        mDay = mDay.replace("-","");
                        int dof = dayOfWeek-i;
                        int m = Integer.parseInt(mDay);
                        if (m == dof) { // If Day is equal to (Today - i)
                            String v = entryListWeek.get(j).getExpSav();
                            if(v.equals("exp")){ // If ExpSav Column of that record is equal to "save", If it is a saving
                                expense4day[i] += entryListWeek.get(j).getValue(); // Record that value in
                            }
                        }
                    }
                }

                for(int i = 0 ;i < 7; i++){
                    xAxisLabel.add(Integer.toString(dayOfWeek - i)); //Add (Today - i) to xAxis Label
                    //  Log.d("Saving For Day", String.valueOf(saving4day[i]));
                    entries.add(new BarEntry(i, expense4day[i], "Anything"));
                    sum_expense += expense4day[i];
                }
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
                te = sum_expense;
            }


            for(int i = 0 ;i < 7; i++) {
                sum_saving += saving4day[i];
                sum_expense += expense4day[i];
            }

            //sum_expense = expense4day[0] + expense4day[1] + expense4day[2] + expense4day[3] + expense4day[4] + expense4day[5] + expense4day[6];
            //sum_saving = saving4day[0] + saving4day[1] + saving4day[2] + saving4day[3] + saving4day[4] + saving4day[5] + saving4day[6];
        }
        else{
            if(ExpenseOrSave){
                entries.add(new BarEntry(0, monthlySaving[0], "Week 1"));
                entries.add(new BarEntry(2, monthlySaving[1], "Week 2"));
                entries.add(new BarEntry(4, monthlySaving[2], "Week 3"));
                entries.add(new BarEntry(6, monthlySaving[3], "Week 4"));
            }
            else{
                entries.add(new BarEntry(0, monthlyExpense[0], "Week 1"));
                entries.add(new BarEntry(2, monthlyExpense[1], "Week 2"));
                entries.add(new BarEntry(4, monthlyExpense[2], "Week 3"));
                entries.add(new BarEntry(6, monthlyExpense[3], "Week 4"));
            }

            final ArrayList<String> xAxisLabel = new ArrayList<>();
            xAxisLabel.add("1st Week");
            xAxisLabel.add(" ");
            xAxisLabel.add("2nd Week");
            xAxisLabel.add(" ");
            xAxisLabel.add("3rd Week");
            xAxisLabel.add(" ");
            xAxisLabel.add("4th Week");

            barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

            sum_expense = monthlyExpense[0] + monthlyExpense[1] + monthlyExpense[2] + monthlyExpense[3];
            sum_saving = monthlySaving[0] + monthlySaving[1] + monthlySaving[2] + monthlySaving[3];
        }

        BarDataSet barDataSet = new BarDataSet(entries, "Savings & Expenditures");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        if(MonthlyOrWeekly) {
            if(ExpenseOrSave){
                barDataSet.setColor(Color.parseColor("#25FF75"));
            }
            else{
                barDataSet.setColor(Color.parseColor("#D32424"));
            }
            barChart.getDescription().setText("Transactions over the past week.");
        }
        else{
            if(ExpenseOrSave){
                barDataSet.setColor(Color.parseColor("#25FF75"));
            }
            else{
                barDataSet.setColor(Color.parseColor("#D32424"));
            }
            barData.setBarWidth(1.4f);
            barChart.getDescription().setText("Transactions over the past month.");
        }
        barChart.animateY(2000);

        float[] sums_bar={0,0};

        if(MonthlyOrWeekly) {
            sums_bar[0] = te;
            sums_bar[1] = ts;
        }
        else{
            sums_bar[0] = sum_expense;
            sums_bar[1] = sum_saving;
        }

        return sums_bar;
    }
/*
    public void pieView(){

        PieChart pieChart = findViewById(R.id.piechart);

        ArrayList<PieEntry> entries = new ArrayList<>();


        entries.add(new PieEntry(2012, "700"));
        entries.add(new PieEntry(2013, "800"));
        entries.add(new PieEntry(2014, "900"));
        entries.add(new PieEntry(2015, "1000"));
        entries.add(new PieEntry(2016, "1100"));

        PieDataSet pieDataSet = new PieDataSet(entries, "Savings & Expenditures");

        PieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieDataSet.setValueTextColor(Color.BLACK);
        PieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Savings & Expenditures");
        pieChart.animate();
    }
    */


    @RequiresApi(api = Build.VERSION_CODES.N)
    public float[] lineView(boolean MonthlyOrWeekly, boolean ExpenseOrSave){

        LineChart lineChart = findViewById(R.id.linechart);

        lineChart.setDragEnabled(true);
        lineChart.setSaveEnabled(false);

        ArrayList<Entry> entries = new ArrayList<>();

        float sum_expense, sum_saving;

        String[] weeklyExpenseString = getResources().getStringArray(R.array.weekly_expenses);
        String[] weeklySavingString = getResources().getStringArray(R.array.weekly_savings);
        Float[] weeklyExpense = Arrays.stream(weeklyExpenseString).map(Float::valueOf).toArray(Float[]::new);
        Float[] weeklySaving = Arrays.stream(weeklySavingString).map(Float::valueOf).toArray(Float[]::new);

        String[] monthlyExpenseString = getResources().getStringArray(R.array.monthly_expenses);
        String[] monthlySavingString = getResources().getStringArray(R.array.monthly_savings);
        Float[] monthlyExpense = Arrays.stream(monthlyExpenseString).map(Float::valueOf).toArray(Float[]::new);
        Float[] monthlySaving = Arrays.stream(monthlySavingString).map(Float::valueOf).toArray(Float[]::new);


        if(MonthlyOrWeekly){
            if(ExpenseOrSave){
                entries.add(new BarEntry(1, weeklySaving[0], "Sunday"));
                entries.add(new BarEntry(2, weeklySaving[1], "Monday"));
                entries.add(new BarEntry(3, weeklySaving[2], "Tuesday"));
                entries.add(new BarEntry(4, weeklySaving[3], "Wednesday"));
                entries.add(new BarEntry(5, weeklySaving[4], "Thursday"));
                entries.add(new BarEntry(6, weeklySaving[5], "Friday"));
                entries.add(new BarEntry(7, weeklySaving[6], "Saturday"));
            }
            else{
                entries.add(new BarEntry(1, weeklyExpense[0], "Sunday"));
                entries.add(new BarEntry(2, weeklyExpense[1], "Monday"));
                entries.add(new BarEntry(3, weeklyExpense[2], "Tuesday"));
                entries.add(new BarEntry(4, weeklyExpense[3], "Wednesday"));
                entries.add(new BarEntry(5, weeklyExpense[4], "Thursday"));
                entries.add(new BarEntry(6, weeklyExpense[5], "Friday"));
                entries.add(new BarEntry(7, weeklyExpense[6], "Saturday"));
            }

            sum_expense = weeklyExpense[0] + weeklyExpense[1] + weeklyExpense[2] + weeklyExpense[3] + weeklyExpense[4] + weeklyExpense[5] + weeklyExpense[6];
            sum_saving = weeklySaving[0] + weeklySaving[1] + weeklySaving[2] + weeklySaving[3] + weeklySaving[4] + weeklySaving[5] + weeklySaving[6];
        }
        else{
            if(ExpenseOrSave){
                entries.add(new BarEntry(1, monthlySaving[0], "Week 1"));
                entries.add(new BarEntry(2, monthlySaving[1], "Week 2"));
                entries.add(new BarEntry(3, monthlySaving[2], "Week 3"));
                entries.add(new BarEntry(4, monthlySaving[3], "Week 4"));
            }
            else{
                entries.add(new BarEntry(1, monthlyExpense[0], "Week 1"));
                entries.add(new BarEntry(2, monthlyExpense[1], "Week 2"));
                entries.add(new BarEntry(3, monthlyExpense[2], "Week 3"));
                entries.add(new BarEntry(4, monthlyExpense[3], "Week 4"));
            }

            sum_expense = monthlyExpense[0] + monthlyExpense[1] + monthlyExpense[2] + monthlyExpense[3];
            sum_saving = monthlySaving[0] + monthlySaving[1] + monthlySaving[2] + monthlySaving[3];
        }


        LineDataSet lineDataSet = new LineDataSet(entries, "Savings & Expenditures");

        lineDataSet.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);

        lineChart.setData(data);

        float[] sums_bar = {sum_expense, sum_saving};

        return sums_bar;
    }


}