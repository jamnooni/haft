package com.example.me.haft;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class TrackPageFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.track_page,container,false);

        LineChart lineChart= (LineChart) rootView.findViewById(R.id.line_chart);

        List<Entry> lineEntries=new ArrayList<>();
        lineEntries.add(new Entry(0,1));
        lineEntries.add(new Entry(1,1));
        lineEntries.add(new Entry(2,0));
        lineEntries.add(new Entry(3,3));
        lineEntries.add(new Entry(4,2));
        lineEntries.add(new Entry(5,1));
        lineEntries.add(new Entry(6,4));

        initializeLineChart(lineChart,lineEntries);

        PieChart pieChart= (PieChart) rootView.findViewById(R.id.pie_chart);

        List<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(18.5f, "done"));
        pieEntries.add(new PieEntry(81.5f, "remaining"));

        PieDataSet dataSet = new PieDataSet(pieEntries, "progress");
        dataSet.setColors(new int[]{Color.parseColor("#FC4C25") ,Color.GRAY});
        dataSet.setDrawValues(false);

        PieData pieData = new PieData(dataSet);

        pieChart.setData(pieData);
        pieChart.invalidate(); // refresh
        pieChart.setDescription(null);
        pieChart.setHoleRadius(80f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setTouchEnabled(false);





        Legend legend=pieChart.getLegend();
        legend.setEnabled(false);







        return rootView;
    }

    private void initializeLineChart(LineChart lineChart, List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, "Label"); // add entries to dataset
        dataSet.setColor(Color.parseColor("#001f3f"));
        dataSet.setCircleColor(Color.parseColor("#001f3f"));
        //dataSet.setValueTextColor(...); // styling, ...
        dataSet.setDrawValues(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setCircleRadius(3f);


        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh
        lineChart.setLogEnabled(true);
        lineChart.setDescription(null);



        lineChart.setTouchEnabled(false);
/*        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(false);
        lineChart.setScaleYEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDragDecelerationEnabled(false);*/
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);

        Legend legend=lineChart.getLegend();
        legend.setEnabled(false);

        XAxis xAxis=lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(-0.5f);
        xAxis.setAxisMaximum(6.5f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        String[] values=new String[] {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        xAxis.setValueFormatter(new MyXAxisValueFormatter(values));
        xAxis.setLabelRotationAngle(30f);

        YAxis rightAxis=lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis yAxis=lineChart.getAxisLeft();
        yAxis.setDrawAxisLine(false);
        yAxis.setAxisMinimum(0f);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setGranularity(2f);
        yAxis.setAxisMaximum(6f);
        //TODO: if y>6 resetAxisMaximum
        yAxis.setGridLineWidth(.5f);
        yAxis.setMaxWidth(.6f);
        yAxis.setMinWidth(.6f);
    }


    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;

        public MyXAxisValueFormatter(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues[(int) value];
        }

        /** this is only needed if numbers are returned, else return 0 */
/*        @Override
        public int getDecimalDigits() { return 0; }*/
    }
}
