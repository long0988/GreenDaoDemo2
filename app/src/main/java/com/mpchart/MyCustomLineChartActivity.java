package com.mpchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.BaseActivity.BaseActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.greendaodemo2.R;
import com.mpchart.custom.MyCustomMarkerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import skin.support.SkinCompatManager;

/**
 * Created by qlshi on 2018/10/18.
 */

public class MyCustomLineChartActivity extends BaseActivity implements OnChartGestureListener , OnChartValueSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.chart)
    LineChart mChart;
    private ArrayList<Integer> mList;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_mycustomlinechart);
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            //1-10随机数
            //Random random = new Random();
            //int num = random.nextInt(10);
            int random = (int) (1 + Math.random() * 10);
            mList.add(random);
        }
        //初始化LineChart
        initLineChart();
        initChartData(mList);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        //换肤
        SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN); // 后缀加载
    }

    //初始化LineChart
    private void initLineChart() {
        //不显示背景
        mChart.setDrawGridBackground(false);
        //显示各个点边界
        mChart.setDrawBorders(false);
        //无描述文本/轴线
        mChart.getDescription().setEnabled(false);
        //启用触摸手势
        mChart.setTouchEnabled(true);
        // enable scaling and dragging 缩放和拖动
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        //如果禁用，则可以分别在x轴和y轴上进行缩放。 if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        //无数据显示
        mChart.setNoDataText("暂无数据");
        //X轴
        //得到X轴
        XAxis xAxis = mChart.getXAxis();
        //设置x轴的位置(默认上方)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //X轴的之间的最小间隔
        xAxis.setGranularity(1f);
        //设置X轴的刻度数量，第二个参数为true,将会画出明确数量（带有小数点），但是可能值导致不均匀，默认（6，false）
        xAxis.setLabelCount(mList.size() / 4, false);
        //设置X轴的值（最小值、最大值、然后会根据设置的刻度数量自动分配刻度显示）
        xAxis.setAxisMaximum(mList.size());
        xAxis.setAxisMinimum(0f);
        //不显示网格
        xAxis.setDrawGridLines(false);
        //刻度值倾斜度
        xAxis.setLabelRotationAngle(10);
        //设置X轴值为字符串
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axisBase) {
                Log.e("eeeeee", "X轴value=" + value);
                int IValue = (int) value;
                CharSequence format = DateFormat.format("MM/dd", System.currentTimeMillis() - (long) (mList.size() - IValue) * 24 * 60 * 60 * 1000);
                return format.toString();
            }
        });
        //Y轴
        //得到Y轴
        YAxis yAxis = mChart.getAxisLeft();
        YAxis rightYAxis = mChart.getAxisRight();
        //右侧Y轴不显示
        rightYAxis.setEnabled(false);
        //不显示网格线
        yAxis.setDrawGridLines(false);
        //设置Y轴坐标之间的最小间隔
        yAxis.setGranularity(1);
        //+2：最大值n就有n+1个刻度，在加上y轴多一个单位长度，为了好看，so+2
        yAxis.setLabelCount(Collections.max(mList) + 2, false);
        //设置从Y轴值
        yAxis.setAxisMinimum(0f);
        //+1:y轴多一个单位长度，为了好看
        yAxis.setAxisMaximum(Collections.max(mList) + 1);
        //y轴
        yAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axisBase) {
                Log.e("eeeeeeee", "Y轴值-" + value);
                int IValue = (int) value;
                return String.valueOf(IValue)+"辆";
            }
        });
    }

    private void initChartData(ArrayList<Integer> list) {
        //设置X、Y轴的数据
        ArrayList<Entry> entries = new ArrayList<>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            entries.add(new Entry(i, list.get(i), getResources().getDrawable(R.mipmap.star)));
        }
        ArrayList<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            //不受主题影响
            //ResourcesCompat.getDrawable(getResources(), R.mipmap.star, null);
           //受当前activity主题影响
            entries2.add(new Entry(i, list.get(i)+2, ContextCompat.getDrawable(this,R.mipmap.star)));
            //entries2.add(new Entry(i, list.get(i)+2, getResources().getDrawable(R.mipmap.star)));
        }
        //一个LineDataSet就是一条直线
        LineDataSet lineDataSet = new LineDataSet(entries, "车辆销售图");
        //线条颜色
        lineDataSet.setColor(Color.rgb(255, 241, 46));
        //线宽度
        lineDataSet.setLineWidth(1.5f);
        //是否画各点的圆
        lineDataSet.setDrawCircles(true);
        //各点是圆否画中空的圆心
        lineDataSet.setDrawCircleHole(true);
        //圆的颜色
        lineDataSet.setCircleColor(Color.BLACK);
        //圆的半径
        lineDataSet.setCircleRadius(3f);
        //值的大小
        lineDataSet.setValueTextSize(9f);
        //线条平滑
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(true);
        //线是虚线
        //lineDataSet.enableDashedLine(10f, 5f, 0f);
        //lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        LineData data = new LineData(lineDataSet);
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "车辆销售图");
        data.addDataSet(lineDataSet2);
        //图例：得到Lengend
        Legend legend = mChart.getLegend();
        //隐藏
        //legend.setEnabled(false);
        //隐藏描述
        Description description = new Description();
        description.setEnabled(false);
        mChart.setDescription(description);
        //折线图点的标记
        MyCustomMarkerView mv = new MyCustomMarkerView(this, R.layout.custom_marker_view);
        mChart.setMarker(mv);
        // 设置数据
        mChart.setData(data);
        // 图标刷新
        mChart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.actionToggleValues: {
                List<ILineDataSet> dataSets = mChart.getData().getDataSets();
                for (ILineDataSet dataSet : dataSets) {
                    dataSet.setDrawValues(!dataSet.isDrawValuesEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                List<ILineDataSet> dataSets = mChart.getData().getDataSets();
                for (ILineDataSet dataSet : dataSets) {
                    dataSet.setDrawIcons(!dataSet.isDrawIconsEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {
                List<ILineDataSet> dataSets = mChart.getData().getDataSets();
                for (ILineDataSet dataSet : dataSets) {
                    dataSet.setDrawFilled(!dataSet.isDrawFilledEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> dataSets = mChart.getData().getDataSets();
                for (ILineDataSet dataSet : dataSets) {
                    LineDataSet set = (LineDataSet) dataSet;
                    set.setDrawCircles(!set.isDrawCirclesEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCubic: {
                List<ILineDataSet> dataSets = mChart.getData().getDataSets();
                for (ILineDataSet dataSet : dataSets) {
                    LineDataSet set = (LineDataSet) dataSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.CUBIC_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.CUBIC_BEZIER);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                List<ILineDataSet> dataSets = mChart.getData().getDataSets();
                for (ILineDataSet dataSet : dataSets) {
                    LineDataSet set = (LineDataSet) dataSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.STEPPED);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHorizontalCubic: {
                List<ILineDataSet> dataSets = mChart.getData().getDataSets();
                for (ILineDataSet dataSet : dataSets) {
                    LineDataSet set = (LineDataSet) dataSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            : LineDataSet.Mode.HORIZONTAL_BEZIER);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHighlight: {
                if (mChart.getData() != null) {
                    mChart.getData().setHighlightEnabled(!mChart.getData().isHighlightEnabled());
                    mChart.invalidate();
                }
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000, Easing.EasingOption.EaseInCubic);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(3000, 3000);
                break;
            }
        }
        return true;
    }

    @Override
    public void onChartGestureStart(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent motionEvent, ChartTouchListener.ChartGesture chartGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent motionEvent) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent motionEvent) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent motionEvent) {

    }

    @Override
    public void onChartFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.e("eeeeeeee",v+"----onChartFling----"+v1);
    }

    @Override
    public void onChartScale(MotionEvent motionEvent, float v, float v1) {
        Log.e("eeeeeeee",v+"---onChartScale-----"+v1);
    }

    @Override
    public void onChartTranslate(MotionEvent motionEvent, float v, float v1) {
        Log.e("eeeeeeee",v+"----onChartTranslate---"+v1);
    }

    @Override
    public void onValueSelected(Entry entry, Highlight highlight) {
        Log.e("eeeeeeee",entry.getX()+"--entry--"+entry.getY());
    }

    @Override
    public void onNothingSelected() {

    }
}
