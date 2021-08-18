package com.juguo.magazine.remote;

import android.content.Context;
import android.content.SharedPreferences;

import com.juguo.magazine.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    SharedPreferences sp = App
            .getContext()
            .getSharedPreferences("sp", Context.MODE_PRIVATE);

    @Override
    public Response intercept(Chain chain) throws IOException {
        String result_token = sp.getString("result_token", "");

        Request request = chain.request();
        Request build = request.newBuilder()
                .addHeader("Authorization", result_token)
                .build();
        return chain.proceed(build);
    }
}
