package com.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * <读取Json文件的工具类>
 */

public class GetJsonDataUtil {
    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public static <T> List<T> parseData(String JsonData, T t){
        ArrayList<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(JsonData, new TypeToken<List<T>>() {
            }.getType());
//            JSONArray data = new JSONArray(result);
//            for (int i = 0; i < data.length(); i++) {
//                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
//                detail.add(entity);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

