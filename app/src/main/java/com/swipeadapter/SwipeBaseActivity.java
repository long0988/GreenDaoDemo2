package com.swipeadapter;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.greendaodemo2.MyAppLication;
import com.greendaodemo2.R;
import com.utils.CustomDensityUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by qlshi on 2018/9/21.
 */

public abstract class SwipeBaseActivity extends AppCompatActivity implements BGASwipeBackHelper.Delegate , SwipeItemClickListener {
    protected BGASwipeBackHelper mSwipeBackHelper;
    private Unbinder unBind;
    public Toolbar mToolbar;
    protected SwipeMenuRecyclerView mRecyclerView;
    public ActionBar mActionBar;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.ItemDecoration mItemDecoration;

    protected SwipeBaseAdapter mAdapter;
    protected List<String> mDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // BGASwipeBackHelper.init 来初始化滑动返回
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
        initData();
        initListener();
        MyAppLication.addActivity(this);
        CustomDensityUtil.setCustomDensity(this, this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unBind = ButterKnife.bind(this);
        setDefaultToolbar();
    }

    private void setDefaultToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            if (displayHomeAsUpEnabled()) {
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);
        if (mRecyclerView != null) {
            mLayoutManager = createLayoutManager();
            mItemDecoration = createItemDecoration();
            mDataList = createDataList();
            mAdapter = createAdapter();

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(mItemDecoration);
            mRecyclerView.setSwipeItemClickListener(this);
        }
    }

    protected boolean displayHomeAsUpEnabled() {
        return true;
    }

    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        //mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    protected void initView(Bundle savedInstanceState) {

    }

    protected void initData() {

    }

    protected void initListener() {

    }

    //是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    //正在滑动返回
    //slideOffset 从 0 到 1
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    //没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
    @Override
    public void onSwipeBackLayoutCancel() {

    }

    //滑动返回执行完毕，销毁当前 Activity
    @Override
    public void onSwipeBackLayoutExecuted() {
        try {
            mSwipeBackHelper.swipeBackward();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this);
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultItemDecoration(ContextCompat.getColor(this, R.color.divider_color));
    }

    protected List<String> createDataList() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add("第" + i + "个Item");
        }
        return dataList;
    }

    protected SwipeBaseAdapter createAdapter() {
        return new MainAdapter(this);
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBind.unbind();
        MyAppLication.clearActivity(this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
