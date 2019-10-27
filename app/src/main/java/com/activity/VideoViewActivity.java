package com.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.utils.LogUtils;
import com.widget.FullVideoView;

/*1、MediaController+VideoView实现方式*/
public class VideoViewActivity extends BaseActivity {

    private FullVideoView videoView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_video1);
        videoView = findViewById(R.id.videoView);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video_1;
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            videoView.setVideoPath(Environment.getExternalStorageDirectory() + "/246.mp4");
//        }
        videoView.setVideoURI(Uri.parse((uri)));
        videoView.setMediaController(new MediaController(this));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                LogUtils.logE("eeeeeeee","视频初始化完成");
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                LogUtils.logE("eeeeeeee","视频初播完");
                videoView.start();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                LogUtils.logE("eeeeeeee","视频出错");
                return false;
            }
        });
//        videoView.findFocus();
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null){
            videoView.destroyDrawingCache();
            videoView=null;
        }
    }
}
