package com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.utils.DipPxUtils;
import com.utils.LogUtils;
import com.utils.MediaRecoderUtils;
import com.utils.MediaUtil;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;

public class AudioActivity extends BaseActivity implements MediaRecoderUtils.OnAudioStatusUpdateListener {
    private final String TAG = "AudioActivity";
    @BindView(R.id.startRecord)
    TextView startRecord;
    @BindView(R.id.pauseRecord)
    TextView pauseRecord;
    @BindView(R.id.doneRecord)
    TextView doneRecord;
    @BindView(R.id.palyRecord)
    TextView palyRecord;
    MediaRecoderUtils mediaRecoderUtils;
    @BindView(R.id.reRecord)
    TextView reRecord;
    @BindView(R.id.timems)
    TextView timems;
    @BindView(R.id.deleteRecord)
    TextView deleteRecord;
    private String audioPath = "";
    private ExecutorService mExecutorService;
    private final static int REQUEST_RECORDER = 100;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_audio);
        mExecutorService = Executors.newCachedThreadPool();
    }

    @Override
    protected void initData() {
        mediaRecoderUtils = new MediaRecoderUtils(this);
        mediaRecoderUtils.setOnAudioStatusUpdateListener(this);
    }
    //删除
    public void deleteRecord(View view) {
        mediaRecoderUtils.deleteRecoder();
        setDeleteVisible();
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                MediaUtil.getInstance().onDeleteStop();
            }
        });
    }

    //开始录音
    public void startRecord(View view) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                mediaRecoderUtils.startRecord();
            }
        });
        setStartVisible();
    }

    //暂停
    public void pauseRecord(View view) {
        mediaRecoderUtils.pauseRecord();
        setPauseVisible();
    }

    //完成
    public void doneRecord(View view) {
        mediaRecoderUtils.stopRecord();
        setDoneVisible();
    }

    //播放
    public void palyRecord(View view) {
        File file = new File(audioPath);
        if(!file.exists()){
            Toast.makeText(this,"链接不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                MediaUtil.getInstance().play(audioPath);
            }
        });

    }

    //重录
    public void reRecord(View view) {
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                mediaRecoderUtils.reRecord();
            }
        });
        setReRecordVisible();
//        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setType( "audio/*");
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent,REQUEST_RECORDER);
//        }else {
//            Toast.makeText(this,"您设备不支持该功能！", Toast.LENGTH_SHORT).show();
//        }
//        mediaRecoderUtils.reRecord();

    }

    //    录音回调
    @Override
    public void onUpdate(double db, final long time) {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                String seconds = DipPxUtils.formatSeconds((int) time);
                timems.setText(seconds);
            }
        });
    }

    //    停止
    @Override
    public void onStop(String filePath) {
        LogUtils.logE(TAG, "音频地址--"+filePath);
        audioPath = filePath;
    }

    public void setStartVisible() {
        startRecord.setVisibility(View.GONE);
        pauseRecord.setVisibility(View.VISIBLE);
        deleteRecord.setVisibility(View.VISIBLE);
        doneRecord.setVisibility(View.VISIBLE);
        reRecord.setVisibility(View.GONE);
    }
    public void setPauseVisible() {
        startRecord.setVisibility(View.GONE);
        pauseRecord.setVisibility(View.GONE);
        deleteRecord.setVisibility(View.VISIBLE);
        doneRecord.setVisibility(View.VISIBLE);
        reRecord.setVisibility(View.VISIBLE);
    }
    public void setDoneVisible() {
        startRecord.setVisibility(View.GONE);
        pauseRecord.setVisibility(View.GONE);
        deleteRecord.setVisibility(View.VISIBLE);
        doneRecord.setVisibility(View.GONE);
        palyRecord.setVisibility(View.VISIBLE);
        reRecord.setVisibility(View.GONE);
    }

    public void setDeleteVisible() {
        startRecord.setVisibility(View.VISIBLE);
        pauseRecord.setVisibility(View.GONE);
        deleteRecord.setVisibility(View.GONE);
        doneRecord.setVisibility(View.GONE);
        palyRecord.setVisibility(View.GONE);
        reRecord.setVisibility(View.GONE);
    }
    public void setReRecordVisible() {
        startRecord.setVisibility(View.GONE);
        pauseRecord.setVisibility(View.VISIBLE);
        deleteRecord.setVisibility(View.VISIBLE);
        doneRecord.setVisibility(View.VISIBLE);
        palyRecord.setVisibility(View.GONE);
        reRecord.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mExecutorService.isShutdown();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
