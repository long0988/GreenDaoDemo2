package com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entity.Contact;
import com.greendaodemo2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qlshi on 2018/7/18.
 */

public class ContactAdapter extends BaseAdapter {
    private List<Contact> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public ContactAdapter(List<Contact> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_contact_item, null);
            holder.mTv_letter = convertView.findViewById(R.id.tv_letter);
            holder.mTv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Contact contact = mList.get(position);
        holder.mTv_letter.setText(contact.getHeaderLetter());
        holder.mTv_name.setText(contact.getName());
        //一
        if (position == 0) {
            holder.mTv_letter.setVisibility(View.VISIBLE);
        } else {
            String headerLetter = mList.get(position - 1).getHeaderLetter();
            if (contact.getHeaderLetter().equals(headerLetter)) {
                holder.mTv_letter.setVisibility(View.GONE);
            } else {
                holder.mTv_letter.setVisibility(View.VISIBLE);
            }
        }
        //二
        //根据position获取分类的首字母的char ascii值
//        int section = getSectionForPosition(position);
//        if (position == getPositionForSection(section)) {
//            holder.mTv_letter.setVisibility(View.VISIBLE);
//        } else {
//            holder.mTv_letter.setVisibility(View.GONE);
//        }
        return convertView;
    }

    public void updateListView(ArrayList<Contact> search) {
        this.mList=search;
        notifyDataSetChanged();
    }

    public class ViewHolder {
        private TextView mTv_letter;
        private TextView mTv_name;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).getHeaderLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).getHeaderLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}
