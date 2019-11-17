package com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.city.PickCityActivity;
import com.contact.PickContactActivity;
import com.greendaodemo2.MyAppLication;
import com.greendaodemo2.R;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.mpchart.MPChartListActivity;
import com.utils.CustomDensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ListView mListVew;
    private List<Class<? extends Activity>> mListAciivity=new ArrayList<>();
    private List<String> mClassName=new ArrayList<>();

    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        CustomDensityUtil.setCustomDensity(this, MyAppLication.context);
        setContentView(R.layout.activity_main);
        mListVew=(ListView)findViewById(R.id.listview);
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
        mListAciivity.add(TabBottomActivity.class);
        mClassName.add("底部Tab");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mClassName);
        mListVew.setAdapter(arrayAdapter);
        initMyListener();
        initRequestPermissions();
    }

    private void initRequestPermissions() {
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.permission(Permission.REQUEST_INSTALL_PACKAGES) //支持8.0及以上请求安装权限
                //.permission(Permission.SYSTEM_ALERT_WINDOW) //支持请求6.0及以上悬浮窗权限
                //.permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
                .permission(new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.RECORD_AUDIO})
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        Toast.makeText(MainActivity.this,"拒绝权限将导致应用部分功能不能使用",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initMyListener() {
        mListVew.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,mListAciivity.get(position)));
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
