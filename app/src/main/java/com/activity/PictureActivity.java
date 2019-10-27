package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.utils.GildeImageLoader;

import java.util.ArrayList;

/**
 * Created by qlshi on 2018/9/14.
 */

public class PictureActivity extends BaseActivity{
    private static final int IMAGE_PICKER = 0x001;
    private static final int REQUEST_CODE_SELECT = 0x002;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GildeImageLoader());//设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setMultiMode(true);
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
    public void onClick(View v) {
        int id = v.getId();
        Intent intent = new Intent(this, ImageGridActivity.class);
        switch (id){
            case R.id.open_image:
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case R.id.open_carmae:
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for(ImageItem item:images) {
                    Log.e("eeeeeee", item.path+"-"+item.name+"--"+item.mimeType+"--"+item.describeContents());
                }
            } else if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for(ImageItem item:images) {
                    Log.e("eeeeeee", "拍照-"+item.path+"-"+item.name+"--"+item.mimeType+"--"+item.describeContents());
                }
            } else {
                Toast.makeText(this, "数据出错", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
