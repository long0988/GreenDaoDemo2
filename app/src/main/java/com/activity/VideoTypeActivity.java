package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

public class VideoTypeActivity extends BaseActivity {
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_video_type);
    }

    public void goto1(View view) {
        startActivity(new Intent(this,VideoViewActivity.class));
    }

    public void goto2(View view) {
        startActivity(new Intent(this,SurfaceViewActivity.class));
    }

    public void goto3(View view) {
    }
}
