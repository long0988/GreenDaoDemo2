package com.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.widget.MyWhiteBoard;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.pen)
    Button pen;
    @BindView(R.id.highlighter)
    Button highlighter;
    @BindView(R.id.loadimage)
    Button loadimage;
    @BindView(R.id.save)
    Button save;
    private int[] color;
    private int[] color2;
    private int currColor = 0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_white_board);
        color = new int[]{getResources().getColor(R.color.red_normal), getResources().getColor(R.color.blue)
                , getResources().getColor(R.color.green_pressed), getResources().getColor(R.color.gray)};
        color2 = new int[]{getResources().getColor(R.color.red_pressed), getResources().getColor(R.color.blue2)
                , getResources().getColor(R.color.green_pressed2), getResources().getColor(R.color.gray66)};
        whiteboard.setColorArr(color);
        whiteboard.setLighterColorArr(color2);

    }

    @OnClick(R.id.undo)
    public void onViewClicked() {
        whiteboard.undo();
    }


    @OnClick(R.id.reundo)
    public void onClick() {
        whiteboard.reundo();
    }

    @OnClick({R.id.scale, R.id.reduce, R.id.red, R.id.blue, R.id.green, R.id.gray, R.id.eraser, R.id.pen, R.id.highlighter,R.id.loadimage,R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.scale:
                whiteboard.scalePaintWidth();
                break;
            case R.id.reduce:
                whiteboard.reducePaintWidth();
                break;
            case R.id.red:
                currColor = 0;
                whiteboard.setCurrColor(0);
//                whiteboard.resetPaintColor(color[currColor]);
                break;
            case R.id.blue:
                currColor = 1;
                whiteboard.setCurrColor(1);
//                whiteboard.resetPaintColor(color[currColor]);
                break;
            case R.id.green:
                currColor = 2;
                whiteboard.setCurrColor(2);
//                whiteboard.resetPaintColor(color[currColor]);
                break;
            case R.id.gray:
                currColor = 3;
                whiteboard.setCurrColor(3);
//                whiteboard.resetPaintColor(color[currColor]);
                break;
            case R.id.eraser:
                whiteboard.eraser(Color.WHITE);
                break;
            case R.id.pen:
                whiteboard.setPen();
                break;
            case R.id.highlighter:
                whiteboard.setHighLighter();
                break;
            case R.id.loadimage:
                whiteboard.loadImage(BitmapFactory.decodeResource(getResources(),R.mipmap.banner));
                break;
            case R.id.save:
                whiteboard.saveImage(Environment.getExternalStorageDirectory().toString(), "whiteBoard",
                        Bitmap.CompressFormat.PNG, 100);
                break;
        }
    }

}
