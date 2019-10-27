package com.utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;

import com.constant.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioRecoderUtils {
    private static final String TAG = "AudioRecoderUtils";
    private static AudioRecoderUtils audioRecorder;
    //音频输入-麦克风
    private static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;
    //采用频率ENCODING_PCM_16BIT
    //44100是目前的标准(采用这个文件很大)，但是某些设备仍然支持22050，16000，11025
    //采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级
    private static int AUDIO_SAMPLE_RATE = 16000;
    //声道 单声道
    private static int AUDIO_CHANNEL = AudioFormat.CHANNEL_IN_MONO;
    //编码
    private static int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    // 缓冲区字节大小
    private int bufferSizeInBytes = 0;
    //录音对象
    private AudioRecord audioRecord;
    //录音状态
    private Status status = Status.STATUS_NO_READY;
    //文件名
    private String fileName;
    //录音文件
    private List<String> filesName = new ArrayList<>();
    //线程池
    private ExecutorService mExecutorService;
    //录音监听
    private RecordStreamListener mListener;
    //录音存储目录
    private String fileDir;
    //文件最终合成路径
    private String destinationPath;

    private int SPACE = 1000;// 间隔取样时间
    private final Handler mHandler = new Handler();
    private long startTime=0;

    public void setOnRecordStreamListener(RecordStreamListener listener) {
        this.mListener = listener;
    }
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    private void updateMicStatus() {
        if (null != mListener) {
            mListener.updateTime(startTime);
        }
        mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        startTime=startTime+1;
    }

    private AudioRecoderUtils() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    //单例模式
    public static AudioRecoderUtils getInstance() {
        if (audioRecorder == null) {
            audioRecorder = new AudioRecoderUtils();
        }
        return audioRecorder;
    }

    /**
     * 创建录音对象
     */
    public void createAudio(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat) {
        this.AUDIO_INPUT=audioSource;
        this.AUDIO_SAMPLE_RATE=sampleRateInHz;
        this.AUDIO_CHANNEL=channelConfig;
        this.AUDIO_ENCODING=audioFormat;
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
        File dir = MyCommonFileUtils.createDir(Constant.RECORD_PATH);
        this.fileDir=dir.getAbsolutePath()+"/";
        status = Status.STATUS_READY;
    }

    /**
     * 创建默认的录音对象
     */
    public void createDefaultAudio() {
        // 获得缓冲区字节大小
        bufferSizeInBytes = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE,
                AUDIO_CHANNEL, AUDIO_ENCODING);
        audioRecord = new AudioRecord(AUDIO_INPUT, AUDIO_SAMPLE_RATE, AUDIO_CHANNEL, AUDIO_ENCODING, bufferSizeInBytes);
        File dir = MyCommonFileUtils.createDir(Constant.RECORD_PATH);
        this.fileDir=dir.getAbsolutePath()+"/";
        status = Status.STATUS_READY;
    }

    /**
     * 开始录音
     */
    public void startRecord() {

        if (status == Status.STATUS_NO_READY ) {
            throw new IllegalStateException("录音尚未初始化,请检查是否禁止了录音权限~");
        }
        if (status == Status.STATUS_START) {
            throw new IllegalStateException("正在录音");
        }
        fileName = fileDir + System.currentTimeMillis() + ".pcm";
        if(audioRecord==null){
            createDefaultAudio();
        }
        Log.d("AudioRecorder","===startRecord==="+audioRecord.getState());
        audioRecord.startRecording();
        filesName.add(fileName);
        updateMicStatus();
//        Thread thread = new Thread(new WrireDataThread());
//        thread.start();
        //使用线程池管理线程
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                writeDataTOFile(fileName);
            }
        });
    }

    /**
     * 暂停录音
     */
    public void pauseRecord() {
        mExecutorService.isShutdown();
        mHandler.removeCallbacks(mUpdateMicStatusTimer);
        Log.d("AudioRecorder","===pauseRecord===");
        if (status != Status.STATUS_START) {
            throw new IllegalStateException("没有在录音");
        } else {
            audioRecord.stop();
            status = Status.STATUS_PAUSE;
        }
    }

    /**
     * 停止录音
     */
    public void stopRecord() {
        mExecutorService.isShutdown();
        mHandler.removeCallbacks(mUpdateMicStatusTimer);
        startTime=0;
        Log.d("AudioRecorder","===stopRecord===");
        if (status == Status.STATUS_NO_READY || status == Status.STATUS_READY) {
            throw new IllegalStateException("录音尚未开始");
        } else {
            audioRecord.stop();
            status = Status.STATUS_STOP;
            release();
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        Log.d("AudioRecorder","===release===");
        //假如有暂停录音
        try {
            if (filesName.size() > 0) {
                List<String> filePaths = new ArrayList<>();
                for (String fileName : filesName) {
                    filePaths.add(fileName);
                }
                //清除
                filesName.clear();
                //将多个pcm文件转化为wav文件
                mergePCMFilesToWAVFile(filePaths);

            } else {
                //这里由于只要录音过filesName.size都会大于0,没录音时fileName为null
                //会报空指针 NullPointerException
                // 将单个pcm文件转化为wav文件
                //Log.d("AudioRecorder", "=====makePCMFileToWAVFile======");
                //makePCMFileToWAVFile();
            }
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage());
        }

        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
        status = Status.STATUS_NO_READY;
    }

    /**
     * 取消录音
     */
    public void delete() {
        fileName = null;
        mExecutorService.isShutdown();
        mHandler.removeCallbacks(mUpdateMicStatusTimer);
        startTime=0;
        if (null != mListener) {
            mListener.updateTime(startTime);
        }
        if (audioRecord != null) {
            audioRecord.release();
            audioRecord = null;
        }
        if(destinationPath!=null){
            File file = new File(destinationPath);
            if(file.exists()){
                file.delete();
            }
        }
        status = Status.STATUS_READY;
        destinationPath=null;
        for(String fileName:filesName){
            File file = new File(fileName);
            if(file.exists()){
                file.delete();
            }
        }
        filesName.clear();
    }

    /**
     * 将音频信息写入文件
     * @param currentFileName
     */
    private void writeDataTOFile(String currentFileName) {

        FileOutputStream fos = null;
        int readsize = 0;
        try {
//            String currentFileName = fileName;
//            if (status == Status.STATUS_PAUSE) {
//                //假如是暂停录音,重新建一个文件
//                currentFileName = fileDir + System.currentTimeMillis() + ".pcm";
//            }
            File file = new File(currentFileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);// 建立一个可存取字节的文件
        } catch (IllegalStateException e) {
            Log.e("AudioRecorder", e.getMessage());
            throw new IllegalStateException(e.getMessage());
        } catch (FileNotFoundException e) {
            Log.e("AudioRecorder", e.getMessage());
        }
        //将录音状态设置成正在录音状态
        status = Status.STATUS_START;
        while (status == Status.STATUS_START) {
            // new一个byte数组用来存一些字节数据，大小为缓冲区大小
            byte[] audiodata = new byte[bufferSizeInBytes];
            if(audioRecord!=null) {
                readsize = audioRecord.read(audiodata, 0, audiodata.length);
                if (AudioRecord.ERROR_INVALID_OPERATION != readsize && fos != null) {
                    try {
                        fos.write(audiodata, 0, readsize);
                        if (mListener != null) {
                            //用于拓展业务
                            mListener.recordOfByte(audiodata, 0, audiodata.length);
                        }
                    } catch (IOException e) {
                        Log.e("AudioRecorder", e.getMessage());
                    }
                }
            }
        }
        try {
            if (fos != null) {
                fos.close();// 关闭写入流
            }
        } catch (IOException e) {
            Log.e("AudioRecorder", e.getMessage());
        }
    }

    /**
     * 将pcm合并成wav
     * @param filePaths
     */
    private void mergePCMFilesToWAVFile(final List<String> filePaths) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                long timeMillis = System.currentTimeMillis();
                destinationPath=fileDir+timeMillis+".wav";
                //mergePCMFilesToWAVFile这个必须设置采样频率为16000
