package com.net.markj.retrofit.util;

import com.google.gson.Gson;

/**
 * Created by Kron Xu on 2018/7/2
 */

public class JsonUtils {
    private JsonUtils() {}

    public static <T> T fromJson(String json, Class<T> tClass) {
        return new Gson().fromJson(json, tClass);
    }
}
