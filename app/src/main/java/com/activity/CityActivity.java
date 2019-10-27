package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.CityListSelectActivity;
import com.lljjcoder.style.citylist.bean.CityInfoBean;
import com.lljjcoder.style.citylist.utils.CityListLoader;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.lljjcoder.style.citythreelist.CityBean;
import com.lljjcoder.style.citythreelist.ProvinceActivity;

/**
 * Created by qlshi on 2018/7/19.
 */

public class CityActivity extends BaseActivity {
    //申明对象
    CityPickerView mPicker = new CityPickerView();
    CityConfig cityConfig;
    private TextView mTv_info;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_city);
        mTv_info = (TextView) findViewById(R.id.tv_info);
        mPicker.init(this);
        /**
         * 预先加载三级列表显示省市区的数据
         */
        CityListLoader.getInstance().loadProData(this);
        /**
         * 预先加载一级列表显示 全国所有城市市的数据
         */
        CityListLoader.getInstance().loadCityData(this);
        //添加默认的配置，不需要自己定义
        cityConfig = new CityConfig.Builder()
                .title("城市选择")
                .province("广东省")
                .city("广州市")
                .district("天河区")
                .build();
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, com.lljjcoder.bean.CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                mTv_info.setText(province + "-" + city + "-" + district);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }
        });
    }

    public void showCity(View view) {
        mPicker.setConfig(cityConfig);
        //显示
        mPicker.showCityPicker();
    }

    //跳到城市列表
    public void showCityActivity(View view) {
        //城市列表
        Intent intent = new Intent(this, CityListSelectActivity.class);
        startActivityForResult(intent, CityListSelectActivity.CITY_SELECT_RESULT_FRAG);
    }

    //跳到省份
    public void showProvice(View view) {
        //跳转到省份列表
        Intent intent = new Intent(this, ProvinceActivity.class);
        startActivityForResult(intent, ProvinceActivity.RESULT_DATA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CityListSelectActivity.CITY_SELECT_RESULT_FRAG) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                Bundle bundle = data.getExtras();
                CityInfoBean cityinfo = (CityInfoBean) bundle.getParcelable("cityinfo");
                if (cityinfo != null) {
                    mTv_info.setText(cityinfo.getName() + "-" + cityinfo.getId());
                }

            }
        }
        if (requestCode == ProvinceActivity.RESULT_DATA) {
            if (resultCode == RESULT_OK) {
                if (data == null) return;
                //区域结果
                CityBean area = data.getParcelableExtra("area");
                //城市结果
                CityBean city = data.getParcelableExtra("city");
                //省份结果
                CityBean province = data.getParcelableExtra("province");
                mTv_info.setText(province.getName() + "-" + city.getName() + "-" + area.getName());
            }
        }
    }
}
