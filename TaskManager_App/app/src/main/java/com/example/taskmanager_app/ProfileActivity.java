package com.example.taskmanager_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BarChart barChart=findViewById(R.id.barChart);
        ArrayList<BarEntry> taskDone=new ArrayList<>();
        taskDone.add(new BarEntry(1,4));
        taskDone.add(new BarEntry(2,9));
        taskDone.add(new BarEntry(3,5));
        taskDone.add(new BarEntry(4,6));
        taskDone.add(new BarEntry(5,2));
        taskDone.add(new BarEntry(6,8));
        taskDone.add(new BarEntry(7,3));

        ArrayList<String>xAxisLabel=new ArrayList<>();
        xAxisLabel.add("Mon");
        xAxisLabel.add("Mon");
        xAxisLabel.add("Tue");
        xAxisLabel.add("Wed");
        xAxisLabel.add("Thu");
        xAxisLabel.add("Fri");
        xAxisLabel.add("Sat");
        xAxisLabel.add("Sun");

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));

        BarDataSet barDataSet=new BarDataSet(taskDone,"taskDone");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData=new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar chart Example");
        barChart.animateY(3000);
    }
}