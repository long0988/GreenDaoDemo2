package com.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.contrarywind_bean.CardBean;
import com.contrarywind_bean.CityFourLevel;
import com.contrarywind_bean.JsonBean;
import com.contrarywind_bean.MyCityEntity;
import com.contrarywind_bean.ProvinceBean;
import com.fragment.FragmentTestActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greendaodemo2.R;
import com.popwindow.CustomPopWindow;
import com.utils.GetJsonDataUtil;
import com.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by qlshi on 2018/9/19.
 */

public class PickViewCityActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private Button btn_Options;
    private Button btn_CustomOptions;
    private Button btn_CustomTime;
    View viewPop;

    private ArrayList<CardBean> cardItem = new ArrayList<>();

    private ArrayList<String> food = new ArrayList<>();
    private ArrayList<String> clothes = new ArrayList<>();
    private ArrayList<String> computer = new ArrayList<>();
    private TimePickerView pvTime, pvCustomLunar, pvCustomTime;
    private OptionsPickerView pvOptions, pvCustomOptions, pvNoLinkOptions;
    private OptionsPickerView pvOptionsAddress, myCusOptionsAddress;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private MyHandler myHandler;
    private Dialog dialog;
    private Dialog dialog2;
    //    private PopupWindow popupWindow;
    private CustomPopWindow popupWindow;
    private Window window;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_pickviewcity);
        //等数据加载完毕再初始化并显示Picker,以免还未加载完数据就显示,造成APP崩溃。
        getOptionData();
        //公/农历切换
        initLunarPicker();
        //时间选择器
        initTimePicker();
        //条件选择
        initOptionPicker();
        //自定义时间选择
        initCustomTimePicker();
        //条件选择器自定义布局
        initCustomOptionPicker();
        //自定义不联动
        initNoLinkOptionsPicker();
        //三级地址选择器
        showAddressPickerView();
        //三级地址选择器数据格式2
        showAddressPickerView2();
        initView();
        window = this.getWindow();
    }

    //不联动的多级选项
    private void initNoLinkOptionsPicker() {
        pvNoLinkOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View view) {
                String str = "food:" + food.get(options1)
                        + "\nclothes:" + clothes.get(options2)
                        + "\ncomputer:" + computer.get(options3);

                Toast.makeText(PickViewCityActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        }).setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
            @Override
            public void onOptionsSelectChanged(int i, int i1, int i2) {
                LogUtils.logE("eeeeeee", i + "--" + i1 + "--" + i2);
            }
        }).setLayoutRes(R.layout.mycustom_pickview, new CustomListener() {
            @Override
            public void customLayout(View view) {
                final TextView tvSubmit = (TextView) view.findViewById(R.id.tv_finish);
                ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvNoLinkOptions.returnData();
                        pvNoLinkOptions.dismiss();
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvNoLinkOptions.dismiss();
                    }
                });
            }
        })
                .build();
        pvNoLinkOptions.setNPicker(food, clothes, computer);
        pvNoLinkOptions.setSelectOptions(0, 1, 1);
    }

    //条件选择器初始化，自定义布局
    private void initCustomOptionPicker() {
        /**
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View view) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                btn_CustomOptions.setText(tx);
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View view) {
                final TextView tvSubmit = (TextView) view.findViewById(R.id.tv_finish);
                final TextView tvAdd = (TextView) view.findViewById(R.id.tv_add);
                ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.dismiss();
                    }
                });

                tvAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getCardData();
                        pvCustomOptions.setPicker(cardItem);
                    }
                });
            }
        })
                .isDialog(true)
                .build();
        pvCustomOptions.setPicker(cardItem);//添加数据
    }

    private void initCustomTimePicker() {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectDate = Calendar.getInstance();
        Calendar stratDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        stratDate.set(2018, 8, 19);
        endDate.set(2099, 8, 19);
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                btn_CustomTime.setText(getTime(date));
            }
        }).setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
            @Override
            public void customLayout(View view) {
                final TextView tvSubmit = (TextView) view.findViewById(R.id.tv_finish);
                ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomTime.returnData();
                        pvCustomTime.dismiss();
                    }
                });
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomTime.dismiss();
                    }
                });
            }
        })
                .setTitleText("选择日期")
                .setDate(selectDate)
                .setRangDate(stratDate, endDate)
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, true, true, true})
                .setLabel("年", "月", "日", "时", "分", "秒")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
                        /*.setType(TimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentTextSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.animGravity(Gravity.RIGHT)// default is center*/

    }

    //条件选择器初始化
    private void initOptionPicker() {
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View view) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(options2)
                       /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                btn_Options.setText(tx);
            }
        }).setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.parseColor(getString(R.color.colorPrimary)))//背景色
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("省", "市", "区")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {//改变时触发
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        LogUtils.logE("eeeeee", "options1: " + options1 + "--options2: " + options2 + "--options3: " + options3);
                    }
                })
                .build();
        //        pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    //时间选择器
    private void initTimePicker() {
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(PickViewCityActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.e("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.e("pvTime", "onTimeSelectChanged" + date);
                    }
                })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(false) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .isCenterLabel(false)
                .build();
        //设置底部弹出
