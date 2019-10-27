package com.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration{

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 6;
        outRect.right = 6;

        // Add top margin only for the first item to avoid double space between items
        if(parent.getChildAdapterPosition(view) != -1) {
            outRect.top = 24;
        }
}
}
