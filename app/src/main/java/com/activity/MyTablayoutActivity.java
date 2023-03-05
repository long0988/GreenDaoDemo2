package com.activity;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyTablayoutActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout1)
    TabLayout tabLayout1;
    @BindView(R.id.tab_layout2)
    TabLayout tabLayout2;
    @BindView(R.id.tab_layout3)
    TabLayout tabLayout3;
    @BindView(R.id.tab_layout4)
    TabLayout tabLayout4;
    @BindView(R.id.tab_layout5)
    TabLayout tabLayout5;
    @BindView(R.id.tab_layout6)
    TabLayout tabLayout6;
    @BindView(R.id.tab_layout7)
    TabLayout tabLayout7;
    @BindView(R.id.tab_layout8)
    TabLayout tabLayout8;
    @BindView(R.id.v_arrow_dot)
    View vArrowDot;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;

    private int defaultIndex = 0;
    private HashMap<String, Integer> companyMap = new HashMap();

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_my_tablayout);
    }

    @Override
    protected void initData() {
        companyMap.put("苹果", 2);
        companyMap.put("亚马逊", 0);
        companyMap.put("谷歌", 8);
        companyMap.put("微软", 0);
        companyMap.put("阿里巴巴", 0);
        companyMap.put("腾讯", 0);
        companyMap.put("脸书", 0);
        companyMap.put("思科", 0);
        initViewPager();
        initTabLayout1();//基础样式
        initTabLayout2();//添加icon & 去掉indicator
        initTabLayout3();//style 字体大小、加粗 & 顶部indicator
        initTabLayout4();//下划线样式 & tab分割线
        initTabLayout5();//Badge 数字 & 红点
        initTabLayout6();//TabLayout样式 & 选中字体加粗
        initTabLayout7();//获取隐藏tab
        initTabLayout8();//自定义item view & lottie
    }

    private void initTabLayout1() {
        //tabLayout关联viewpager
        tabLayout1.setupWithViewPager(mViewPager);
        //设置默认选中
        tabLayout1.getTabAt(0).isSelected();
    }

    private void initTabLayout2() {
        //去掉下划线indicator 设置高度为0即可 注意，单纯设置tabIndicatorColor为透明，其实不准确，默认还是有2dp的，根本瞒不过设计师的眼睛
        //tabLayout关联viewpager
        tabLayout2.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout2.getTabCount(); i++) {
            tabLayout2.getTabAt(i).setIcon(R.mipmap.ic_launcher);
        }
    }

    //style 字体大小、加粗 & 顶部indicator
    private void initTabLayout3() {
        tabLayout3.setupWithViewPager(mViewPager);
    }

    private void initTabLayout4() {
        //通过源码可以看到内部实现TabView继承至LinearLayout，我们知道LinearLayout是可以给子view设置分割线的，那我们就可以通过遍历来添加分割线
        tabLayout4.setupWithViewPager(mViewPager);

        //设置 分割线
        for (int index = 0; index < tabLayout4.getTabCount(); index++) {
            LinearLayout linearLayout = (LinearLayout) tabLayout4.getChildAt(index);
            if (linearLayout != null) {
                linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.shape_tab_divider));
                linearLayout.setDividerPadding(30);
            }

        }
    }

    private void initTabLayout5() {
        tabLayout5.setupWithViewPager(mViewPager);
        // 数字
        TabLayout.Tab tab = tabLayout5.getTabAt(defaultIndex);
//        tabLayout5.getTabAt(defaultIndex).getCustomView().setBackgroundColor(Color.RED);
//        tabLayout5.getTabAt(defaultIndex)?.let { tab ->
//                tab.orCreateBadge.apply {
//            backgroundColor = Color.RED
//            maxCharacterCount = 3
//            number = 99999
//            badgeTextColor = Color.WHITE
//        }
//        }
//
//        // 红点
//        tabLayout5.getTabAt(1).b;
//       tabLayout5.getTabAt(1)?.let { tab ->
//                tab.orCreateBadge.backgroundColor = ContextCompat.getColor(this, R.color.orange)
//        }
    }

    //TabLayout样式 & 选中字体加粗
    private void initTabLayout6() {
        tabLayout6.setupWithViewPager(mViewPager);
        tabLayout6.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchTextStyle(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switchTextStyle(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchTextStyle(TabLayout.Tab tab) {
        if (tab != null) {
            TextView textView = (TextView) tab.getCustomView();
            if (textView == null) return;
            if (tab.isSelected()) {
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            } else {
                textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }
        }
    }


    private void initTabLayout7() {
        Iterator<Map.Entry<String, Integer>> entries = companyMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, Integer> entry = entries.next();
            String key = entry.getKey();
            int value = entry.getValue();
            System.out.println(key + ":" + value);
        }
        for (Map.Entry<String, Integer> entry : companyMap.entrySet()) {
            String mapKey = entry.getKey();
            int mapValue = entry.getValue();
            TabLayout.Tab tab = tabLayout7.newTab();
            tab.setText(mapKey);
//            tab.orCreateBadge.apply {
//                backgroundColor = Color.RED
//                maxCharacterCount = 3
//                number = it.value
//                badgeTextColor = Color.WHITE
//                isVisible = it.value > 0
//            }
            hideToolTipText(tab);
            tabLayout7.addTab(tab);
        }
        // mBinding.tabLayout7.setOnScrollChangeListener() // min api 23 (6.0)
        // 适配 5.0  滑动过程中判断右侧小红点是否需要显示
        tabLayout7.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (isShowDot()) {
                    vArrowDot.setVisibility(View.VISIBLE);
                } else {
                    vArrowDot.setVisibility(View.GONE);
                }
            }
        });
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CompanyDialog(companyMap, tabLayout7.getSelectedTabPosition()).show(getSupportFragmentManager(), "CompanyDialog");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tabLayout7.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isShowDot()) {
                    vArrowDot.setVisibility(View.VISIBLE);
                } else {
                    vArrowDot.setVisibility(View.GONE);
                }
                tabLayout7.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private int lastShowIndex = 0;// 最后一个可见tab

    private Boolean isShowDot() {
        int showIndex = 0;
        int tipCount = 0;
        for (int index = 0; index < companyMap.size(); index++) {
            TabLayout.Tab tab = tabLayout7.getTabAt(index);
            if (tab == null) continue;
            LinearLayout tabView = (LinearLayout) tab.view;
            if (tabView == null) continue;
            Rect rect = new Rect();
            Boolean visible = tabView.getLocalVisibleRect(rect);
            // 可见范围小于80%也在计算范围之内，剩下20%宽度足够红点透出（可自定义）
            if (visible && rect.right > tabView.getWidth() * 0.8) {
                showIndex = index;
            } else {
                //if (index > showIndex) // 任意一个有count的tab隐藏就会显示，比如第一个在滑动过程中会隐藏，也在计算范围之内
                if (index > lastShowIndex) { // 只检测右侧隐藏且有count的tab 才在计算范围之内
//                    tab.badge?.let { tipCount += it.number }
                }
            }
        }
        lastShowIndex = showIndex;
        return tipCount > 0;
    }

    /**
     * 隐藏长按显示文本
     */
    private void hideToolTipText(TabLayout.Tab tab) {
        // 取消长按事件
//        tab.getCustomView().setLongClickable(false);
//        // api 26 以上 设置空text
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
//            tab.getCustomView().setTooltipText("");
//        }
    }

    private void initTabLayout8() {

    }
    /**
     * 自定义item view & lottie
     * implementation "com.airbnb.android:lottie:5.0.1"
     */
