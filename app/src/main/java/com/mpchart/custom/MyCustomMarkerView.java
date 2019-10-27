package com.mpchart.custom;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.greendaodemo2.R;

import java.text.DecimalFormat;

/**
 * Created by qlshi on 2018/10/18.
 */

public class MyCustomMarkerView extends MarkerView {
    private TextView tvContent;
    private DecimalFormat format = new DecimalFormat("##0");

    public MyCustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tvContent = findViewById(R.id.tvContent);
    }

    //显示的内容
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText(format(e.getX())+"\n"+format.format(e.getY())+"辆");
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth()/2),-getHeight());
    }

    //时间格式化（显示今日往前30天的每一天日期）
    private String format(float x) {
        CharSequence format = DateFormat.format("MM月dd", System.currentTimeMillis() - (long) (30 - (int) x) * 24 * 60 * 60 * 1000);
        return format.toString();
    }
}
