package com.swipedeleteactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlshi on 2018/10/9.
 */

/**
 * 侧滑菜单和ViewPager嵌套，事先说好ViewPager的左右滑动会失效。
 */
public class ViewPagerActivity extends BaseActivity {
    private ActionBar mActionBar;
    private ViewPager mViewPager;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_menu_pager_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.btn_one).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btn_two).setOnClickListener(mBtnClickListener);
        findViewById(R.id.btn_three).setOnClickListener(mBtnClickListener);

        mViewPager = (ViewPager) findViewById(R.id.view_pager_menu);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mViewPager.setOffscreenPageLimit(2);

        List<Fragment> fragments = new ArrayList<>(3);
        fragments.add(Fragment.instantiate(this, MenuFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MenuFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MenuFragment.class.getName()));
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);

    }

    @Override
    protected void initData() {
        super.initData();
    }

    private ViewPager.SimpleOnPageChangeListener mPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            mActionBar.setSubtitle("第" + position + "个");
            switch (position) {
                case 0: {
                    mViewPager.setBackgroundColor(ContextCompat.getColor(ViewPagerActivity.this, R.color.colorAccent));
                    break;
                }
                case 1: {
                    mViewPager.setBackgroundColor(ContextCompat.getColor(ViewPagerActivity.this, R.color.colorPrimary));
                    break;
                }
                case 2: {
                    mViewPager.setBackgroundColor(ContextCompat.getColor(ViewPagerActivity.this, R.color.green_normal));
                    break;
                }
            }
        }
    };

    /**
     * Button点击监听。
     */
    private View.OnClickListener mBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_one) {
                mViewPager.setCurrentItem(0, true);
            } else if (v.getId() == R.id.btn_two) {
                mViewPager.setCurrentItem(1, true);
            } else if (v.getId() == R.id.btn_three) {
                mViewPager.setCurrentItem(2, true);
            }
        }
    };
    private class PagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> fragments;
        public PagerAdapter(FragmentManager fm,List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments == null ? 0 : fragments.size();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