//                if (PcmToWav.mergePCMFilesToWAVFile(filePaths, destinationPath)) {
                if (PcmToWav2.mergePCMFilesToWAVFile(AUDIO_SAMPLE_RATE,AUDIO_CHANNEL,AUDIO_ENCODING,filePaths, destinationPath)) {
                    if(mListener!=null){
                        mListener.audioPath(destinationPath);
                    }
                    //操作成功
                    LogUtils.logE(TAG,"录音合成成功");
//                    wavToM4a();
                } else {
                    mListener.audioPath(null);
                    //操作失败
                    Log.e("AudioRecorder", "mergePCMFilesToWAVFile fail");
                    throw new IllegalStateException("mergePCMFilesToWAVFile fail");
                }
                fileName = null;
            }
        });
    }

    /**
     * 获取录音对象的状态
     * @return
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 获取本次录音文件的个数
     * @return
     */
    public int getPcmFilesCount() {
        return filesName.size();
    }

    /**
     * 录音对象的状态
     */
    public  enum Status {
        //未开始
        STATUS_NO_READY,
        //预备
        STATUS_READY,
        //录音
        STATUS_START,
        //暂停
        STATUS_PAUSE,
        //停止
        STATUS_STOP
    }
    public interface RecordStreamListener {
        void recordOfByte(byte[] data, int begin, int end);
        void audioPath(String path);
        void updateTime(long time);
    }
}
