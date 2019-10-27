package com.utils;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class FormatTimeUtil {
    private static long longTime=1000*60*60;

    public  static String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour+"："+minute+"："+second;
        return strtime;

    }
    public  static String formatLongToTimeStr(int l) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//这里想要只保留分秒可以写成"mm:ss"
        SimpleDateFormat formatter2 = new SimpleDateFormat("mm:ss");
         formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String strtime="00:00";
         if(l>=longTime){
             strtime = formatter.format(l);
         }else {
             strtime = formatter2.format(l);
         }
         return strtime;

    }
}
