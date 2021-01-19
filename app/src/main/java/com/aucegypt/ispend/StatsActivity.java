package com.aucegypt.ispend;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MotionEvent;

import java.util.ArrayList;

import static android.graphics.Color.red;

public class StatsActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private float initialX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        lineView();
        barView();

        viewFlipper = (ViewFlipper) findViewById(R.id.myViewFlipper);
        viewFlipper.setInAnimation(this, android.R.anim.fade_in);
        viewFlipper.setOutAnimation(this, android.R.anim.fade_out);

        Button btn1 = (Button) findViewById(R.id.weekly);
        Button btn2 = (Button) findViewById(R.id.monthly);

        TextView textView1 = (TextView) findViewById(R.id.type1);
        TextView textView2 = (TextView) findViewById(R.id.type2);
        TextView textView3 = (TextView) findViewById(R.id.type3);

        btn1.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textView1.setText("weekly");
                textView2.setText("weekly");
                textView3.setText("weekly");
                btn1.setTextColor(Color.DKGRAY);
                btn2.setTextColor(Color.WHITE);

            }
        });

        btn2.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                textView1.setText("monthly");
                textView2.setText("monthly");
                textView3.setText("monthly");
                btn2.setTextColor(Color.DKGRAY);
                btn1.setTextColor(Color.WHITE);
            }
        });







    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    } */
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


    public void barView (){
        BarChart barChart = findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();

       /* String a, b;

        a = getActivity().getIntent().getExtras().getString("dummy_values_x");
        b = getActivity().getIntent().getExtras().getString("dummy_values_y");*/

        entries.add(new BarEntry(2012,567, "700"));
        entries.add(new BarEntry(2013,250, "800"));
        entries.add(new BarEntry(2014,540, "900"));
        entries.add(new BarEntry(2015,980, "1000"));
        entries.add(new BarEntry(2016,120, "1100"));

        BarDataSet barDataSet = new BarDataSet(entries, "Savings & Expenditures");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Transactions over the past 4 years.");
        barChart.animateY(2000);
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


    public void lineView(){

        LineChart lineChart = findViewById(R.id.linechart);

        lineChart.setDragEnabled(true);
        lineChart.setSaveEnabled(false);

        ArrayList<Entry> entries = new ArrayList<>();


        entries.add(new Entry(2012, 700));
        entries.add(new Entry(2013, 800));
        entries.add(new Entry(2014, 900));
        entries.add(new Entry(2015, 1000));
        entries.add(new Entry(2016, 1100));

        LineDataSet lineDataSet = new LineDataSet(entries, "Savings & Expenditures");

        lineDataSet.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData data = new LineData(dataSets);

        lineChart.setData(data);
    }


}