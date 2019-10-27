package com.utils;

import android.media.MediaPlayer;

import java.io.IOException;

public class MediaUtil {
    private static final String TAG = "MediaUtil";
    private MediaPlayer player = null;

    private MediaUtil() {
        if (player == null) {
            player = new MediaPlayer();
        }
    }

    private static MediaUtil instance = new MediaUtil();

    public static MediaUtil getInstance() {
        return instance;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void play(String path) {
        try {
            player.reset();
            player.setDataSource(path);
            // 通过异步的方式装载媒体资源
            player.prepareAsync();
            LogUtils.logE(TAG,"准备播放");
//            同步
//            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // 装载完毕回调
                    LogUtils.logE(TAG,"开始播放");
                    player.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.logE(TAG,"播放异常--"+e.getMessage());
        }
    }

    //暂停
    public void pause() {
        player.pause();
    }

    //重新播放
    public void rePlay() {
        player.start();
    }

    //停止
    public void onDeleteStop() {
        if (player != null&&player.isPlaying()) {
            player.pause();
        }
    }

    //停止
    public void onStop() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
