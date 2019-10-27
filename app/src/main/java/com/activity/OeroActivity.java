package com.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import com.BaseActivity.BaseActivity;
import com.utils.FileProviderUtil;
import com.utils.ToastUtil;

import java.io.File;

/**
 * Created by qlshi on 2018/8/23.
 */

public class OeroActivity extends BaseActivity {
    private boolean installPermission;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        initInstall();
    }

    private void initInstall() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            installPermission = getPackageManager().canRequestPackageInstalls();
            if (!installPermission) {
                ToastUtil.ShowDialog(this, "安装应用需要打开未知来源权限，请去设置中开启权限", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startSettingActivity();
                    }
                }, null);
            }
        } else {
            inStallApk();
        }
    }

    private void inStallApk() {
        File apk = new File("");
        FileProviderUtil.installApk(this, apk);
    }

    private void startSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10086);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == Activity.RESULT_OK) {
            initInstall();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
