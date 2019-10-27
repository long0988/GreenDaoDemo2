package com.mpchart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.mpchart.fragment.BarChartFrag;
import com.mpchart.fragment.ComplexityFragment;
import com.mpchart.fragment.PieChartFrag;
import com.mpchart.fragment.ScatterChartFrag;
import com.mpchart.fragment.SineCosineFragment;

/**
 * Created by qlshi on 2018/10/17.
 */

public class SimpleChartDemo extends BaseActivity {
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_awesomedesign);

        ViewPager pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        pager.setAdapter(pageAdapter);

    }

    private class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            Fragment f = null;

            switch (pos) {
                case 0:
                    f = SineCosineFragment.newInstance();
                    break;
                case 1:
                    f = ComplexityFragment.newInstance();
                    break;
                case 2:
                    f = BarChartFrag.newInstance();
                    break;
                case 3:
                    f = ScatterChartFrag.newInstance();
                    break;
                case 4:
                    f = PieChartFrag.newInstance();
                    break;
            }

            return f;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