//        Dialog mDialog = pvTime.getDialog();
//        if (mDialog != null) {
//
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            pvTime.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
//                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
//            }
//        }
    }

    //公历农历切换
    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(2018, 9, 10);
        endDate.set(2069, 9, 10);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View view) {
                Toast.makeText(PickViewCityActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        }).setDate(selectedDate).setRangDate(startDate, endDate).setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {
            @Override
            public void customLayout(final View view) {
                final TextView tvSubmit = (TextView) view.findViewById(R.id.tv_finish);
                ImageView ivCancel = (ImageView) view.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomLunar.returnData();
                        pvCustomLunar.dismiss();
                    }
                });
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomLunar.dismiss();
                    }
                });
                //公农历切换
                CheckBox cb_lunar = (CheckBox) view.findViewById(R.id.cb_lunar);
                cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        pvCustomLunar.setLunarCalendar(!pvCustomLunar.isLunarCalendar());
                        //自适应宽
                        setTimePickerChildWeight(view, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
                    }
                });

            }

            /**
             * 公农历切换后调整宽
             * @param v
             * @param yearWeight
             * @param weight
             */
            private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                View year = timePicker.getChildAt(0);
                LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                lp.weight = yearWeight;
                year.setLayoutParams(lp);
                for (int i = 1; i < timePicker.getChildCount(); i++) {
                    View childAt = timePicker.getChildAt(i);
                    LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                    childLp.weight = weight;
                    childAt.setLayoutParams(childLp);
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED).build();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.e("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void initView() {
        Button btn_Time = (Button) findViewById(R.id.btn_Time);
        btn_Options = (Button) findViewById(R.id.btn_Options);
        btn_CustomOptions = (Button) findViewById(R.id.btn_CustomOptions);
        btn_CustomTime = (Button) findViewById(R.id.btn_CustomTime);
        Button btn_no_linkage = (Button) findViewById(R.id.btn_no_linkage);
        Button btn_to_Fragment = (Button) findViewById(R.id.btn_fragment);

        btn_Time.setOnClickListener(this);
        btn_Options.setOnClickListener(this);
        btn_CustomOptions.setOnClickListener(this);
        btn_CustomTime.setOnClickListener(this);
        btn_no_linkage.setOnClickListener(this);
        btn_to_Fragment.setOnClickListener(this);
        findViewById(R.id.btn_GotoJsonData).setOnClickListener(this);
        findViewById(R.id.btn_Goto_mycus).setOnClickListener(this);
        findViewById(R.id.btn_lunar).setOnClickListener(this);
        findViewById(R.id.btn_fourlevel).setOnClickListener(this);
        findViewById(R.id.btn_fourlevel2).setOnClickListener(this);
        findViewById(R.id.btn_fourlevel3).setOnClickListener(this);
    }

    private void getOptionData() {
        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        getCardData();
        getNoLinkData();

        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(1, "湖南", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(2, "广西", "描述部分", "其他数据"));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items_03.add("玉林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);
        myHandler = new MyHandler(this);
        myHandler.sendEmptyMessage(MSG_LOAD_DATA);
        //初始化省、市、县json数据
        initJsonData();
        //初始化我自定义的省市县数据
        initMyCustomJson();
        //四级地址选择器
        initFourJson();
        initFourJson2();
        //initFourJson3();

        /*--------数据源添加完毕---------*/
    }

    private void getCardData() {
        for (int i = 0; i < 5; i++) {
            cardItem.add(new CardBean(i, "No.ABC12345 " + i));
        }

        for (int i = 0; i < cardItem.size(); i++) {
            if (cardItem.get(i).getCardNo().length() > 6) {
                String str_item = cardItem.get(i).getCardNo().substring(0, 6) + "...";
                cardItem.get(i).setCardNo(str_item);
            }
        }
    }

    private void getNoLinkData() {
        food.add("KFC");
        food.add("MacDonald");
        food.add("Pizza hut");

        clothes.add("Nike");
        clothes.add("Adidas");
        clothes.add("Armani");

        computer.add("ASUS");
        computer.add("Lenovo");
        computer.add("Apple");
        computer.add("HP");
    }

    private ArrayList<JsonBean> optionsProvince = new ArrayList<>();
    private ArrayList<ArrayList<String>> optionsCity = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> optionsArea = new ArrayList<>();

    private void initJsonData() {
        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = GetJsonDataUtil.getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        optionsProvince = jsonBean;
        int size = jsonBean.size();
        if (size < 1) {
            return;
        }
        for (int i = 0; i < size; i++) {
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> areaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                List<String> area = jsonBean.get(i).getCity().get(c).getArea();
                if (area == null || area.size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(area);
                }
                areaList.add(City_AreaList);//添加该省所有地区数据
            }
            optionsCity.add(CityList);
            optionsArea.add(areaList);
        }

    }

    private void showAddressPickerView() {// 弹出地址选择器

        pvOptionsAddress = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = optionsProvince.get(options1).getPickerViewText() +
                        optionsCity.get(options1).get(options2) +
                        optionsArea.get(options1).get(options2).get(options3);

                Toast.makeText(PickViewCityActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptionsAddress.setPicker(optionsProvince, optionsCity, optionsArea);//三级选择器
    }

    private ArrayList<JsonBean> parseData(String result) {
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            Gson gson = new Gson();
            detail = gson.fromJson(result, new TypeToken<List<JsonBean>>() {
            }.getType());
//            JSONArray data = new JSONArray(result);
//            for (int i = 0; i < data.length(); i++) {
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                detail.add(entity);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private ArrayList<MyCityEntity> optionsProvince2 = new ArrayList<>();
    private ArrayList<ArrayList<String>> optionsCity2 = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> optionsArea2 = new ArrayList<>();

    //第二种省市县格式
    private void initMyCustomJson() {
        String jsonData = GetJsonDataUtil.getJson(this, "my_province.json");//获取assets目录下的json文件数据
        ArrayList<MyCityEntity> myCityBean = myParseData(jsonData);
        int size = myCityBean.size();
        if (size < 1) {
            return;
        }
        optionsProvince2 = myCityBean;
        for (int i = 0; i < size; i++) {
            ArrayList<String> cityList = new ArrayList<>();//第二级，存放省的城市
            ArrayList<ArrayList<String>> arenList = new ArrayList<>();//第三级，存放省的城市
            int sizeChildren = myCityBean.get(i).getChildren().size();
            for (int j = 0; j < sizeChildren; j++) {
                MyCityEntity.city cityChildren = myCityBean.get(i).getChildren().get(j);
                String name = cityChildren.getName();
                cityList.add(name);
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                int thirdSize = cityChildren.getChildren().size();
                for (int k = 0; k < thirdSize; k++) {
                    String arenName = cityChildren.getChildren().get(k).getName();
                    if (arenName == null || arenName == "") {
                        City_AreaList.add("其他");
                    } else {
                        City_AreaList.add(arenName);
                    }
                }
                arenList.add(City_AreaList);
            }
            optionsCity2.add(cityList);
            optionsArea2.add(arenList);
        }
        myHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }

    private void showAddressPickerView2() {// 弹出地址选择器

        myCusOptionsAddress = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = optionsProvince2.get(options1).getPickerViewText() +
                        optionsCity2.get(options1).get(options2) +
                        optionsArea2.get(options1).get(options2).get(options3);

                Toast.makeText(PickViewCityActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        myCusOptionsAddress.setPicker(optionsProvince2, optionsCity2, optionsArea2);//三级选择器
    }

    private ArrayList<MyCityEntity> myParseData(String result) {
        ArrayList<MyCityEntity> detail = new ArrayList<>();
        try {
            Gson gson = new Gson();
            detail = gson.fromJson(result, new TypeToken<List<MyCityEntity>>() {
            }.getType());
//            JSONArray data = new JSONArray(result);
//            for (int i = 0; i < data.length(); i++) {
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                detail.add(entity);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private ArrayList<CityFourLevel> parsefourData(String result) {
        ArrayList<CityFourLevel> detail = new ArrayList<>();
        try {
            Gson gson = new Gson();
            detail = gson.fromJson(result, new TypeToken<List<CityFourLevel>>() {
            }.getType());
//            JSONArray data = new JSONArray(result);
//            for (int i = 0; i < data.length(); i++) {
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                detail.add(entity);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    //四级地址
    private ArrayList<CityFourLevel> optionsProvince3 = new ArrayList<>();
    private ArrayList<ArrayList<String>> optionsCity3 = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> optionsArea3 = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<ArrayList<String>>>> optionsStreet3 = new ArrayList<>();
    int op1Index = 0, op2Index = 0, op3Index = 0;

    private void initFourJson() {
        String jsonData = GetJsonDataUtil.getJson(this, "province_four.json");//获取assets目录下的json文件数据
        ArrayList<CityFourLevel> cityBean = parsefourData(jsonData);
        int size = cityBean.size();
        if (size < 1) {
            return;
        }
        optionsProvince3 = cityBean;
        for (int i = 0; i < size; i++) {
            ArrayList<String> cityList = new ArrayList<>();//第二级，存放省的所有城市
            ArrayList<ArrayList<String>> arenList = new ArrayList<>();//第三级，存放城市所有县、区
            ArrayList<ArrayList<ArrayList<String>>> streetList = new ArrayList<>();//第四级，存放县、市下的镇、街道
            int sizeChildren = cityBean.get(i).getChildren().size();
            for (int j = 0; j < sizeChildren; j++) {
                CityFourLevel.City city = cityBean.get(i).getChildren().get(j);
                String name = city.getName();
                cityList.add(name);
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                ArrayList<ArrayList<String>> streetAreaList1 = new ArrayList<>();//该城市的所有地区的街道列表
                int thirdSize = city.getChildren().size();
                for (int k = 0; k < thirdSize; k++) {
                    CityFourLevel.City.Area area = city.getChildren().get(k);
                    String arenName = area.getName();
                    if (arenName == null || arenName == "") {
                        City_AreaList.add("其他");
                    } else {
                        City_AreaList.add(arenName);
                    }
                    List<CityFourLevel.City.Area.Street> children = area.getChildren();
                    int fourSize = children.size();
                    ArrayList<String> streetAreaList2 = new ArrayList<>();//该城市的所有地区的街道列表
                    for (int m = 0; m < fourSize; m++) {
                        String streetName = area.getChildren().get(m).getName();
                        streetAreaList2.add(streetName);
                    }
                    streetAreaList1.add(streetAreaList2);
                }
                streetList.add(streetAreaList1);
                arenList.add(City_AreaList);
            }
            optionsCity3.add(cityList);
            optionsArea3.add(arenList);
            optionsStreet3.add(streetList);
        }
        View view = getLayoutInflater().inflate(R.layout.four_address_item, null);
        final WheelView op1 = (WheelView) view.findViewById(R.id.options1);
        final WheelView op2 = (WheelView) view.findViewById(R.id.options2);
        final WheelView op3 = (WheelView) view.findViewById(R.id.options3);
        final WheelView op4 = (WheelView) view.findViewById(R.id.options4);
        initWheelAdater(op1, optionsProvince3);
        //op2.setAdapter(new ArrayWheelAdapter(optionsCity3.get(0)));
        initWheelAdater(op2, optionsCity3.get(0));
        //op3.setAdapter(new ArrayWheelAdapter(optionsArea3.get(0).get(0)));
        initWheelAdater(op3, optionsArea3.get(0).get(0));
        initWheelAdater(op4, optionsStreet3.get(0).get(0).get(0));
        op1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                op1Index = index;
                initWheelAdater(op2,optionsCity3.get(index));
                initWheelAdater(op3,optionsArea3.get(index).get(0));
                initWheelAdater(op4,optionsStreet3.get(index).get(0).get(0));
            }
        });
        op2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                op2Index = index;
                initWheelAdater(op3,optionsArea3.get(op1Index).get(index));
                initWheelAdater(op4,optionsStreet3.get(op1Index).get(index).get(0));
            }
        });
        op3.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                op3Index = index;
                initWheelAdater(op4,optionsStreet3.get(op1Index).get(op2Index).get(index));
            }
        });
        op4.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
            }
        });
        dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.show();

    }

    //四级地址
    private ArrayList<CityFourLevel> optionsProvince4 = new ArrayList<>();
    private ArrayList<List<CityFourLevel.City>> optionsCity4 = new ArrayList<>();
    private ArrayList<List<CityFourLevel.City.Area>> optionsArea4 = new ArrayList<>();
    private ArrayList<List<CityFourLevel.City.Area.Street>> optionsStreet4 = new ArrayList<>();
    int index1 = 0, index2 = 0, index3 = 0, index4 = 0;

    private void initFourJson2() {
        String jsonData = GetJsonDataUtil.getJson(this, "province_four.json");//获取assets目录下的json文件数据
        ArrayList<CityFourLevel> cityBean = parsefourData(jsonData);
        int size = cityBean.size();
        if (size < 1) {
            return;
        }
        optionsProvince4 = cityBean;
        View view = getLayoutInflater().inflate(R.layout.four_address_item, null);
        WheelView op1 = (WheelView) view.findViewById(R.id.options1);
        final WheelView op2 = (WheelView) view.findViewById(R.id.options2);
        final WheelView op3 = (WheelView) view.findViewById(R.id.options3);
        final WheelView op4 = (WheelView) view.findViewById(R.id.options4);
        initWheelAdater(op1, optionsProvince4);
        op1.setDividerColor(R.color.colorPrimary);
        op1.setBackgroundColor(R.color.colorPrimary);
        op1.setCyclic(false);
        //op2.setAdapter(new ArrayWheelAdapter(optionsProvince4.get(0).getChildren()));
        initWheelAdater(op2, optionsProvince4.get(0).getChildren());
        //op2.setTextSize(18);
        op2.setTextColorCenter(R.color.colorAccent);
        op2.setTextColorOut(R.color.gray);
        op2.setCyclic(false);
        initWheelAdater(op3, optionsProvince4.get(0).getChildren().get(0).getChildren());
        op3.setTextXOffset(40);
        op3.setCyclic(false);
        initWheelAdater(op4, optionsProvince4.get(0).getChildren().get(0).getChildren().get(0).getChildren());
        op4.setCyclic(false);
        op1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index1 = index;
                initWheelAdater(op2, optionsProvince4.get(index).getChildren());
                initWheelAdater(op3, optionsProvince4.get(index).getChildren().get(0).getChildren());
                initWheelAdater(op4, optionsProvince4.get(index).getChildren().get(0).getChildren().get(0).getChildren());
            }
        });
        op2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index2 = index;
                initWheelAdater(op3, optionsProvince4.get(index1).getChildren().get(index).getChildren());
                initWheelAdater(op4, optionsProvince4.get(index1).getChildren().get(index).getChildren().get(0).getChildren());
            }
        });
        op3.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index3 = index;
                initWheelAdater(op4, optionsProvince4.get(index1).getChildren().get(index2).getChildren().get(index).getChildren());
            }
        });
        op4.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index4 = index;
            }
        });
        dialog2 = new Dialog(this, R.style.shareDialog_style);
        dialog2.setContentView(view);
        Window dialogWindow = dialog2.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
