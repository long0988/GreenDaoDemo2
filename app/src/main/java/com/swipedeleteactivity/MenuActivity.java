package com.swipedeleteactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.greendaodemo2.R;
import com.swipeadapter.SwipeBaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by qlshi on 2018/9/28.
 */

public class MenuActivity extends SwipeBaseActivity {
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
                startActivity(new Intent(this, ListActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, GridActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, ViewTypeActivity.class));
                break;
            }
            case 3: {
                startActivity(new Intent(this, VerticalActivity.class));
                break;
            }
            case 4: {
                startActivity(new Intent(this, DefineActivity.class));
                break;
            }
        }
    }

    @Override
    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.menu_item));
    }
}
