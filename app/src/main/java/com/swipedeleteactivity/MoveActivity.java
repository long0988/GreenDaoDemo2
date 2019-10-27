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

public class MoveActivity extends SwipeBaseActivity {
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
                startActivity(new Intent(this, DragSwipeListActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, DragGridActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, DragTouchListActivity.class));
                break;
            }
        }
    }

    @Override
    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.touch_item));
    }
}
