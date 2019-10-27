package com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.utils.AudioRecoderUtils;
import com.utils.DipPxUtils;
import com.utils.MediaUtil;

import java.io.File;

import butterknife.BindView;

public class AudioWavActivity extends BaseActivity implements AudioRecoderUtils.RecordStreamListener {
    @BindView(R.id.timems)
    TextView timems;
    @BindView(R.id.deleteRecord)
    TextView deleteRecord;
    @BindView(R.id.startRecord)
    TextView startRecord;
    @BindView(R.id.pauseRecord)
    TextView pauseRecord;
    @BindView(R.id.reRecord)
    TextView reRecord;
    @BindView(R.id.doneRecord)
    TextView doneRecord;
    @BindView(R.id.palyRecord)
    TextView palyRecord;
    private String audioPath = null;
    AudioRecoderUtils mAudioInstance;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_audio_wav);
        mAudioInstance = AudioRecoderUtils.getInstance();
        mAudioInstance.setOnRecordStreamListener(this);
        mAudioInstance.createDefaultAudio();
    }

    //删除录音
    public void deleteRecord(View viwe){
        mAudioInstance.delete();
        setDeleteVisible();
    }

    //开始录音
    public void startRecord(View viwe){
        mAudioInstance.startRecord();
        setStartVisible();
    }
    //暂停录音
    public void pauseRecord(View viwe){
        mAudioInstance.pauseRecord();
        setPauseVisible();
    }
    //继续录音
    public void  reRecord(View viwe){
        mAudioInstance.startRecord();
        setReRecordVisible();
    }
    //完成录音
    public void doneRecord(View viwe){
        mAudioInstance.stopRecord();
        setDoneVisible();
    }

    //播放录音
    public void palyRecord(View view) {
        File file = new File(audioPath);
        if(audioPath==null||!file.exists()){
            Toast.makeText(this,"链接不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        MediaUtil.getInstance().play(audioPath);
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
    public void recordOfByte(byte[] data, int begin, int end) {

    }

    @Override
    public void audioPath(String path) {
        audioPath = path;
    }

    @Override
    public void updateTime(final long time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String seconds = DipPxUtils.formatSeconds((int) time);
                timems.setText(seconds);
            }
        });
    }
}
