package com.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.utils.UtilPermission;

import butterknife.BindView;
import butterknife.OnClick;
import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class Activity_AudioRecorder extends BaseActivity {
    private static final int REQUEST_RECORD_AUDIO = 0;
    private static final String AUDIO_FILE_PATH =
            Environment.getExternalStorageDirectory().getPath() + "/wkrecord/recorded_audio.mp3";
    @BindView(R.id.startRecord)
    Button startRecord;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_audiorecorder);
        UtilPermission.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        UtilPermission.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

//    public void startRecording(View view) {
//        AndroidAudioRecorder.with(Activity_AudioRecorder.this)
//                // Required
//                .setFilePath(AUDIO_FILE_PATH)
////                .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
//                .setRequestCode(REQUEST_RECORD_AUDIO)
//
//                // Optional
//                .setSource(AudioSource.MIC)
//                .setChannel(AudioChannel.STEREO)
//                .setSampleRate(AudioSampleRate.HZ_48000)
//                .setAutoStart(false)
//                .setKeepDisplayOn(true)
//
//                // Start recording
//                .record();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Audio recorded successfully!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @OnClick(R.id.startRecord)
    public void onViewClicked() {
        AndroidAudioRecorder.with(Activity_AudioRecorder.this)
                // Required
                .setFilePath(AUDIO_FILE_PATH)
//                .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
                .setRequestCode(REQUEST_RECORD_AUDIO)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
    }
}
