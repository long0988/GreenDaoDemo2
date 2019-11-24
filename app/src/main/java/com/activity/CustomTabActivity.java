package com.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.BaseActivity.BaseActivity;
import com.fragment.TabFragment;
import com.greendaodemo2.R;
import com.widget.CustomTabView;

public class CustomTabActivity extends BaseActivity implements CustomTabView.OnTabCheckListener{
    Fragment mFragmensts[] = new Fragment[4];
    private CustomTabView mCustomTabView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        mFragmensts[0] = TabFragment.newInstance("tab1");
        mFragmensts[1] = TabFragment.newInstance("tab2");
        mFragmensts[2] = TabFragment.newInstance("tab3");
        mFragmensts[3] = TabFragment.newInstance("tab4");
        initMyView();
    }

    private void initMyView() {
        mCustomTabView = (CustomTabView) findViewById(R.id.custom_tab_container);
        CustomTabView.Tab tabHome = new CustomTabView.Tab().setText("首页")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.icon_phone_unselect)
                .setPressedIcon(R.mipmap.icon_phone_select);
        mCustomTabView.addTab(tabHome);
        CustomTabView.Tab tabDis = new CustomTabView.Tab().setText("发现")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.icon_phone_unselect)
                .setPressedIcon(R.mipmap.icon_phone_select);
        mCustomTabView.addTab(tabDis);
        CustomTabView.Tab tabAttention = new CustomTabView.Tab().setText("管制")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.icon_phone_unselect)
                .setPressedIcon(R.mipmap.icon_phone_select);
        mCustomTabView.addTab(tabAttention);
        CustomTabView.Tab tabProfile = new CustomTabView.Tab().setText("我的")
                .setColor(getResources().getColor(android.R.color.darker_gray))
                .setCheckedColor(getResources().getColor(android.R.color.black))
                .setNormalIcon(R.mipmap.icon_phone_unselect)
                .setPressedIcon(R.mipmap.icon_phone_select);
        mCustomTabView.addTab(tabProfile);
        //设置监听
        mCustomTabView.setOnTabCheckListener(this);
        // 默认选中tab
        mCustomTabView.setCurrentItem(0);

    }

    @Override
    public void onTabSelected(View v, int position) {
        Log.e("zhouwei","position:"+position);
        onTabItemSelected(position);
    }

    private void onTabItemSelected(int position){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment  fragment1= manager.findFragmentByTag("tab1");
        Fragment  fragment2= manager.findFragmentByTag("tab2");
        Fragment  fragment3= manager.findFragmentByTag("tab3");
        Fragment  fragment4= manager.findFragmentByTag("tab4");
        if(fragment1!=null){
            transaction.hide(fragment1);
        }
        if(fragment2!=null){
            transaction.hide(fragment2);
        }
        if(fragment3!=null){
            transaction.hide(fragment3);
        }
        if(fragment4!=null){
            transaction.hide(fragment4);
        }
        switch (position){
            case 0:
                if(fragment1==null){
                    fragment1 = mFragmensts[0];
                    transaction.add(R.id.home_container,fragment1,"tab1");
                }else {
                    transaction.show(fragment1);
                }
                break;
            case 1:
                if(fragment2==null){
                    fragment2 = mFragmensts[1];
                    transaction.add(R.id.home_container,fragment2,"tab2");
                }else {
                    transaction.show(fragment2);
                }
                break;

            case 2:
                if(fragment3==null){
                    fragment3 = mFragmensts[2];
                    transaction.add(R.id.home_container,fragment3,"tab3");
                }else {
                    transaction.show(fragment3);
                }
                break;
            case 3:
                if(fragment4==null){
                    fragment4 = mFragmensts[3];
                    transaction.add(R.id.home_container,fragment4,"tab4");
                }else {
                    transaction.show(fragment4);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }
}
