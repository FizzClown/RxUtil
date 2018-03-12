package com.lq.mylibrary;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lq
 *         Created by lq on 2017/12/19.
 */

public class RequestMap {
    private static String TAG = "RequestMap";

    public static Map<String, String> parseDataNoZero(Object data, Type typeOfSrc) {
        Gson gson = new Gson();
        String json = gson.toJson(data, typeOfSrc);

        Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());

        Iterator<String> iter = map.keySet().iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            if ("-1".equals(map.get(key)) || "0.0".equals(map.get(key))) {
                //   map.remove(key);  // java.util.ConcurrentModificationException
                iter.remove();
            }
        }
        Log.i(TAG, "parseData: " + json);
        for (int i = 0; i < map.size(); i++) {
            Log.i(TAG, "parseData: 键   值 : " + map.keySet() + "  " + map.values());
        }
        return map;
    }
}
