package com.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.adapter.MyWheelAdapter;
import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.greendaodemo2.R;
import com.hengyi.wheelpicker.listener.OnWheelClickedListener;
import com.wx.wheelview.adapter.SimpleWheelAdapter;
import com.wx.wheelview.common.WheelData;
import com.wx.wheelview.widget.WheelViewDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by qlshi on 2018/8/21.
 */

public class WheelViewActivity extends BaseActivity{
    private WheelView mWheelView;
    private com.hengyi.wheelpicker.weight.WheelView mNumber_Wheel;
    private com.wx.wheelview.widget.WheelView  mainWheelView, subWheelView, childWheelView, hourWheelView, minuteWheelView, secondWheelView,
            commonWheelView,
            simpleWheelView, myWheelView;
    private String TAG="WheelViewActivity";

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wheelview);
        mNumber_Wheel= (com.hengyi.wheelpicker.weight.WheelView) findViewById(R.id.Number_Wheel);
        String strs []= {"1","2","1","2","1","2","1","2","1","2","1","2"};
        int ints []= {1,2,3,4,5,6,7,8,9,0};
        mNumber_Wheel.setViewAdapter(new com.hengyi.wheelpicker.weight.adapters.ArrayWheelAdapter<String>(this,strs));
        mNumber_Wheel.addClickingListener(new OnWheelClickedListener() {
            @Override
            public void onItemClicked(com.hengyi.wheelpicker.weight.WheelView wheel, int itemIndex) {
                Toast.makeText(WheelViewActivity.this,wheel.getCurrentItem()+"-"+itemIndex,Toast.LENGTH_SHORT).show();
            }
        });
        mNumber_Wheel.setWheelBackground(R.drawable.contact_unselect_shape);
        //第二种滚轮
        mWheelView=(WheelView)findViewById(R.id.OtherWheel);
        mWheelView.setCyclic(false);
        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("item0");
        mOptionsItems.add("item1");
        mOptionsItems.add("item2");
        mWheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        mWheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                Toast.makeText(WheelViewActivity.this, "" + mOptionsItems.get(index), Toast.LENGTH_SHORT).show();
            }
        });
        //第三种滚轮
        inti();
    }

    public class ArrayWheelAdapter<T> implements WheelAdapter{
        private List<T> items;

        public ArrayWheelAdapter(List<T> items) {
            this.items = items;
        }

        @Override
        public int getItemsCount() {
            return this.items.size();
        }

        @Override
        public Object getItem(int index) {
            if(index>=0&&index<this.items.size()){
                return this.items.get(index);
            }
            return "";
        }

        @Override
        public int indexOf(Object o) {
            return items.indexOf(o);
        }
    }
    private void inti() {
        initWheel1();
        initWheel2();
        initWheel3();
    }

    private void initWheel1() {
        mainWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.main_wheelview);
        mainWheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(this));
        mainWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        mainWheelView.setWheelData(createMainDatas());
        com.wx.wheelview.widget.WheelView.WheelViewStyle style = new com.wx.wheelview.widget.WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        mainWheelView.setStyle(style);

        //联动
        subWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.sub_wheelview);
        subWheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(this));
        subWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        subWheelView.setWheelData(createSubDatas().get(createMainDatas().get(mainWheelView.getSelection())));
        subWheelView.setStyle(style);
        mainWheelView.join(subWheelView);
        mainWheelView.joinDatas(createSubDatas());

        childWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.child_wheelview);
        childWheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(this));
        childWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        childWheelView.setWheelData(createChildDatas().get(createSubDatas().get(createMainDatas().get(mainWheelView
                .getSelection())).get(subWheelView.getSelection())));
        childWheelView.setStyle(style);
        subWheelView.join(childWheelView);
        subWheelView.joinDatas(createChildDatas());
    }

    private void initWheel2() {
        hourWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.hour_wheelview);
        hourWheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(this));
        hourWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        hourWheelView.setWheelData(createHours());
        com.wx.wheelview.widget.WheelView.WheelViewStyle style = new com.wx.wheelview.widget.WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#0288ce");
        style.textColor = Color.GRAY;
        style.selectedTextSize = 20;
        hourWheelView.setStyle(style);
        hourWheelView.setExtraText("时", Color.parseColor("#0288ce"), 40, 70);

        minuteWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.minute_wheelview);
        minuteWheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(this));
        minuteWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        minuteWheelView.setWheelData(createMinutes());
        minuteWheelView.setStyle(style);
        minuteWheelView.setExtraText("分", Color.parseColor("#0288ce"), 40, 70);

        secondWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.second_wheelview);
        secondWheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(this));
        secondWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        secondWheelView.setWheelData(createMinutes());
        secondWheelView.setStyle(style);
        secondWheelView.setExtraText("秒", Color.parseColor("#0288ce"), 40, 70);
    }
    //Common 图文混排，自定义布局
    private void initWheel3() {
        commonWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.common_wheelview);
        commonWheelView.setWheelAdapter(new com.wx.wheelview.adapter.ArrayWheelAdapter(this));
        commonWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Common);
        commonWheelView.setWheelData(createArrays());


        simpleWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.simple_wheelview);
        simpleWheelView.setWheelAdapter(new SimpleWheelAdapter(this));
        simpleWheelView.setWheelSize(5);
        simpleWheelView.setWheelData(createDatas());
        simpleWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        simpleWheelView.setLoop(true);
        simpleWheelView.setWheelClickable(true);
        //点击事件
        simpleWheelView.setOnWheelItemClickListener(new com.wx.wheelview.widget.WheelView.OnWheelItemClickListener() {
            @Override
            public void onItemClick(int position, Object o) {
                Log.e(TAG,  "click:" + position);
            }
        });
        //选择事件
        simpleWheelView.setOnWheelItemSelectedListener(new com.wx.wheelview.widget.WheelView.OnWheelItemSelectedListener<WheelData>() {
            @Override
            public void onItemSelected(int position, WheelData data) {
                Log.e(TAG,  "click:" + position);
            }
        });

        myWheelView = (com.wx.wheelview.widget.WheelView) findViewById(R.id.my_wheelview);
        myWheelView.setWheelAdapter(new MyWheelAdapter(this));
        myWheelView.setWheelSize(5);
        myWheelView.setSkin(com.wx.wheelview.widget.WheelView.Skin.Holo);
        myWheelView.setWheelData(createArrays());
        myWheelView.setSelection(2);
        com.wx.wheelview.widget.WheelView.WheelViewStyle style = new com.wx.wheelview.widget.WheelView.WheelViewStyle();
        style.backgroundColor = Color.LTGRAY;
        style.textColor = Color.DKGRAY;
        style.selectedTextColor = Color.GREEN;
        myWheelView.setStyle(style);
    }
    public void showDialog(View view) {
        WheelViewDialog dialog = new WheelViewDialog(this);
        dialog.setTitle("wheelview dialog").setItems(createArrays()).setButtonText("确定").setDialogStyle(Color
                .parseColor("#6699ff")).setCount(5).show();
    }
    private List<String> createMainDatas() {
        String[] strings = {"黑龙江", "吉林", "辽宁"};
        return Arrays.asList(strings);
    }
    private HashMap<String, List<String>> createSubDatas() {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        String[] strings = {"黑龙江", "吉林", "辽宁"};
        String[] s1 = {"哈尔滨", "齐齐哈尔", "大庆"};
        String[] s2 = {"长春", "吉林"};
        String[] s3 = {"沈阳", "大连", "鞍山", "抚顺"};
        String[][] ss = {s1, s2, s3};
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }


    private HashMap<String, List<String>> createChildDatas() {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        String[] strings = {"哈尔滨", "齐齐哈尔", "大庆", "长春", "吉林", "沈阳", "大连", "鞍山", "抚顺"};
        String[] s1 = {"道里区", "道外区", "南岗区", "香坊区"};
        String[] s2 = {"龙沙区", "建华区", "铁锋区"};
        String[] s3 = {"红岗区", "大同区"};
        String[] s11 = {"南关区", "朝阳区"};
        String[] s12 = {"龙潭区"};
        String[] s21 = {"和平区", "皇姑区", "大东区", "铁西区"};
        String[] s22 = {"中山区", "金州区"};
        String[] s23 = {"铁东区", "铁西区"};
        String[] s24 = {"新抚区", "望花区", "顺城区"};
        String[][] ss = {s1, s2, s3, s11, s12, s21, s22, s23, s24};
        for (int i = 0; i < strings.length; i++) {
            map.put(strings[i], Arrays.asList(ss[i]));
        }
        return map;
    }
    private ArrayList<String> createHours() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }

    private ArrayList<String> createMinutes() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }

    private ArrayList<String> createArrays() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }
        return list;
    }

    private ArrayList<WheelData> createDatas() {
        ArrayList<WheelData> list = new ArrayList<WheelData>();
        WheelData item;
        for (int i = 0; i < 20; i++) {
            item = new WheelData();
            item.setId(R.mipmap.ic_loading);
            item.setName((i < 10) ? ("0" + i) : ("" + i));
            list.add(item);
        }
        return list;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
