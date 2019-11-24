package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

public class BottomTabsActivity extends BaseActivity {
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_bottom_tabs);
    }

    public void customTab(View view) {
        startActivity(new Intent(this,CustomTabActivity.class));
    }

    public void tablayFrag(View view) {
        startActivity(new Intent(this,TablayoutTabActivity.class));
    }

    public void bottomNatTAB(View view) {
        startActivity(new Intent(this,BottomNavTabActivity.class));
    }

    public void tablayoutVp(View view) {
        startActivity(new Intent(this,TablaayoutVpActivity.class));
    }
}
