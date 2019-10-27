package com.utils;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class MyCommonFileUtils {

    /**
     * 判断是否有外部存储设备sdcard
     * @return true | false
     */
    public static boolean isSdcardExit() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 获取sd卡或内存存储路径
     *
     * @return
     */
    public static String getBaseDir() {
        String path =null;
        if (isSdcardExit()) {
            path = Environment.getExternalStorageDirectory().getPath();
        }
//        else {
//            path = Environment.getDataDirectory().getPath();
//        }
        return path;
    }

    /*文件是否存在*/
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    /* 目录是否存在 1*/
    public static boolean isDirExist(String dir) {
        File dirs = new File(dir);
        return dirs.isDirectory();
    }


    public static void deletefile(String delpath) throws FileNotFoundException, IOException {
        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {
                file.delete();
            } else if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "/" + filelist[i]);
                    if (!delfile.isDirectory())
                        delfile.delete();
                    else if (delfile.isDirectory())
                        deletefile(delpath + "/" + filelist[i]);
                }
            }
        } catch (FileNotFoundException e) {
        }
    }

    /**
     * 获取File
     *
     * @param path
     * @return
     */
    public static File getFile(String path, String name) {
        File dirs = new File(path);
        if (!dirs.isDirectory()) {
            if (!dirs.mkdirs()) {
                return null;
            }
        }
        File file = null;
        try {
            file = new File(dirs.getAbsolutePath() + "/" + name);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 创建目录
     *
     * @param path 目录路径
     */
    public static File createDir(String path) {
        String baseDir = getBaseDir();
        File dir=null;
        if(path.startsWith("/")){
            dir = new File(baseDir+path);
        }else {
            dir = new File(baseDir+"/"+path);
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 创建目录
     *
     * @param path 目录路径
     */
    public static File createDir2(String path) {
        File dir=null;
        if(path.startsWith("/")){
            dir = new File(path);
        }else {
            dir = new File("/"+path);
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    public static File createNewFile(String path) {
        String baseDir = getBaseDir();
        File file = null;
        if(path.startsWith("/")){
            file = new File(baseDir+path);
        }else {
            file = new File(baseDir+"/"+path);
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    public static File createNewFile2(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }


    /**
     * 删除文件
     *
     * @param path 文件的路径
     */
    public static void delFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    //流文件写到sd卡
    public static void writeStreamToFile(InputStream stream, File file) {
        try {
            //
            OutputStream output = null;
            try {
                output = new FileOutputStream(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            try {
                try {
                    final byte[] buffer = new byte[1024];
                    int read;

                    while ((read = stream.read(buffer)) != -1)
                        output.write(buffer, 0, read);

                    output.flush();
                } finally {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件的Uri
     *
     * @param path 文件的路径
     * @return
     */
    public static Uri getUriFromFile(String path) {
        File file = new File(path);
        return Uri.fromFile(file);
    }

    /**
     * 换算文件大小
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "未知大小";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

}
