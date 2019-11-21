package com.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragment.TabFragment;
import com.greendaodemo2.R;

public class DataGenerator {
    public static final int []mTabRes = new int[]{R.mipmap.icon_phone_unselect,R.mipmap.icon_phone_unselect,R.mipmap.icon_phone_unselect,R.mipmap.icon_phone_unselect};
    public static final int []mTabResPressed = new int[]{R.mipmap.icon_phone_select,R.mipmap.icon_phone_select,R.mipmap.icon_phone_select,R.mipmap.icon_phone_select};
    public static final String []mTabTitle = new String[]{"首页","发现","关注","我的"};

    public static Fragment[] getFragments(){
        Fragment fragments[] = new Fragment[4];
        fragments[0] = TabFragment.newInstance("tab1");
        fragments[1] = TabFragment.newInstance("tab2");
        fragments[2] = TabFragment.newInstance("tab3");
        fragments[3] =TabFragment.newInstance("tab4");
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position){
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content,null);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(DataGenerator.mTabRes[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
