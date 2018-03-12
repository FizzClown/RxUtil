package com.lq.mylibrary;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaUtil {
    private static Gson gson;
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create(buildGson());
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();
    public static Retrofit.Builder retrofitBuilder = new Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxJavaCallAdapterFactory);

    //解决服务器返回int，double，float类型为""的情况
    private static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(Integer.class, new ResolveIntNull())
                    .registerTypeAdapter(int.class, new ResolveIntNull())
                    .registerTypeAdapter(Double.class, new ResolveDoubleNull())
                    .registerTypeAdapter(double.class, new ResolveDoubleNull())
                    .create();
        }
        return gson;
    }
}
