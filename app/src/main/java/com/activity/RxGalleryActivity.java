//package com.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import com.BaseActivity.BaseActivity;
//import com.greendaodemo2.R;
//
//import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
//import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
//import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
//import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultDisposable;
//import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;
//import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
//
///**
// * Created by qlshi on 2018/9/17.
// */
//
//public class RxGalleryActivity extends BaseActivity {
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//        super.initView(savedInstanceState);
//        setContentView(R.layout.activity_rxgallery);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
////        RefWatcher refWatcher = MyAppLication.getRefWatcher(this);
////        refWatcher.watch(this);
//    }
//
//    public void rxsingle(View view) {
//        //单选，使用RxGalleryFinal默认设置，并且带有裁剪
////        RxGalleryFinalApi.getInstance(this)
////                .openGalleryRadioImgDefault(new RxBusResultDisposable<ImageRadioResultEvent>() {
////            @Override
////            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
////
////            }
////        }).onCropImageResult(new IRadioImageCheckedListener() {
////            @Override
////            public void cropAfter(Object o) {
////                //裁剪完成
////
////            }
////
////            @Override
////            public boolean isActivityFinish() {
////                //返回false不关闭，返回true则为关闭
////                return false;
////            }
////        });
//        RxGalleryFinal
//                .with(this)
//                .gif(true)
//                .image()
//                .radio()
////                .cropAspectRatioOptions(0, new AspectRatio("3:3", 30, 10))
//                .crop()
//                .imageLoader(ImageLoaderType.FRESCO)
//                .subscribe(new RxBusResultDisposable<ImageRadioResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                        Toast.makeText(getBaseContext(), "选中了图片路径：" + imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .openGallery();
//    }
//
//    public void rxmulit(View view) {
//        RxGalleryFinal
//                .with(this)
//                .image()
//                .multiple()
//                .crop()
//                .maxSize(9)
//                .gif(true)
////                .cropAspectRatioOptions(0, new AspectRatio("3:3", 30, 10))
//                .imageLoader(ImageLoaderType.FRESCO)
//                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
//                        Toast.makeText(getBaseContext(), "已选择" + imageMultipleResultEvent.getResult().size() + "张图片", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                    }
//                })
//                .openGallery();
//    }
//
//    public void rxopencaream(View view) {
//        RxGalleryFinalApi.openZKCamera(this);
//    }
//
//    public void rxvedio(View view) {
//        RxGalleryFinal
//                .with(this)
//                .video()
//                .multiple()
//                .crop()
//                .maxSize(9)
//                .gif(true)
////                .cropAspectRatioOptions(0, new AspectRatio("3:3", 30, 10))
//                .imageLoader(ImageLoaderType.FRESCO)
//                .subscribe(new RxBusResultDisposable<ImageMultipleResultEvent>() {
//                    @Override
//                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
//                        Toast.makeText(getBaseContext(), "已选择" + imageMultipleResultEvent.getResult().size() + "张图片", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        super.onComplete();
//                    }
//                })
//                .openGallery();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RxGalleryFinalApi.TAKE_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            Log.e("eeeeee","拍照OK，图片路径:" + RxGalleryFinalApi.fileImagePath.getPath());
//        }
//    }
//}
