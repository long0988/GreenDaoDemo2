package com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.randomcode.RandomCodeUtils;

import java.util.Random;

public class RandomCodeActivity extends BaseActivity {
    ImageView mCodeImg;
    EditText et;
    Button bt;
    String mCode;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setContentView(R.layout.activity_randomcode);
        mCodeImg = (ImageView) findViewById(R.id.img);
        bt = (Button) findViewById(R.id.reset);
        et = (EditText) findViewById(R.id.et);
        RandomCodeUtils.getInstance().setRandStr("trP129");
        mCodeImg.setImageBitmap(RandomCodeUtils.getInstance().createBitmap());
        mCode=RandomCodeUtils.getInstance().getCode();
        System.out.println("验证码："+mCode);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RandomCodeUtils.getInstance().setRandStr("trP12"+new Random().nextInt(10));
                mCodeImg.setImageBitmap(RandomCodeUtils.getInstance().createBitmap());
                mCode=RandomCodeUtils.getInstance().getCode();
                System.out.println("验证码：" + mCode);
            }
        });
        mCodeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RandomCodeUtils.getInstance().setRandStr("trP12"+new Random().nextInt(10));
                mCodeImg.setImageBitmap(RandomCodeUtils.getInstance().createBitmap());
                mCode=RandomCodeUtils.getInstance().getCode();
                System.out.println("验证码：" + mCode);
            }
        });
    }


    public void lowerLetter(View view) {
        System.out.println("验证码：mCode--" + mCode.toLowerCase());
        String s = et.getText().toString();
        String s1 = s.toLowerCase();
        System.out.println("验证码：-----s1" + s1);
    }
}
