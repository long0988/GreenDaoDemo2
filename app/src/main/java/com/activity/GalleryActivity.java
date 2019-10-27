package com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.baoyz.actionsheet.ActionSheet;
import com.glide.GalleryGlideImageLoader;
import com.glide.GlidePauseOnScrollListener;
import com.greendaodemo2.R;

import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by qlshi on 2018/9/18.
 */

public class GalleryActivity extends BaseActivity {
    FunctionConfig functionConfig;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initGallery();
    }

    private void initGallery() {
        //建议在application中配置
        //设置主题
        ThemeConfig themeConfig = ThemeConfig.DEFAULT;
        //自定义主题
//        ThemeConfig theme = new ThemeConfig.Builder()
//                .setTitleBarBgColor(Color.rgb(0xFF, 0x57, 0x22))
//                .setTitleBarTextColor(Color.BLACK)
//                .setTitleBarIconColor(Color.BLACK)
//                .setFabNornalColor(Color.RED)
//                .setFabPressedColor(Color.BLUE)
//                .setCheckNornalColor(Color.WHITE)
//                .setCheckSelectedColor(Color.BLACK)
//                .setIconBack(R.mipmap.ic_action_previous_item)
//                .setIconRotate(R.mipmap.ic_action_repeat)
//                .setIconCrop(R.mipmap.ic_action_crop)
//                .setIconCamera(R.mipmap.ic_action_camera)
//                .build();
//        themeConfig = theme;
        //配置功能
         functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                 .setMutiSelectMaxSize(9)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(this, new GalleryGlideImageLoader(), themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(new GlidePauseOnScrollListener(false, true))
                .setNoAnimcation(false)
                .build();
        GalleryFinal.init(coreConfig);
    }
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {

        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            Toast.makeText(GalleryActivity.this, "选择结果--"+resultList.toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(GalleryActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    public void single(View view) {
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY,mOnHanlderResultCallback);
    }

    public void mulit(View view) {
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
    }

    public void actionsheet(View view) {
        ActionSheet.createBuilder(this,getSupportFragmentManager())
                .setCancelButtonTitle("取消(Cancel)")
                .setOtherButtonTitles("打开相册(Open Gallery)", "拍照(Camera)", "裁剪(Crop)", "编辑(Edit)")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {

                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean b) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int i) {

                    }
                }).show();
    }

    public void opencaream(View view) {
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mOnHanlderResultCallback!=null){
            mOnHanlderResultCallback=null;
        }
    }
}
