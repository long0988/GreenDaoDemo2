package com.activity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.progress.ArcProgress;
import com.progress.OnTextCenter;
import com.progress.onImageCenter;
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
    ArcProgress mProgress,mProgress1,mProgress02,mProgress03;

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
        circlebar2.setProgressNum(100,3000);
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
        //5进度
        mProgress = (ArcProgress) findViewById(R.id.myProgress);
        mProgress1 = (ArcProgress) findViewById(R.id.myProgress01);
        mProgress02 = (ArcProgress) findViewById(R.id.myProgress02);
        mProgress03 = (ArcProgress) findViewById(R.id.myProgress03);

        mProgress.setOnCenterDraw(new OnTextCenter());
        mProgress1.setOnCenterDraw(new OnTextCenter());
        mProgress02.setOnCenterDraw(new ArcProgress.OnCenterDraw() {
            @Override
            public void draw(Canvas canvas, RectF rectF, float x, float y, float storkeWidth, int progress) {
                Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
                textPaint.setStrokeWidth(35);
                textPaint.setColor(getResources().getColor(R.color.textColor));
                textPaint.setTextSize(68);
                String progressStr = String.valueOf(progress+"%");
                float textX = x-(textPaint.measureText(progressStr)/2);
                float textY = y-((textPaint.descent()+textPaint.ascent())/2);
                canvas.drawText(progressStr,textX,textY,textPaint);
            }
        });
        mProgress03.setOnCenterDraw(new onImageCenter(this,R.mipmap.ic_launcher));
        addProrgress(mProgress);
        addProrgress(mProgress1);
        addProrgress(mProgress02);
        addProrgress(mProgress03);
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
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArcProgress progressBar = (ArcProgress) msg.obj;
            progressBar.setProgress(msg.what);
        }
    };

    public void addProrgress(ArcProgress progressBar) {
        Thread thread = new Thread(new ProgressThread(progressBar));
        thread.start();
    }

    class ProgressThread implements Runnable{
        int i= 0;
        private ArcProgress progressBar;
        public ProgressThread(ArcProgress progressBar) {
            this.progressBar = progressBar;
        }
        @Override
        public void run() {
            for(;i<=100;i++){
                if(isFinishing()){
                    break;
                }
                Message msg = new Message();
                msg.what = i;
                Log.e("DEMO","i == "+i);
                msg.obj = progressBar;
                SystemClock.sleep(100);
                handler.sendMessage(msg);
            }
        }
    }
}
