package com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.adapter.MainActivityRcrAdapter;
import com.adapter.MyRecyclerAdapter;
import com.city.PickCityActivity;
import com.contact.PickContactActivity;
import com.decoration.mainpage.DividerItemDecoration;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.greendaodemo2.MyAppLication;
import com.greendaodemo2.R;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.mpchart.MPChartListActivity;
import com.utils.CustomDensityUtil;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView mListVew;
    private RecyclerView mRecyclerView;
    private List<Class<? extends Activity>> mListAciivity = new ArrayList<>();
    private List<String> mClassName = new ArrayList<>();
    private MainActivityRcrAdapter mainAdapter;

    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        //CustomDensityUtil.setCustomDensity(this, MyAppLication.context);
        setContentView(R.layout.activity_main);
        //mListVew = (ListView) findViewById(R.id.listview);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mListAciivity.add(DateTimePickerActivity.class);
        mClassName.add("时钟-日期页");
        mListAciivity.add(CityActivity.class);
        mClassName.add("城市列表-地址列表-liji.library");
        mListAciivity.add(ContactActivity.class);
        mClassName.add("联系人列表-my");
        mListAciivity.add(CityPickerActivity.class);
        mClassName.add("城市列表-热门-me.yokeyword");
        mListAciivity.add(PickContactActivity.class);
        mClassName.add("联系人列表2-优化-me.yokeyword");
        mListAciivity.add(PickCityActivity.class);
        mClassName.add("城市列表2-优化-me.yokeyword");
        mListAciivity.add(GreenDaoActivity.class);
        mClassName.add("GreenDao数据库");
        mListAciivity.add(CityPickerActivity_II.class);
        mClassName.add("地址选择器-wheelpicker");
        mListAciivity.add(PickViewCityActivity.class);
        mClassName.add("地址选择等--com.bigkoo.pickerview△");
        mListAciivity.add(WheelViewActivity.class);
        mClassName.add("滚轮WheelView");
        mListAciivity.add(ScannerActivity.class);
        mClassName.add("二维码扫描-yipianfengye");
        mListAciivity.add(ScannerActivity2.class);
        mClassName.add("二维码-条形码扫描-tbruyelle");
        mListAciivity.add(GeneraRcodeActivity.class);
        mClassName.add("条形码生成-tbruyelle");
        mListAciivity.add(OeroActivity.class);
        mClassName.add("8.0安装未知来源适配");
        mListAciivity.add(PictureActivity.class);
        mClassName.add("仿微信图片选择框架-com.lzy.imagepicker");
        mListAciivity.add(PictureSelectorActivity.class);
        mClassName.add("图片选择框架-LuckSiege.PictureSelector");
        mListAciivity.add(GalleryActivity.class);
        mClassName.add("Gallery图片框架");
//        mListAciivity.add(RxGalleryActivity.class);
//        mClassName.add("Rx多功能图片选择框架");
        mListAciivity.add(SwipeDeleteActiivity.class);
        mClassName.add("右滑删除-recyclerview-swipe");
        mListAciivity.add(MPChartListActivity.class);
        mClassName.add("chart图表演示");
        mListAciivity.add(WiFiActivity.class);
        mClassName.add("WiFi链接");
        mListAciivity.add(BlueToothActivity.class);
        mClassName.add("蓝牙链接");
        mListAciivity.add(AudioActivity.class);
        mClassName.add("录音功能");
        mListAciivity.add(Activity_AudioRecorder.class);
        mClassName.add("Activity_AudioRecorder");
        mListAciivity.add(AudioWavActivity.class);
        mClassName.add("录音Wav格式");
        mListAciivity.add(RandomCodeActivity.class);
        mClassName.add("随机验证码");
        mListAciivity.add(ProgressBarActivity.class);
        mClassName.add("自定义条形颜色");
        mListAciivity.add(DouYinActivity.class);
        mClassName.add("仿抖音切換");
        mListAciivity.add(VideoTypeActivity.class);
        mClassName.add("视频播放");
        mListAciivity.add(ListViewNestActivity.class);
        mClassName.add("ListView嵌套滑动问题");
        mListAciivity.add(RecyclerNestActivity.class);
        mClassName.add("Recycler嵌套滑动问题");
        mListAciivity.add(CricleBarActivity.class);
        mClassName.add("圆形进度条");
        mListAciivity.add(LinearGradientActivity.class);
        mClassName.add("风险刻度");
        mListAciivity.add(BottomTabsActivity.class);
        mClassName.add("底部Tab");
        mListAciivity.add(WhiteBoardActivity.class);
        mClassName.add("白板涂鸦");
        mListAciivity.add(MyTablayoutActivity.class);
        mClassName.add("重新定义样式Tablayout");
        /*ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mClassName);
        mListVew.setAdapter(arrayAdapter);*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter = new MainActivityRcrAdapter(mClassName,this);
        mRecyclerView.setAdapter(mainAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,1));
        initMyListener();
        //权限会导致卡顿？把挂载权限去掉
        initRequestPermissions();
        boolean valid_cn = isPhoneNumberValid("+8615820799999", "86");
        System.out.println("isPhoneNumberValid:" + valid_cn);

        boolean valid_hk = isPhoneNumberValid("+85283079901", "852");
        System.out.println("isPhoneNumberValid:" + valid_hk);
        boolean valid_My = isPhoneNumberValid("+8613822198145", "86");
        System.out.println("isPhoneNumberValid:" + valid_My);
        boolean valid_niriliya = isPhoneNumberValid("+23408180245099", "234");
        System.out.println("isPhoneNumberValid:" + valid_niriliya);
        boolean valid_niriliya2 = isPhoneNumberValid("+2348180245098", "234");
        System.out.println("isPhoneNumberValid:" + valid_niriliya2);
    }

    private void initRequestPermissions() {
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.permission(Permission.REQUEST_INSTALL_PACKAGES) //支持8.0及以上请求安装权限
                //.permission(Permission.SYSTEM_ALERT_WINDOW) //支持请求6.0及以上悬浮窗权限
                //.permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
//                .permission(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.RECORD_AUDIO})
                .permission(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO})
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        Toast.makeText(MainActivity.this, "拒绝权限将导致应用部分功能不能使用", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initMyListener() {
        /*mListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, mListAciivity.get(position)));
            }
        });*/
        mainAdapter.setOnItemClickListener(new MainActivityRcrAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(MainActivity.this, mListAciivity.get(position)));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 根据区号判断是否是正确的电话号码
     *
     * @param phoneNumber :带国家码的电话号码
     * @param countryCode :默认国家码
     *                    return ：true 合法  false：不合法
     */
    public static boolean isPhoneNumberValid(String phoneNumber, String countryCode) {

        System.out.println("isPhoneNumberValid: " + phoneNumber + "/" + countryCode);
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            System.err.println("isPhoneNumberValid NumberParseException was thrown: " + e.toString());
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    //  获取返回的联系人的Uri信息
                    Uri contactDataUri = data.getData();
                    Cursor cursor = getContentResolver().query(contactDataUri, null, null, null, null);
                    if (cursor.moveToFirst()) {
                        //   获得联系人记录的ID
                        String contactId = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts._ID));
                        //  获得联系人的名字
                        String name = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));
                        String phoneNumber = "未找到联系人号码";
                        Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.
                                Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.
                                Phone.CONTACT_ID + "=" + "?", new String[]{contactId}, null);
                        if (phoneCursor.moveToFirst()) {
                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        //  关闭查询手机号码的cursor
                        phoneCursor.close();
                    }
                    //  关闭查询联系人信息的cursor
                    cursor.close();
                }
                break;
        }
    }
}
