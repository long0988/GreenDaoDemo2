package com.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.adapter.MyRecyclerAdapter;
import com.greendaodemo2.R;

public class RecyclerNestActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private ScrollView scrollView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_nest_recycler);
        scrollView = (ScrollView) findViewById(R.id.sv);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        MyRecyclerAdapter adapter = new MyRecyclerAdapter();
        adapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
        scrollView.smoothScrollTo(0, 0);
    }
}
