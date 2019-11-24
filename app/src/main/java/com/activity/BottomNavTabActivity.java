package com.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.fragment.TabFragment;
import com.greendaodemo2.R;
import com.widget.MyViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BottomNavTabActivity extends BaseActivity {
    @BindView(R.id.vp)
    MyViewPager vp;
    @BindView(R.id.bnview)
    BottomNavigationView bnview;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_bottomnav_tab);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TabFragment.newInstance("tab1"));
        fragments.add(TabFragment.newInstance("tab2"));
        fragments.add(TabFragment.newInstance("tab3"));
        fragments.add(TabFragment.newInstance("tab4"));

        FragmentAdapter adapter = new FragmentAdapter(fragments, getSupportFragmentManager());
        vp.setAdapter(adapter);
        disableShiftMode(bnview);
        bnview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int menuId = item.getItemId();
                // 跳转指定页面：Fragment
                switch (menuId) {
                    case R.id.tab_one:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.tab_two:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.tab_three:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.tab_four:
                        vp.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //将滑动到的页面对应的 menu 设置为选中状态
                bnview.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //获取整个BottomNavigationView
        BottomNavigationMenuView bnv= (BottomNavigationMenuView) bnview.getChildAt(0);
        //设置红点点
        //根据角标获取tab
        View messageTab = bnv.getChildAt(2);
        BottomNavigationItemView itemView= (BottomNavigationItemView) messageTab;
        View badge = LayoutInflater.from(this).inflate(R.layout.bg_dot, bnv, false);
        itemView.addView(badge);
        TextView msgBadgeView=badge.findViewById(R.id.item_badge);
        msgBadgeView.setVisibility(View.VISIBLE);
        //
        View messageTab3 = bnv.getChildAt(3);
        BottomNavigationItemView itemView3= (BottomNavigationItemView) messageTab3;
        View badge3 = LayoutInflater.from(this).inflate(R.layout.bg_dot2, bnv, false);
        itemView3.addView(badge3);
        TextView msgBadgeView3=badge3.findViewById(R.id.item_badge);
        msgBadgeView3.setText("99+");
        msgBadgeView3.setWidth(57);
        msgBadgeView3.setHeight(57);
    }
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView navigationView) {

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);

            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public final class FragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public FragmentAdapter(List<Fragment> fragments, FragmentManager fm) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