//        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
//        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
//        // 一定要重新设置, 才能生效
//        dialogWindow.setAttributes(attributes);
        if (dialogWindow != null) {
            dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
            dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
        }

        //dialog2.show();
        //        if (mDialog != null) {
//
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    Gravity.BOTTOM);
//
//            params.leftMargin = 0;
//            params.rightMargin = 0;
//            pvTime.getDialogContainerLayout().setLayoutParams(params);
//
//            Window dialogWindow = mDialog.getWindow();
//            if (dialogWindow != null) {
//                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
//                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
//            }
//        }

    }

    private void initFourJson3() {
        viewPop = getLayoutInflater().inflate(R.layout.four_address_item, null);
        WheelView op1 = (WheelView) viewPop.findViewById(R.id.options1);
        final WheelView op2 = (WheelView) viewPop.findViewById(R.id.options2);
        final WheelView op3 = (WheelView) viewPop.findViewById(R.id.options3);
        final WheelView op4 = (WheelView) viewPop.findViewById(R.id.options4);
        initWheelAdater(op1, optionsProvince4);
        initWheelAdater(op2, optionsProvince4.get(0).getChildren());
        initWheelAdater(op3, optionsProvince4.get(0).getChildren().get(0).getChildren());
        initWheelAdater(op4, optionsProvince4.get(0).getChildren().get(0).getChildren().get(0).getChildren());
        op1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index1 = index;
                initWheelAdater(op2, optionsProvince4.get(index).getChildren());
                initWheelAdater(op3, optionsProvince4.get(index).getChildren().get(0).getChildren());
                initWheelAdater(op4, optionsProvince4.get(index).getChildren().get(0).getChildren().get(0).getChildren());
            }
        });
        op2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index2 = index;
                initWheelAdater(op3, optionsProvince4.get(index1).getChildren().get(index).getChildren());
                initWheelAdater(op4, optionsProvince4.get(index1).getChildren().get(index).getChildren().get(0).getChildren());
            }
        });
        op3.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index3 = index;
                initWheelAdater(op4, optionsProvince4.get(index1).getChildren().get(index2).getChildren().get(index).getChildren());
            }
        });
        op4.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                index4 = index;
            }
        });
        popupWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(viewPop)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .enableBackgroundDark(true)
                .setBgDarkAlpha(0.5f)
                .enableOutsideTouchableDissmiss(true)
                .setFocusable(true)
                .setAnimationStyle(R.style.view_slide_anim)
                .create();
        //popupWindow = new PopupWindow(viewPop, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        //popupWindow.setContentView(viewPop);
