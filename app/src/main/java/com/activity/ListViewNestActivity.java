package com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.adapter.MyListAdapter;
import com.greendaodemo2.R;
import com.widget.MyListView;
import com.widget.MyScrollView;

public class ListViewNestActivity extends BaseActivity {
    private MyListView listView;
    private MyScrollView scrollView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_listview_nest);
        scrollView = findViewById(R.id.sv);
        listView = findViewById(R.id.lv);
        listView.setAdapter(new MyListAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        scrollView.smoothScrollTo(0, 0);
    }
}
