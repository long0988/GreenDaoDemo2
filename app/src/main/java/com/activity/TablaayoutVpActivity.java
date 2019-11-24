package com.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.adapter.FramentVPAdapter;
import com.fragment.TabFragment;
import com.greendaodemo2.R;
import com.utils.DataGenerator;
import com.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * tablay+viewpager
 */
public class TablaayoutVpActivity extends BaseActivity {
    @BindView(R.id.vp)
    MyViewPager vp;
    @BindView(R.id.bottom_tab_layout)
    TabLayout mTabLayout;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_tablayoutvp);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(TabFragment.newInstance("tab1"));
        fragments.add(TabFragment.newInstance("tab2"));
        fragments.add(TabFragment.newInstance("tab3"));
        fragments.add(TabFragment.newInstance("tab4"));
        FramentVPAdapter adapter = new FramentVPAdapter(fragments, getSupportFragmentManager());

        //给Tabs设置适配器
//        mTabLayout.setTabsFromPagerAdapter(adapter);
        vp.setAdapter(adapter);
        mTabLayout.setupWithViewPager(vp);
        //1111
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((ImageView) tab.getCustomView().findViewById(R.id.tab_content_image)).setImageDrawable(getResources().getDrawable(DataGenerator.mTabResPressed[tab.getPosition()]));
                ((TextView) tab.getCustomView().findViewById(R.id.tab_content_text)).setTextColor((getResources().getColor(android.R.color.black)));
                vp.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((ImageView) tab.getCustomView().findViewById(R.id.tab_content_image)).setImageDrawable(getResources().getDrawable(DataGenerator.mTabRes[tab.getPosition()]));
                ((TextView) tab.getCustomView().findViewById(R.id.tab_content_text)).setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        for (int i = 0; i < 4; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            //注意！！！这里就是添加我们自定义的布局
            tab.setCustomView(DataGenerator.getTabView(this,i));
            //这里是初始化时，默认item0被选中，setSelected（true）是为了给图片和文字设置选中效果，代码在文章最后贴出
            if (i == 0) {
                ImageView img =(ImageView) tab.getCustomView().findViewById(R.id.tab_content_image);
                img.setImageResource(DataGenerator.mTabResPressed[0]);//注意一开始设置的是setImageResource还是setImageDrawable
//                img.setImageDrawable(getResources().getDrawable(DataGenerator.mTabResPressed[0]));
                ((TextView) tab.getCustomView().findViewById(R.id.tab_content_text)).setTextColor(getResources().getColor(android.R.color.black));
            }
        }
        View customView = mTabLayout.getTabAt(0).getCustomView();
        customView.findViewById(R.id.red_dot).setVisibility(View.GONE);


          //222222
//        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                // Tab 选中之后，改变各个Tab的状态
//                for (int i=0;i<mTabLayout.getTabCount();i++){
//                    View view = mTabLayout.getTabAt(i).getCustomView();
//                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
//                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
//                    if(i == tab.getPosition()){ // 选中状态
//                        icon.setImageResource(DataGenerator.mTabResPressed[i]);
//                        text.setTextColor(getResources().getColor(android.R.color.black));
//                    }else{// 未选中状态
//                        icon.setImageResource(DataGenerator.mTabRes[i]);
//                        text.setTextColor(getResources().getColor(android.R.color.darker_gray));
//                    }
//                }
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
////         提供自定义的布局添加Tab
//        for(int i=0;i<4;i++){
//            mTabLayout.addTab(mTabLayout.newTab().setCustomView(DataGenerator.getTabView(this,i)));
//        }
//        View customView = mTabLayout.getTabAt(0).getCustomView();
//        customView.findViewById(R.id.red_dot).setVisibility(View.GONE);

    }
    //注意！！！这里就是我们自定义的布局tab_item
    public View getCustomView(int position){
        View view= LayoutInflater.from(this).inflate(R.layout.home_tab_content,null);
        ImageView iv= (ImageView) view.findViewById(R.id.tab_content_image);
        TextView tv= (TextView) view.findViewById(R.id.tab_content_text);
        switch (position){
            case 0:
                //drawable代码在文章最后贴出
                iv.setImageDrawable(this.getResources().getDrawable(DataGenerator.mTabRes[0]));
                tv.setText("首页");
                break;
            case 1:
                iv.setImageDrawable(this.getResources().getDrawable(DataGenerator.mTabRes[1]));
                tv.setText("理财");
                break;
            case 2:
                iv.setImageDrawable(this.getResources().getDrawable(DataGenerator.mTabRes[2]));
                tv.setText("生活");
                break;
            case 3:
                iv.setImageDrawable(this.getResources().getDrawable(DataGenerator.mTabRes[3]));
                tv.setText("我的");
                break;
        }
        return view;
    }
}
