/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.swipeadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.greendaodemo2.R;

import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class ContextLongMainAdapter extends SwipeBaseAdapter<ContextLongMainAdapter.ViewHolder> {

    private List<String> mDataList;

    public ContextLongMainAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<String> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_menu_main, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            itemView.setOnCreateContextMenuListener(this);
        }

        public void setData(String title) {
            this.tvTitle.setText(title);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,0,0,"删除");
            menu.add(0,1,0,"添加");
        }
    }

}
