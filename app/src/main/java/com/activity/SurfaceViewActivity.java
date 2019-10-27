package com.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.utils.FormatTimeUtil;

public class SurfaceViewActivity extends BaseActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener, View.OnClickListener,
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnVideoSizeChangedListener,
        SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    private ImageView playOrPauseIv;
    private SurfaceView videoSuf;
    private MediaPlayer mPlayer;
    private SeekBar mSeekBar;
    private String path;
    private RelativeLayout rootViewRl;
    private LinearLayout controlLl;
    private TextView startTime, endTime;
    private ImageView forwardButton, backwardButton;
    private boolean isShow = false;

    public static final int UPDATE_TIME = 0x0001;
    public static final int HIDE_CONTROL = 0x0002;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME:
                    updateTime();
                    mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
                    break;
                case HIDE_CONTROL:
                    hideControl();
                    break;
            }
        }
    };
    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_surfaceview);
        initViews();
        initMyData();
        initSurfaceView();
        initPlayer();

    }

    private void initViews() {
        playOrPauseIv = (ImageView) findViewById(R.id.playOrPause);
        startTime = (TextView) findViewById(R.id.tv_start_time);
        endTime = (TextView) findViewById(R.id.tv_end_time);
        mSeekBar = (SeekBar) findViewById(R.id.tv_progess);
        rootViewRl = (RelativeLayout) findViewById(R.id.root_rl);
        controlLl = (LinearLayout) findViewById(R.id.control_ll);
        forwardButton = (ImageView) findViewById(R.id.tv_forward);
        backwardButton = (ImageView) findViewById(R.id.tv_backward);
    }

    private void initMyData() {
        path= "android.resource://" + getPackageName() + "/" + R.raw.video_2;
    }

    private void initSurfaceView() {
        videoSuf = (SurfaceView) findViewById(R.id.surfaceView);
        videoSuf.setZOrderOnTop(false);
        videoSuf.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        videoSuf.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                mPlayer.setDisplay(surfaceHolder);
                mPlayer.prepareAsync();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    private void initPlayer() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnErrorListener(this);
        mPlayer.setOnInfoListener(this);
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnSeekCompleteListener(this);
        mPlayer.setOnVideoSizeChangedListener(this);
        try {
            //使用手机本地视频
            mPlayer.setDataSource(this,Uri.parse(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        playOrPauseIv.setOnClickListener(this);
        rootViewRl.setOnClickListener(this);
        rootViewRl.setOnTouchListener(this);
        forwardButton.setOnClickListener(this);
        backwardButton.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        startTime.setText(FormatTimeUtil.formatLongToTimeStr(mp.getCurrentPosition()));
        endTime.setText(FormatTimeUtil.formatLongToTimeStr(mp.getDuration()));
        mSeekBar.setMax(mp.getDuration());
        mSeekBar.setProgress(mp.getCurrentPosition());
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {

    }
    private void play() {
        if (mPlayer == null) {
            return;
        }
        Log.e("playPath", path);
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            mHandler.removeMessages(UPDATE_TIME);
            mHandler.removeMessages(HIDE_CONTROL);
            playOrPauseIv.setVisibility(View.VISIBLE);
            playOrPauseIv.setImageResource(android.R.drawable.ic_media_play);
        } else {
            mPlayer.start();
            mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 500);
            mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
            playOrPauseIv.setVisibility(View.INVISIBLE);
            playOrPauseIv.setImageResource(android.R.drawable.ic_media_pause);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_backward:
                backWard();
                break;
            case R.id.tv_forward:
                forWard();
                break;
            case R.id.playOrPause:
                play();
                break;
            case R.id.root_rl:
                showControl();
                break;
        }
    }

    /**
     * 更新播放时间
     */
    private void updateTime() {
        startTime.setText(FormatTimeUtil.formatLongToTimeStr(
                mPlayer.getCurrentPosition()));
        mSeekBar.setProgress(mPlayer.getCurrentPosition());
    }

    /**
     * 隐藏进度条
     */
    private void hideControl() {
        isShow = false;
        mHandler.removeMessages(UPDATE_TIME);
        controlLl.animate().setDuration(300).translationY(controlLl.getHeight());
    }
    /**
     * 显示进度条
     */
    private void showControl() {
        if (isShow) {
            play();
        }
        isShow = true;
        mHandler.removeMessages(HIDE_CONTROL);
        mHandler.sendEmptyMessage(UPDATE_TIME);
        mHandler.sendEmptyMessageDelayed(HIDE_CONTROL, 5000);
        controlLl.animate().setDuration(300).translationY(0);
    }
    /**
     * 设置快进10秒方法
     */
    private void forWard(){
        if(mPlayer != null){
            int position = mPlayer.getCurrentPosition();
            mPlayer.seekTo(position + 10000);
        }
    }

    /**
     * 设置快退10秒的方法
     */
    public void backWard(){
        if(mPlayer != null){
            int position = mPlayer.getCurrentPosition();
            if(position > 10000){
                position-=10000;
            }else{
                position = 0;
            }
            mPlayer.seekTo(position);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        if(mPlayer != null && b){
            mPlayer.seekTo(progress);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
