package com.mpchart;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.greendaodemo2.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by qlshi on 2018/10/16.
 */

public class BubbleChartActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.chart1)
    BubbleChart mChart;
    @BindView(R.id.seekBar2)
    SeekBar mSeekBarY;
    @BindView(R.id.seekBar1)
    SeekBar mSeekBarX;
    @BindView(R.id.tvXMax)
    TextView tvX;
    @BindView(R.id.tvYMax)
    TextView tvY;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_bubblechart);
        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        setSupportActionBar(mToolbar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mSeekBarX.setOnSeekBarChangeListener(this);

        mSeekBarY.setOnSeekBarChangeListener(this);

        mChart.getDescription().setEnabled(false);

        mChart.setOnChartValueSelectedListener(this);

        mChart.setDrawGridBackground(false);

        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        mChart.setMaxVisibleValueCount(200);
        mChart.setPinchZoom(true);

        mSeekBarX.setProgress(10);
        mSeekBarY.setProgress(50);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTypeface(mTfLight);

        YAxis yl = mChart.getAxisLeft();
        yl.setTypeface(mTfLight);
        yl.setSpaceTop(30f);
        yl.setSpaceBottom(30f);
        yl.setDrawZeroLine(false);

        mChart.getAxisRight().setEnabled(false);

        XAxis xl = mChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(mTfLight);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bubble, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet set : mChart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

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
            case R.id.actionTogglePinch: {
                if (mChart.isPinchZoomEnabled())
                    mChart.setPinchZoom(false);
                else
                    mChart.setPinchZoom(true);

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                mChart.setAutoScaleMinMaxEnabled(!mChart.isAutoScaleMinMaxEnabled());
                mChart.notifyDataSetChanged();
                break;
            }
            case R.id.actionSave: {
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
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
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        int count = mSeekBarX.getProgress();
        int range = mSeekBarY.getProgress();

        tvX.setText("" + count);
        tvY.setText("" + range);

        ArrayList<BubbleEntry> yVals1 = new ArrayList<BubbleEntry>();
        ArrayList<BubbleEntry> yVals2 = new ArrayList<BubbleEntry>();
        ArrayList<BubbleEntry> yVals3 = new ArrayList<BubbleEntry>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            float size = (float) (Math.random() * range);

            yVals1.add(new BubbleEntry(i, val, size, getResources().getDrawable(R.mipmap.star)));
        }

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            float size = (float) (Math.random() * range);

            yVals2.add(new BubbleEntry(i, val, size, getResources().getDrawable(R.mipmap.star)));
        }

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * range);
            float size = (float) (Math.random() * range);

            yVals3.add(new BubbleEntry(i, val, size));
        }

        // create a dataset and give it a type
        BubbleDataSet set1 = new BubbleDataSet(yVals1, "DS 1");
        set1.setDrawIcons(false);
        set1.setColor(ColorTemplate.COLORFUL_COLORS[0], 130);
        set1.setDrawValues(true);

        BubbleDataSet set2 = new BubbleDataSet(yVals2, "DS 2");
        set2.setDrawIcons(false);
        set2.setIconsOffset(new MPPointF(0, 15));
        set2.setColor(ColorTemplate.COLORFUL_COLORS[1], 130);
        set2.setDrawValues(true);

        BubbleDataSet set3 = new BubbleDataSet(yVals3, "DS 3");
        set3.setColor(ColorTemplate.COLORFUL_COLORS[2], 130);
        set3.setDrawValues(true);

        ArrayList<IBubbleDataSet> dataSets = new ArrayList<IBubbleDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);
        dataSets.add(set3);

        // create a data object with the datasets
        BubbleData data = new BubbleData(dataSets);
        data.setDrawValues(false);
        data.setValueTypeface(mTfLight);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.WHITE);
        data.setHighlightCircleWidth(1.5f);

        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.e("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }
}
