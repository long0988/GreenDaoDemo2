package com.activity;

import android.os.Bundle;
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

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_white_board);
    }

    @OnClick(R.id.undo)
    public void onViewClicked() {
        whiteboard.undo();
    }
}
