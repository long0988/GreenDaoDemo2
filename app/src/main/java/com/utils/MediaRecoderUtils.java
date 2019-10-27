package com.utils;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MediaRecoderUtils {
    //文件路径
    private String filePath;
    //文件夹路径
    private String FolderPath;

    private MediaRecorder mMediaRecorder;
    private final String TAG = "MediaRecoderUtils";
    public static final int MAX_LENGTH = 1000 * 60 * 10;// 最大录音时长1000*60*10;
    private OnAudioStatusUpdateListener audioStatusUpdateListener;
    private long startTime=0;
    private long endTime;
    //存放暂停的文件集合
    private ArrayList<String> recorderList=new ArrayList<>();
    private File finallFile;
    private int BASE = 1;
    private int SPACE = 1000;// 间隔取样时间
    private final Handler mHandler = new Handler();
    private Context mContext;

    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    public void setOnAudioStatusUpdateListener(OnAudioStatusUpdateListener audioStatusUpdateListener) {
        this.audioStatusUpdateListener = audioStatusUpdateListener;
    }
    /**
     * 文件存储默认sdcard/wkrecord
     */
    public MediaRecoderUtils(Context context) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context,"手机内存不可用",Toast.LENGTH_SHORT).show();
            return;
        }
        String filePath=Environment.getExternalStorageDirectory() + "/wkrecord/";
        File path = new File(filePath);
        if (!path.exists())
            path.mkdirs();
        this.mContext=context;
        this.FolderPath = filePath;
    }

//    public MediaRecoderUtils(String filePath) {
//
//        File path = new File(filePath);
//        if (!path.exists())
//            path.mkdirs();
//
//        this.FolderPath = filePath;
//    }

    /**
     * 开始录音 使用amr格式
     * 录音文件
     *
     * @return
     */
    public void startRecord() {
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            filePath = FolderPath + System.currentTimeMillis() + ".mp3";
            /* ③准备 */
            mMediaRecorder.setOutputFile(filePath);
            recorderList.add(filePath);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                mMediaRecorder.setMaxDuration(MAX_LENGTH);
            }
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            audioStatusUpdateListener.onStop(filePath);
            updateMicStatus();
        } catch (IllegalStateException e) {
            LogUtils.logE(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            LogUtils.logE(TAG, "call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        LogUtils.logE(TAG,"停止录音");
//        if (mMediaRecorder == null)
//            return ;
        //有一些网友反应在5.0以上在调用stop的时候会报错，翻阅了一下谷歌文档发现上面确实写的有可能会报错的情况，捕获异常清理一下就行了，感谢大家反馈！
        try {
            if(mMediaRecorder!=null) {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
            startTime=0;
            //合并文件
            mergeFileList();


        } catch (RuntimeException e) {
            LogUtils.logE(TAG,"进去了录音停止异常方法");
            if(mMediaRecorder!=null) {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
            Toast.makeText(mContext,"程序出错",Toast.LENGTH_SHORT).show();
            File file = new File(filePath);
            if (file.exists())
                file.delete();
            filePath = "";
        }
    }

    //合并文件
    private void mergeFileList() {
        int size = recorderList.size();
        if(size<=1){
            audioStatusUpdateListener.onStop(filePath);
            filePath = "";
            return;
        }
        String finallUrl=FolderPath + System.currentTimeMillis() + ".mp3";
        FileOutputStream fileOutputStream = null;
        File path = new File(finallUrl);
        if (!path.exists()){
            try {
                path.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        finallFile=path;
        try {
            fileOutputStream=new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //遍历文件列表
        for (int i=0;i<size;i++){
            File file=new File(recorderList.get(i));
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] myByte = new byte[fileInputStream.available()];
                //文件长度
                int length = myByte.length;
                if(i==0){
                    while (fileInputStream.read(myByte)!=-1){
                        fileOutputStream.write(myByte,0,length);
                    }
                }else {
                    while (fileInputStream.read(myByte)!=-1){
                        fileOutputStream.write(myByte,6, length-6);
                    }
                }
                fileOutputStream.flush();
                fileInputStream.close();
                System.out.println("合成文件长度："+path.length());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            int len = recorderList.size();
            if(len>0){
                for (String fileName:recorderList){
                    File file=new File(fileName);
                    if(file.exists()){
                        file.delete();
                    }
                }
            }
            recorderList.clear();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        audioStatusUpdateListener.onStop(path.getAbsolutePath());
    }

    //    暂停录音
    public void pauseRecord() {
        audioStatusUpdateListener.onUpdate(1, startTime);
        LogUtils.logE(TAG,"暂停录音");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mMediaRecorder.pause();
        }else {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        mHandler.removeCallbacks(mUpdateMicStatusTimer);
    }

    //重新录音
    public void reRecord() {
        LogUtils.logE(TAG,"重新录音");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mMediaRecorder.resume();
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }else {
            startRecord();
        }
    }

    public void deleteRecoder(){
        if(mMediaRecorder!=null) {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
        filePath="";
        startTime=0;
        audioStatusUpdateListener.onUpdate(1, startTime);
        int size = recorderList.size();
        try {
            if(size>0){
                for (String fileName:recorderList){
                    File file=new File(fileName);
                    if(file.exists()){
                        file.delete();
                    }
                }
            }
            if(finallFile!=null&&finallFile.exists()){
                finallFile.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        recorderList.clear();
    }
    public void destroyAudio(){
        try {
            if(mMediaRecorder!=null) {
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 更新麦克状态
     */
    private void updateMicStatus() {

        if (mMediaRecorder != null) {
            double ratio = (double) mMediaRecorder.getMaxAmplitude() / BASE;
            double db = 0;// 分贝
            if (ratio > 1) {
                db = 20 * Math.log10(ratio);
            }
            if (null != audioStatusUpdateListener) {
                audioStatusUpdateListener.onUpdate(db, startTime);
            }
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
            startTime=startTime+1;
        }
    }


    public interface OnAudioStatusUpdateListener {
        /**
         * 录音中...
         *
         * @param db   当前声音分贝
         * @param time 录音时长
         */
        public void onUpdate(double db, long time);

        /**
         * 停止录音
         *
         * @param filePath 保存路径
         */
        public void onStop(String filePath);
    }
}