//    private fun initTabLayout8() {
//        val animMap = mapOf("party" to R.raw.anim_confetti, "pizza" to R.raw.anim_pizza, "apple" to R.raw.anim_apple)
//
//        animMap.keys.forEach { s ->
//                val tab = mBinding.tabLayout8.newTab()
//            val view = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
//            val imageView = view.findViewById<LottieAnimationView>(R.id.lav_tab_img)
//                    val textView = view.findViewById<TextView>(R.id.tv_tab_text)
//                    imageView.setAnimation(animMap[s]!!)
//            imageView.setColorFilter(Color.BLUE)
//            textView.text = s
//            tab.customView = view
//            mBinding.tabLayout8.addTab(tab)
//        }
//
//        val defaultTab = mBinding.tabLayout8.getTabAt(0)
//        defaultTab?.select()
//        defaultTab?.setSelected()
//
//        mBinding.tabLayout8.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                tab?.setSelected()
//                tab?.let { mBinding.viewPager.currentItem = it.position }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                tab?.setUnselected()
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//            }
//        })
//    }
//
//    /**
//     * 选中状态
//     */
//    fun TabLayout.Tab.setSelected() {
//        this.customView?.let {
//            val textView = it.findViewById<TextView>(R.id.tv_tab_text)
//                    val selectedColor = ContextCompat.getColor(this@TabLayoutActivity, R.color.colorPrimary)
//            textView.setTextColor(selectedColor)
//
//            val imageView = it.findViewById<LottieAnimationView>(R.id.lav_tab_img)
//            if (!imageView.isAnimating) {
//                imageView.playAnimation()
//            }
//            setLottieColor(imageView, true)
//        }
//    }
//
//    /**
//     * 未选中状态
//     */
//    fun TabLayout.Tab.setUnselected() {
//        this.customView?.let {
//            val textView = it.findViewById<TextView>(R.id.tv_tab_text)
//                    val unselectedColor = ContextCompat.getColor(this@TabLayoutActivity, R.color.black)
//            textView.setTextColor(unselectedColor)
//
//            val imageView = it.findViewById<LottieAnimationView>(R.id.lav_tab_img)
//            if (imageView.isAnimating) {
//                imageView.cancelAnimation()
//                imageView.progress = 0f // 还原初始状态
//            }
//            setLottieColor(imageView, false)
//        }
//    }
//
//    /**
//     * set lottie icon color
//     */
//    private fun setLottieColor(imageView: LottieAnimationView?, isSelected: Boolean) {
//        imageView?.let {
//            val color = if (isSelected) R.color.colorPrimary else R.color.black
//            val csl = AppCompatResources.getColorStateList(this@TabLayoutActivity, color)
//            val filter = SimpleColorFilter(csl.defaultColor)
//            val keyPath = KeyPath("**")
//            val callback = LottieValueCallback<ColorFilter>(filter)
//                    it.addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
//        }
//    }
    private void initViewPager() {
        //viewpager设置adapter
        mViewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initListener() {

    }

    private class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitles = {"Android", "Kotlin", "Flutter"};
        private List<Fragment> fragment = Arrays.asList(new Fragment[]{new Fragment(), new Fragment(), new Fragment()});

        public SimpleFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }
    }
}
