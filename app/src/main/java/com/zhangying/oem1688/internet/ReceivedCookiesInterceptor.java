package com.zhangying.oem1688.internet;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhangying.oem1688.singleton.MapCookieSingleton;

import java.io.IOException;
import java.util.HashSet;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
//    private Context context;
//    public ReceivedCookiesInterceptor(Context context) {
//        super();
//        this.context = context;
//    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            for (String header : originalResponse.headers("Set-Cookie")) {
                MapCookieSingleton.getInstance().add(header);
            }
        }

        return originalResponse;
    }
}
