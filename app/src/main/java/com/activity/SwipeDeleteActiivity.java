package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.greendaodemo2.R;
import com.jaeger.library.StatusBarUtil;
import com.swipeadapter.SwipeBaseActivity;
import com.swipedeleteactivity.MenuActivity;
import com.swipedeleteactivity.MoveActivity;
import com.swipedeleteactivity.RefreshLoadActivity;
import com.swipedeleteactivity.StickyGroupActivity;
import com.utils.ToastUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by qlshi on 2018/9/27.
 */

public class SwipeDeleteActiivity extends SwipeBaseActivity {
    private FloatingActionButton btn;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.actiivity_swipedelete);
        btn = findViewById(R.id.context_btn);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(mDataList);
        //绑定上下文菜单（注意不是绑定监听器）
        registerForContextMenu(btn);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this,R.color.colorPrimary));
        StatusBarUtil.setTranslucent(this,100);
    }

    @Override
    protected boolean displayHomeAsUpEnabled() {
        return true;
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
    protected List<String> createDataList() {
        return Arrays.asList(getResources().getStringArray(R.array.main_item));
    }

    /*menu菜单*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    /*menu菜单事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
//        switch (itemId){
        ToastUtil.show(SwipeDeleteActiivity.this, "点击了" + itemId + "ss");
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    //上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        ToastUtil.show(SwipeDeleteActiivity.this, "点击了" + itemId + "ss");
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        switch (position) {
            case 0: {
                startActivity(new Intent(this, MenuActivity.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, MoveActivity.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, RefreshLoadActivity.class));
                break;
            }
            case 3: {
                startActivity(new Intent(this, StickyGroupActivity.class));
                break;
            }
        }
    }

}
