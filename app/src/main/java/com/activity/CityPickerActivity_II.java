package com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.hengyi.wheelpicker.listener.OnCityWheelComfirmListener;
import com.hengyi.wheelpicker.listener.OnWheelClickedListener;
import com.hengyi.wheelpicker.ppw.CityWheelPickerPopupWindow;
import com.hengyi.wheelpicker.weight.WheelView;
import com.hengyi.wheelpicker.weight.adapters.ArrayWheelAdapter;

/**
 * Created by qlshi on 2018/8/21.
 */

public class CityPickerActivity_II extends BaseActivity{
    private Button button;
    private WheelView mNumber_Wheel;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_city_address);
        button = (Button) findViewById(R.id.button);
        mNumber_Wheel= (WheelView) findViewById(R.id.Number_Wheel);
        String strs []= {"1","2","1","2","1","2","1","2","1","2","1","2"};
        int ints []= {1,2,3,4,5,6,7,8,9,0};
        mNumber_Wheel.setViewAdapter(new ArrayWheelAdapter<String>(this,strs));
        mNumber_Wheel.addClickingListener(new OnWheelClickedListener() {
            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                Toast.makeText(CityPickerActivity_II.this,wheel.getCurrentItem()+"-"+itemIndex,Toast.LENGTH_SHORT).show();
            }
        });
        mNumber_Wheel.setWheelForeground(R.color.white);
        final CityWheelPickerPopupWindow cityWheelPickerPopupWindow = new CityWheelPickerPopupWindow(this);
        cityWheelPickerPopupWindow.setListener(new OnCityWheelComfirmListener() {
            @Override
            public void onSelected(String Province, String City, String District, String PostCode) {
                Toast.makeText(CityPickerActivity_II.this,Province+"-"+City+"-"+District+"-"+PostCode,Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityWheelPickerPopupWindow.show();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
