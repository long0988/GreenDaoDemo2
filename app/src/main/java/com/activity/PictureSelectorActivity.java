package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by qlshi on 2018/10/22.
 */

public class PictureSelectorActivity extends BaseActivity {
    private static final int CHOOSE_VIDEO = 0x111;
    private static final int CHOOSE_AUDIO = 0X112;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_pictureselector);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_white_style)
                        .maxSelectNum(9)// 最大图片选择数量 int
                        .minSelectNum(0)// 最小选择数量 int
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        .previewImage(true)// 是否可预览图片 true or false
                        .previewVideo(true)// 是否可预览视频 true or false
                        .enablePreviewAudio(true) // 是否可播放音频 true or false
                        .isCamera(true)// 是否显示拍照按钮 true or false
                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                        .enableCrop(false)// 是否裁剪 true or false
                        //.compress(true)// 是否压缩 true or false
                        .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                        .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                        .isGif(true)// 是否显示gif图片 true or false
                        //.compressSavePath()//压缩图片保存地址
                        .freeStyleCropEnabled(false)// 裁剪框是否可拖拽 true or false
                        .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                        .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                        .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                        .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                        .recordVideoSecond(60)//视频秒数录制 默认60s int
                        .isDragFrame(false)// 是否可拖动裁剪框(固定)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.btn2:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofVideo())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                        .theme(R.style.picture_QQ_style)
                        .imageSpanCount(3)// 每行显示个数 int
                        .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                        //.compress(true)// 是否压缩 true or false
                        //.compressSavePath()//压缩图片保存地址
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .recordVideoSecond(60)//视频秒数录制 默认60s int
                        .forResult(CHOOSE_VIDEO);
                break;
            case R.id.btn3:
                PictureSelector.create(this)
                        .openCamera(PictureMimeType.ofImage())
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.btn4:
                PictureSelector.create(this)
                        .openCamera(PictureMimeType.ofVideo())
                        .forResult(CHOOSE_VIDEO);
                break;
        }
    }

    @OnClick(R.id.btn5)
    public void onViewClicked() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAudio())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_Sina_style)
                .imageSpanCount(3)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                //.compress(true)// 是否压缩 true or false
                //.compressSavePath()//压缩图片保存地址
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .recordVideoSecond(60)//视频秒数录制 默认60s int
                .forResult(CHOOSE_AUDIO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    PictureSelector.create(this).themeStyle(R.style.picture_Sina_style).openExternalPreview(0, selectList);
                    break;
                case CHOOSE_VIDEO:
                    List<LocalMedia> selectList2 = PictureSelector.obtainMultipleResult(data);
                    PictureSelector.create(this).externalPictureVideo(selectList2.get(0).getPath());
                    break;
                case CHOOSE_AUDIO:
                    List<LocalMedia> selectList3 = PictureSelector.obtainMultipleResult(data);
                    PictureSelector.create(this).externalPictureAudio(selectList3.get(0).getPath());
                    break;
            }
        }
    }
}
