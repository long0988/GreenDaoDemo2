package com.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.BaseActivity.BaseActivity;
import com.greendaodemo2.R;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.utils.ImageUtil;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.List;

/**
 * Created by qlshi on 2018/8/22.
 */

public class ScannerActivity extends BaseActivity {
    private Button mScanner, mScanner2;
    private int SCANN_CODE = 0x001;
    private int REQUEST_IMAGE = 0x002;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_scanner);
        initRequestPermissions();
        mScanner = (Button) findViewById(R.id.start_scanner);
        mScanner2 = (Button) findViewById(R.id.start_scanner2);
        mScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScannerActivity.this, CaptureActivity.class);
                startActivityForResult(intent, SCANN_CODE);
            }
        });
        mScanner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
    }

    private void initRequestPermissions() {
        //XXPermissions.gotoPermissionSettings(this);
        XXPermissions.with(this)
                .constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(Permission.REQUEST_INSTALL_PACKAGES) //支持8.0及以上请求安装权限
                .permission(Permission.SYSTEM_ALERT_WINDOW) //支持请求6.0及以上悬浮窗权限
                //.permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
                .permission(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {

                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        Toast.makeText(ScannerActivity.this,"拒绝权限将导致应用部分功能不能使用",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCANN_CODE) {
            if (data != null && resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Log.e("eeeeee", "result--" + result);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (requestCode == REQUEST_IMAGE) {
            if (data != null && resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    //Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片
                    String imageAbsolutePath = ImageUtil.getImageAbsolutePath(this, uri);
                    CodeUtils.analyzeBitmap(imageAbsolutePath, new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(ScannerActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ScannerActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
//
//                    if (mBitmap != null) {
//                        mBitmap.recycle();
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
