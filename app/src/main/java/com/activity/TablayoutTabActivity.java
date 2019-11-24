package com.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.utils.DataGenerator;

public class TablayoutTabActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private Fragment []mFragmensts;
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_tablayout_tab);
        mFragmensts = DataGenerator.getFragments();
        initView();

    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.bottom_tab_layout);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());

//                //改变Tab 状态
//                for(int i=0;i< mTabLayout.getTabCount();i++){
//                    if(i == tab.getPosition()){
//                        mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabResPressed[i]));
//                    }else{
//                        mTabLayout.getTabAt(i).setIcon(getResources().getDrawable(DataGenerator.mTabRes[i]));
//                    }
//                }
                // Tab 选中之后，改变各个Tab的状态
                for (int i=0;i<mTabLayout.getTabCount();i++){
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                    if(i == tab.getPosition()){ // 选中状态
                        icon.setImageResource(DataGenerator.mTabResPressed[i]);
                        text.setTextColor(getResources().getColor(android.R.color.black));
                    }else{// 未选中状态
                        icon.setImageResource(DataGenerator.mTabRes[i]);
                        text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 提供自定义的布局添加Tab
        for(int i=0;i<4;i++){
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(DataGenerator.getTabView(this,i)));
        }
        View customView = mTabLayout.getTabAt(0).getCustomView();
        customView.findViewById(R.id.red_dot).setVisibility(View.GONE);

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
//
//        if(fragment!=null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
//        }
}
