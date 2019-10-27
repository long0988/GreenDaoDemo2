package com.swipedeleteactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.greendaodemo2.R;
import com.swipeadapter.SwipeBaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by qlshi on 2018/10/8.
 */

public class RefreshLoadActivity extends SwipeBaseActivity {
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.actiivity_swipedelete);
    }

    @Override
    protected void initData() {
        super.initData();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(mDataList);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        switch (position) {
            case 0: {
                startActivity(new Intent(this, DefaultActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, CustomRefreshActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, CardViewActivity.class));
                break;
            }
            case 3: {
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            }
            case 4: {
                startActivity(new Intent(this, DrawerActivity.class));
                break;
            }
        }
    }

    @Override
    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.refresh_item));
    }
}
