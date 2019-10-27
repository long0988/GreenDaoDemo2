package com.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class FileProviderUtil {
    /**
     * @param context
     * @param authorities manifest配置的(一般包名加个后缀)
     * @param file
     * @return
     */
    protected static Uri getFileProviderUri(Context context, File file) {
        String pakeNmae = getPakeNmae(context);
        Uri uriForFile = FileProvider.getUriForFile(context, pakeNmae + ".fileprovider", file);
        if (uriForFile != null) {
            return uriForFile;
        } else {
            return null;
        }
    }

    public static String getPakeNmae(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.packageName;
    }

    //安装应用
    public static void installApk(Context context, File apk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        } else {
            //Android7.0之后获取uri要用contentProvider
            Uri uri = getFileProviderUri(context, apk);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
