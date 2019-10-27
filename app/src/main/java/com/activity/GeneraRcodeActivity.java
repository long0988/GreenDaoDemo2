package com.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by qlshi on 2018/9/17.
 */

public class GeneraRcodeActivity extends BaseActivity {
    private ImageView mChineseIv;
    private ImageView mEnglishIv;
    private ImageView mBarcodeWithContentIv;
    private ImageView mBarcodeWithoutContentIv;
    private Toolbar mToolbar;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_generarcode);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("二维码生成");
        //mToolbar.setSubtitle("条码");
        setSupportActionBar(mToolbar);
        initView();
        createQRCode();
    }

    private void initView() {
        mChineseIv = findViewById(R.id.iv_chinese);
        mEnglishIv = findViewById(R.id.iv_english);
        mBarcodeWithContentIv = findViewById(R.id.iv_barcode_with_content);
        mBarcodeWithoutContentIv = findViewById(R.id.iv_barcode_without_content);
    }

    private void createQRCode() {
        //生成不带logo图的二维码
        new MyAsyncTask(this, "我热爱学习", mChineseIv, 0).execute();
        //生成带logo图的二维码
        new MyAsyncTask(this, "I like study", mEnglishIv, R.mipmap.ic_launcher).execute();
        //生成条形码
        new MyContentAsyncTask(this, "kkkk123456", mBarcodeWithContentIv, 16, false).execute();
        new MyContentAsyncTask(this, "gggwrite", mBarcodeWithoutContentIv, 16, true).execute();
    }

    public void decodeChinese(View view) {
        mChineseIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mChineseIv.getDrawingCache();
        decode(bitmap, "解析二维码失败");
    }

    public void decodeEnglish(View view) {
        mEnglishIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mEnglishIv.getDrawingCache();
        decode(bitmap, "解析二维码失败");
    }

    public void decodeBarcodeWithContent(View view) {
        mBarcodeWithContentIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mBarcodeWithContentIv.getDrawingCache();
        decode(bitmap, "识别底部带文字的条形码失败");
    }

    public void decodeBarcodeWithoutContent(View view) {
        mBarcodeWithoutContentIv.setDrawingCacheEnabled(true);
        Bitmap bitmap = mBarcodeWithoutContentIv.getDrawingCache();
        decode(bitmap, "识别底部没带文字的条形码失败");
    }

    private void decode(final Bitmap bitmap, final String errorTip) {
        String codeMesg= QRCodeDecoder.syncDecodeQRCode(bitmap);
        Toast.makeText(this,codeMesg,Toast.LENGTH_SHORT).show();
    }

    //二维码生成
    private class MyAsyncTask extends AsyncTask<Void, Integer, Bitmap> {
        private WeakReference<Context> weakReference;
        private Context mContext;
        private ImageView mCodeIv;
        private int mLogoIv = 0;
        private String mContent;

        public MyAsyncTask(Context context, String content, ImageView iv, int ic_launcher) {
            //weakReference = new WeakReference<Context>(context);
            weakReference = new WeakReference<>(context);
            this.mCodeIv = iv;
            this.mLogoIv = ic_launcher;
            this.mContent = content;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContext = weakReference.get();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            if (mLogoIv != 0) {
                Bitmap logoBitmap = BitmapFactory.decodeResource(mContext.getResources(), mLogoIv);
                return QRCodeEncoder.syncEncodeQRCode(mContent, BGAQRCodeUtil.dp2px(mContext, 150), Color.parseColor("#ff0000"), logoBitmap);
            }
            return QRCodeEncoder.syncEncodeQRCode(mContent, BGAQRCodeUtil.dp2px(mContext, 150));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mCodeIv.setImageBitmap(bitmap);
            } else {
                Toast.makeText(mContext, "生成二维码失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //条形码生成
    private  class MyContentAsyncTask extends AsyncTask<Void, Integer, Bitmap> {
        private WeakReference<Context> weakReference;
        private Context mContext;
        private ImageView mCodeIv;
        private String mContent;
        private boolean mShowText;
        private int mTextSIze;

        public MyContentAsyncTask(Context context, String content, ImageView iv, int textSIze, boolean showText) {
            //weakReference = new WeakReference<Context>(context);
            weakReference = new WeakReference<>(context);
            this.mCodeIv = iv;
            this.mContent = content;
            this.mTextSIze = textSIze;
            this.mShowText = showText;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContext = weakReference.get();
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            int width = BGAQRCodeUtil.dp2px(GeneraRcodeActivity.this, 150);
            int height = BGAQRCodeUtil.dp2px(GeneraRcodeActivity.this, 70);
            int textSize = 0;
            if (mShowText) {
                textSize = BGAQRCodeUtil.sp2px(GeneraRcodeActivity.this, mTextSIze);
            }

            return QRCodeEncoder.syncEncodeBarcode(mContent, width, height, textSize);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mCodeIv.setImageBitmap(bitmap);
            } else {
                Toast.makeText(mContext, "生成条形码失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
