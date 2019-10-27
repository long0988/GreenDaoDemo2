package com.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.adapter.BannerHeaderAdapter;
import com.adapter.ContactAdapter2;
import com.decoration.LinearDecoration;
import com.entity.UserEntity;
import com.greendaodemo2.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * Created by qlshi on 2018/8/10.
 */

public class CityPickerActivity extends BaseActivity {
    private ImageView pic_contact_back;
    private IndexableLayout indexableLayout;
    private ContactAdapter2 mAdapter;
    private BannerHeaderAdapter mBannerHeaderAdapter;
    private String[] city = {"东莞", "深圳", "广州", "温州", "郑州", "金华", "佛山", "上海", "苏州", "杭州", "长沙", "中山"};

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pick_contact);
        initview();
        initAdapter();
        onlisten();
    }

    private void initAdapter() {
        mAdapter = new ContactAdapter2(this);
        indexableLayout.setAdapter(mAdapter);
        indexableLayout.setOverlayStyle_Center();
        mAdapter.setDatas(initDatas());
        //indexableLayout.setOverlayStyle_MaterialDesign(Color.RED);
        // 全字母排序。  排序规则设置为：每个字母都会进行比较排序；速度较慢
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        //indexableLayout.addHeaderAdapter(new SimpleHeaderAdapter<>(mAdapter, "☆",null, initDatas()));
        // 这里BannerView只有一个Item, 添加一个长度为1的任意List作为第三个参数
        List<String> bannerList = new ArrayList<>();
        bannerList.add("");
        mBannerHeaderAdapter = new BannerHeaderAdapter(this, "↑", null, bannerList, city);
        indexableLayout.addHeaderAdapter(mBannerHeaderAdapter);
    }

    private void initview() {
        pic_contact_back = (ImageView) findViewById(R.id.pic_contact_back);
        indexableLayout = (IndexableLayout) findViewById(R.id.indexableLayout);
        indexableLayout.setLayoutManager(new LinearLayoutManager(this));
        indexableLayout.getRecyclerView().addItemDecoration(new LinearDecoration(this,LinearLayoutManager.VERTICAL,R.color.gray1));
    }

    private void onlisten() {
        mAdapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<UserEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, UserEntity entity) {
                if (originalPosition >= 0) {
                    Toast.makeText(CityPickerActivity.this, entity.getNick(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBannerHeaderAdapter.setOnChangeCityListener(new BannerHeaderAdapter.OnChangeCityListener() {
            @Override
            public void OnChangeCity(String city) {
                Toast.makeText(CityPickerActivity.this, city, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<UserEntity> initDatas() {
        List<UserEntity> list = new ArrayList<>();
        // 初始化数据
        List<String> contactStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        List<String> mobileStrings = Arrays.asList(getResources().getStringArray(R.array.provinces));
        for (int i = 0; i < contactStrings.size(); i++) {
            UserEntity contactEntity = new UserEntity(contactStrings.get(i), mobileStrings.get(i));
            list.add(contactEntity);
        }
        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
