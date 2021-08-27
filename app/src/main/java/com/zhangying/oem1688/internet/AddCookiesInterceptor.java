package com.zhangying.oem1688.internet;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhangying.oem1688.singleton.MapCookieSingleton;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {
//    private Context context;
//    public AddCookiesInterceptor(Context context) {
//        super();
//        this.context = context;
//    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = MapCookieSingleton.getInstance();
        if (preferences != null) {
            for (String cookie : preferences) {
                builder.addHeader("Cookie", cookie);
            }
        }
            return chain.proceed(builder.build());
    }
}
