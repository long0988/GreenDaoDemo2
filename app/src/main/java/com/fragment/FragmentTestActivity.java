package com.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

/**
 * Created by qlshi on 2018/9/21.
 */

public class FragmentTestActivity extends BaseActivity{
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_fragmenttest);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_activity_main,new TestFragment());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
