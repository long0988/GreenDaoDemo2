package com.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.widget.MyWhiteBoard;

import butterknife.BindView;
import butterknife.OnClick;

public class WhiteBoardActivity extends BaseActivity {
    @BindView(R.id.undo)
    Button undo;
    @BindView(R.id.whiteboard)
    MyWhiteBoard whiteboard;
    @BindView(R.id.reundo)
    Button reundo;
    @BindView(R.id.scale)
    Button scale;
    @BindView(R.id.reduce)
    Button reduce;
    @BindView(R.id.red)
    Button red;
    @BindView(R.id.blue)
    Button blue;
    @BindView(R.id.green)
    Button green;
    @BindView(R.id.gray)
    Button gray;
    @BindView(R.id.eraser)
    Button eraser;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_white_board);
    }

    @OnClick(R.id.undo)
    public void onViewClicked() {
        whiteboard.undo();
    }


    @OnClick(R.id.reundo)
    public void onClick() {
        whiteboard.reundo();
    }

    @OnClick({R.id.scale, R.id.reduce, R.id.red, R.id.blue, R.id.green, R.id.gray,R.id.eraser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scale:
                whiteboard.scalePaintWidth();
                break;
            case R.id.reduce:
                whiteboard.reducePaintWidth();
                break;
            case R.id.red:
                whiteboard.resetPaintColor(getResources().getColor(R.color.red_pressed));
                break;
            case R.id.blue:
                whiteboard.resetPaintColor(getResources().getColor(R.color.blue));
                break;
            case R.id.green:
                whiteboard.resetPaintColor(getResources().getColor(R.color.green_pressed));
                break;
            case R.id.gray:
                whiteboard.resetPaintColor(getResources().getColor(R.color.gray));
                break;
            case R.id.eraser:
                whiteboard.eraser(Color.WHITE);
                break;
        }
    }
}
