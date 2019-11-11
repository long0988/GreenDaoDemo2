package com.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.utils.LinearGradientUtil;
import com.widget.CircleBarView;
import com.widget.CompletedView;
import com.widget.WaveProgressView;

import java.text.DecimalFormat;

import butterknife.BindView;

public class CricleBarActivity extends BaseActivity {
    @BindView(R.id.text_progress)
    TextView textProgress;
    @BindView(R.id.text_progress2)
    TextView textProgress2;
    @BindView(R.id.text_progress3)
    TextView textProgress3;
    private int mTotalProgress = 100;
    private int mCurrentProgress = 0;
    //进度条
    private CompletedView mTasksView;
    private CircleBarView circlebar,circlebar2;
    private WaveProgressView wave_progress;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_cricle_bar);
        mTasksView = (CompletedView) findViewById(R.id.tasks_view);
        circlebar = findViewById(R.id.circle_view);
        circlebar2 = findViewById(R.id.circle_view2);
        wave_progress=findViewById(R.id.wave_progress);

        //1进度
        circlebar.setMaxNum(100);
        circlebar.setTextView(textProgress);
        circlebar.setOnAnimationListener(new CircleBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
                DecimalFormat decimalFormat = new DecimalFormat("0.0");
                String s = decimalFormat.format(interpolatedTime * updateNum / maxNum * 100) + "%";
                return s;
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {
                LinearGradientUtil linearGradientUtil = new LinearGradientUtil(Color.YELLOW,Color.RED);
                paint.setColor(linearGradientUtil.getColor(interpolatedTime));
            }
        });
        circlebar.setProgressNum(100, 3000);
        //2
        circlebar2.setTextView(textProgress2);
        circlebar2.setProgressNum(100,5000);
        circlebar2.setOnAnimationListener(new CircleBarView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
                return "跳过";
            }

            @Override
            public void howTiChangeProgressColor(Paint paint, float interpolatedTime, float updateNum, float maxNum) {

            }
        });
        //3进度
        wave_progress.setProgressNum(80,3000);
        wave_progress.setTextView(textProgress3);
        wave_progress.setDrawSecondWave(true);
        wave_progress.setOnAnimationListener(new WaveProgressView.OnAnimationListener() {
            @Override
            public String howToChangeText(float interpolatedTime, float updateNum, float maxNum) {
                DecimalFormat decimalFormat=new DecimalFormat("0.00");
                String s = decimalFormat.format(interpolatedTime * updateNum / maxNum * 100)+"%";
                return s;
            }

            @Override
            public float howToChangeWaveHeight(float percent, float waveHeight) {
                return (1-percent)*waveHeight;
            }
        });
        //4进度
        new Thread(new ProgressRunable()).start();
    }

    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mTasksView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(90);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
