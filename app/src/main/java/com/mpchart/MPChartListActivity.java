package com.mpchart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by qlshi on 2018/10/12.
 */

public class MPChartListActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.listview)
    ListView mListview;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activitymplist_chart);
        ArrayList<ContentItem> objects = new ArrayList<ContentItem>();
        objects.add(new ContentItem("Line Chart", "直线图的简单演示:A simple demonstration of the linechart."));
        objects.add(new ContentItem("Line Chart-Dual YAxis", "双y轴直线图的演示:Demonstration of the linechart with dual y-axis."));
        objects.add(new ContentItem("Bar Chart", "条形图的简单演示:A simple demonstration of the bar chart."));
        objects.add(new ContentItem("Horizontal Bar Chart",
                "条形图的横向演示:A simple demonstration of the horizontal bar chart."));
        objects.add(new ContentItem("Combined Chart",
                "混合使用：Demonstrates how to create a combined chart (bar and line in this case)."));
        objects.add(new ContentItem("Pie Chart", "饼图演示:A simple demonstration of the pie chart."));
        objects.add(new ContentItem("Pie Chart with value lines", "饼图带注释折线:A simple demonstration of the pie chart with polyline notes."));
        objects.add(new ContentItem("Scatter Chart", "散射图演示:A simple demonstration of the scatter chart."));
        objects.add(new ContentItem("Bubble Chart", "气泡图：A simple demonstration of the bubble chart."));
        objects.add(new ContentItem("Stacked Bar Chart",
                "堆叠Bar图：A simple demonstration of a bar chart with stacked bars."));
        objects.add(new ContentItem("Stacked Bar Chart Negative",
                "bar堆叠正反：A simple demonstration of stacked bars with negative and positive values."));
        objects.add(new ContentItem("Another Bar Chart",
                "只显示底部值条形图：Implementation of a BarChart that only shows values at the bottom."));
        objects.add(new ContentItem("Multiple Lines Chart",
                "多条线性图：A line chart with multiple DataSet objects. One color per DataSet."));
        objects.add(new ContentItem("Multiple Bars Chart",
                "多条形图：A bar chart with multiple DataSet objects. One multiple colors per DataSet."));
        objects.add(new ContentItem(
                "Charts in ViewPager Fragments",
                "在Fragment中使用：Demonstration of charts inside ViewPager Fragments. In this example the focus was on the design and look and feel of the" +
                        " chart."));
        objects.add(new ContentItem(
                "Inverted Line Chart",
                "线性Y轴倒置：Demonstrates the feature of inverting the y-axis."));
        objects.add(new ContentItem(
                "Candle Stick Chart",
                "蜡烛/棍图Demonstrates usage of the CandleStickChart."));
        objects.add(new ContentItem(
                "Cubic Line Chart",
                "立体线：Demonstrates cubic lines in a LineChart."));
        objects.add(new ContentItem(
                "Radar Chart",
                "雷达/网图Demonstrates the use of a spider-web like (net) chart."));
        objects.add(new ContentItem(
                "Colored Line Chart",
                "line不同背景色跟颜色：Shows a LineChart with different background and line color."));
        objects.add(new ContentItem(
                "Realtime Chart",
                "实时添加线：This chart is fed with new data in realtime. It also restrains the view on the x-axis."));
        objects.add(new ContentItem(
                "动态添加线：Dynamical data adding",
                "This Activity demonstrates dynamical adding of Entries and DataSets (real time graph)."));
        objects.add(new ContentItem(
                "Sinus Bar Chart",
                "二次函数条形图：A Bar Chart plotting the sinus function with 8.000 values."));
        objects.add(new ContentItem(
                "BarChart positive / negative",
                "正负条形图：This demonstrates how to create a BarChart with positive and negative values in different colors."));
        objects.add(new ContentItem(
                "Time Chart",
                "时间图：Simple demonstration of a time-chart. This chart draws one line entry per hour originating from the current time in " +
                        "milliseconds."));
        objects.add(new ContentItem(
                "Filled LineChart",
                "画背景区域颜色：This demonstrates how to fill an area between two LineDataSets."));
        objects.add(new ContentItem(
                "Half PieChart",
                "半饼图：This demonstrates how to create a 180 degree PieChart."));
        objects.add(new ContentItem(
                "Custom LineChart",
                "自定义数据图"));
        MyMpAdapter myMpAdapter = new MyMpAdapter(this, objects);
        mListview.setAdapter(myMpAdapter);
        mListview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i;

        switch (position) {
            case 0:
                i = new Intent(this, LineChartActivity1.class);
                startActivity(i);
                break;
            case 1:
                i = new Intent(this, LineChartActivity2.class);
                startActivity(i);
                break;
            case 2:
                i = new Intent(this, BarChartActivity.class);
                startActivity(i);
                break;
            case 3:
                i = new Intent(this, HorizontalBarChartActivity.class);
                startActivity(i);
                break;
            case 4:
                i = new Intent(this, CombinedChartActivity.class);
                startActivity(i);
                break;
            case 5:
                i = new Intent(this, PieChartActivity.class);
                startActivity(i);
                break;
            case 6:
                i = new Intent(this, PiePolylineChartActivity.class);
                startActivity(i);
                break;
            case 7:
                i = new Intent(this, ScatterChartActivity.class);
                startActivity(i);
                break;
            case 8:
                i = new Intent(this, BubbleChartActivity.class);
                startActivity(i);
                break;
            case 9:
                i = new Intent(this, StackedBarActivity.class);
                startActivity(i);
                break;
            case 10:
                i = new Intent(this, StackedBarActivityNegative.class);
                startActivity(i);
                break;
            case 11:
                i = new Intent(this, AnotherBarActivity.class);
                startActivity(i);
                break;
            case 12:
                i = new Intent(this, MultiLineChartActivity.class);
                startActivity(i);
                break;
            case 13:
                i = new Intent(this, BarChartActivityMultiDataset.class);
                startActivity(i);
                break;
            case 14:
                i = new Intent(this, SimpleChartDemo.class);
                startActivity(i);
                break;
            case 15:
                i = new Intent(this, InvertedLineChartActivity.class);
                startActivity(i);
                break;
            case 16:
                i = new Intent(this, CandleStickChartActivity.class);
                startActivity(i);
                break;
            case 17:
                i = new Intent(this, CubicLineChartActivity.class);
                startActivity(i);
                break;
            case 18:
                i = new Intent(this, RadarChartActivity.class);
                startActivity(i);
                break;
            case 19:
                i = new Intent(this, LineChartActivityColored.class);
                startActivity(i);
                break;
            case 20:
                i = new Intent(this, RealtimeLineChartActivity.class);
                startActivity(i);
                break;
            case 21:
                i = new Intent(this, DynamicalAddingActivity.class);
                startActivity(i);
                break;
            case 22:
                i = new Intent(this, BarChartActivitySinus.class);
                startActivity(i);
                break;
            case 23:
                i = new Intent(this, BarChartPositiveNegative.class);
                startActivity(i);
                break;
            case 24:
                i = new Intent(this, LineChartTime.class);
                startActivity(i);
                break;
            case 25:
                i = new Intent(this, FilledLineActivity.class);
                startActivity(i);
                break;
            case 26:
                i = new Intent(this, HalfPieChartActivity.class);
                startActivity(i);
            case 27:
                i = new Intent(this, MyCustomLineChartActivity.class);
                startActivity(i);
                break;
        }
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
}