//        popupWindow.setFocusable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setAnimationStyle(R.style.view_slide_anim);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        //popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 0, 0)));
//        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams params = window.getAttributes();
//                params.alpha = 1.0f;
//                window.setAttributes(params);
//            }
//        });
    }

    private void initWheelAdater(WheelView wheelView, List list) {
        ArrayWheelAdapter<List> adapter = new ArrayWheelAdapter<>(list);
        wheelView.setAdapter(adapter);
        wheelView.setCurrentItem(0);
        wheelView.setCyclic(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Time && pvTime != null) {
            // pvTime.setDate(Calendar.getInstance());
           /* pvTime.show(); //show timePicker*/
            pvTime.show(v);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        } else if (v.getId() == R.id.btn_lunar) {
            pvCustomLunar.show();
        } else if (v.getId() == R.id.btn_Options && pvOptions != null) {
            pvOptions.show(); //弹出条件选择器
        } else if (v.getId() == R.id.btn_CustomTime && pvCustomTime != null) {
            pvCustomTime.show(); //弹出自定义时间选择器
        } else if (v.getId() == R.id.btn_CustomOptions && pvCustomOptions != null) {
            pvCustomOptions.show(); //弹出自定义条件选择器
        } else if (v.getId() == R.id.btn_no_linkage && pvNoLinkOptions != null) {//不联动数据选择器
            pvNoLinkOptions.show();
        } else if (v.getId() == R.id.btn_GotoJsonData && pvOptionsAddress != null) {
            pvOptionsAddress.show();
        } else if (v.getId() == R.id.btn_Goto_mycus && myCusOptionsAddress != null) {
            myCusOptionsAddress.show();
        } else if (v.getId() == R.id.btn_fragment) {
            startActivity(new Intent(PickViewCityActivity.this, FragmentTestActivity.class));
        } else if (v.getId() == R.id.btn_fourlevel && dialog != null) {
            if (!dialog.isShowing()) {
                dialog.show();
            }
        } else if (v.getId() == R.id.btn_fourlevel2 && dialog2 != null) {
            //if (!dialog2.isShowing()) {
            dialog2.show();
            //}
        } else if (v.getId() == R.id.btn_fourlevel3) {
            //popupWindow.showAtLocation();
            //在当前界面正下方显示
            if (popupWindow != null) {
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams params = window.getAttributes();
                params.alpha = 0.5f;
                window.setAttributes(params);
            } else {
                initFourJson3();
                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            }
//            WindowManager.LayoutParams params = window.getAttributes();
//            params.alpha = 0.7f;
//            window.addFlags(2);
//            window.setAttributes(params);
            // 显示在相对于view的左下方（这个左下方是在屏幕的左下方）
            //popupWindow.showAtLocation(v, Gravity.TOP | Gravity.RIGHT, 0, 0);
        }
    }

    static class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_LOAD_DATA:
                        Toast.makeText(activity, "数据解析中", Toast.LENGTH_SHORT).show();
                        break;
                    case MSG_LOAD_SUCCESS:
                        Toast.makeText(activity, "数据解析完成", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeCallbacksAndMessages(null);
        }
        if (dialog != null) {
            dialog = null;
        }
        if (dialog2 != null) {
            dialog2 = null;
        }
        if (popupWindow != null) {
            popupWindow = null;
        }
    }
}
