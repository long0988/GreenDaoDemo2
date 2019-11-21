package com.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;

            case 2:
                fragment = mFragmensts[2];
                break;
            case 3:
                fragment = mFragmensts[3];
                break;
        }
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
        }
    }
}
