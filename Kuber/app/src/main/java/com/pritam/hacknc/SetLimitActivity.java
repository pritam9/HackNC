package com.pritam.hacknc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckedTextView;
import android.widget.SeekBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SetLimitActivity extends AppCompatActivity {
    BarChart barChart;
    SeekBar seekbar;
    BarDataSet set1;
    private static float columnIndex;
    private static float progressValue;
    CheckedTextView seekbarName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limit);

        //setValueSet();
        barChart = (BarChart) findViewById(R.id.setLimitChart);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        seekbarName = (CheckedTextView) findViewById(R.id.columnName);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progressValue=seekBar.getProgress();
                Log.d("STOPTRACKTOUCH","PROGESS CHANGE "+seekBar.getProgress());
                // if(isSelected)
                refreshMap();
            }
        });

        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setDrawGridLines(false);

        // setting data
        //mSeekBarX.setProgress(10);
        //mSeekBarY.setProgress(100);

        // add a nice and smooth animation
        barChart.animateY(2500);

        barChart.getLegend().setEnabled(false);
        setBarData();
    }

    private void refreshMap() {
        if (barChart.getData() != null &&
                barChart.getData().getDataSetCount() > 0) {


            ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
            set1 = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            List<BarEntry> yVals2=set1.getValues();
            //float changeInValue = progressValue-oldValue;
            //Log.d("CIV","ValuesChanged "+changeInValue);
            //getListofNewValues();
            int count=4;

            for (int i=0;i<yVals2.size();i++)
            {
                if(columnIndex==i) {
                    //yVals2.remove(i);
                    yVals1.add(new BarEntry(i,progressValue));
                    MainActivity.limitList.set(i, progressValue);
                    //yVals2.get(i).setY(progressValue);
                }
                else {
                    int oldSetValue = (int) yVals2.get(i).getY();

                        yVals1.add(new BarEntry(i,oldSetValue));

                   //Log.d("OLDSsetValue","C I V "+oldSetValue+" and new Value is "+newValue);
                }
                //count--;
            }
            set1.setValues(yVals1);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
            //isSelected=false;
        }
        barChart.invalidate();
    }


    //Setup Data
    private void setBarData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < 6; i++) {
            yVals1.add(new BarEntry(i, MainActivity.limitList.get(i)));
        }
        //BarDataSet set1;
        set1 = new BarDataSet(yVals1, "Data Set");
        set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        set1.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("Selected is - ",""+e.getX()+" and Y is - "+e.getY());
                seekbarName.setText(MainActivity.categoryList.get((int) e.getX())+"");
                seekbar.setProgress((int) e.getY());
                columnIndex= (int) e.getX();

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }
}
