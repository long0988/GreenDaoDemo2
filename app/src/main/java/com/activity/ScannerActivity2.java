package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.greendaodemo2.R;
import com.utils.FileProviderUtil;
import com.utils.Glide4Engine;
import com.utils.MyGifSizeFilter;
import com.utils.ToastUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by qlshi on 2018/8/23.
 */

public class ScannerActivity2 extends AppCompatActivity implements QRCodeView.Delegate {
    private ZXingView mZXingView;
    private int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY=0x006;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanneer2);
        mZXingView = (ZXingView) findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
        mZXingView.startSpot();
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        if (!TextUtils.isEmpty(result)) {
            ToastUtil.show(this, "扫描结果为：" + result);
        }
        mZXingView.startSpot(); // 延迟0.5秒后开始识别
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        ToastUtil.show(this, "打开相机出错");
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.open_flashlight:
                mZXingView.openFlashlight(); // 打开闪光灯
                break;
            case R.id.close_flashlight:
                mZXingView.closeFlashlight();
                break;
            case R.id.scan_two_dimension:
                mZXingView.changeToScanQRCodeStyle();
                mZXingView.setType(BarcodeType.TWO_DIMENSION,null); //切换成扫描二维码样式,只识别二维条码
                break;
            case R.id.scan_one_dimension:
                mZXingView.changeToScanBarcodeStyle();//切换成扫描一维码样式,只识别一维条码
                mZXingView.setType(BarcodeType.ONE_DIMENSION,null);
                break;
            case R.id.scan_all:
                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
                mZXingView.setType(BarcodeType.ALL, null); // 识别所有类型的码
                break;
            case R.id.choose_from_gallery:
//                Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
//                        .cameraFileDir(null)// 拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
//                        .maxChooseCount(1)// 图片选择张数的最大值
//                        .selectedPhotos(null)// 当前已选中的图片路径集合
//                        .pauseOnScroll(true)// 滚动列表时是否暂停加载图片
//                        .build();
//                startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                Matisse.from(ScannerActivity2.this)
                        .choose(MimeType.ofAll())// 1、获取 SelectionCreator
                        .countable(false)
                        .capture(true)
                        //.showSingleMediaType(true)//显示单一类型
                        .captureStrategy(new CaptureStrategy(true, FileProviderUtil.getPakeNmae(this)+".fileprovider"))
                        .maxSelectable(1)
                        .addFilter(new MyGifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .imageEngine(new Glide4Engine())
                        .forResult(REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
//            case R.id.scan_custom:
//                mZXingView.changeToScanQRCodeStyle(); // 切换成扫描二维码样式
//
//                Map<DecodeHintType, Object> hintMap = new EnumMap<>(DecodeHintType.class);
//                List<BarcodeFormat> formatList = new ArrayList<>();
//                formatList.add(BarcodeFormat.QR_CODE);
//                formatList.add(BarcodeFormat.EAN_13);
//                formatList.add(BarcodeFormat.CODE_128);
//                hintMap.put(DecodeHintType.POSSIBLE_FORMATS, formatList); // 可能的编码格式
//                hintMap.put(DecodeHintType.TRY_HARDER, Boolean.TRUE); // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
//                hintMap.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 编码字符集
//                mZXingView.setType(BarcodeType.CUSTOM, hintMap); // 自定义识别的类型
//                break;
            default:
                mZXingView.startSpotAndShowRect(); // 显示扫描框，并且延迟0.5秒后开始识别
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK&&requestCode==REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY){
           // String picturePath = BGAPhotoPickerActivity.getSelectedPhotos(data).get(0);
            List<Uri> uris = Matisse.obtainResult(data);
            List<String> strings = Matisse.obtainPathResult(data);
            Log.e("eeeeee",uris.toString());
            Log.e("eeeeee222",strings.toString());

            //String imageAbsolutePath = ImageUtil.getImageAbsolutePath(this, uris.get(0));
            mZXingView.decodeQRCode(strings.get(0));
        }
    }
}
