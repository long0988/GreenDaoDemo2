package com.fileselectlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fileselectlibrary.adapter.PictureGridAdapter;


public class PictureSelectorActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private PictureGridAdapter mImageAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_selector);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
