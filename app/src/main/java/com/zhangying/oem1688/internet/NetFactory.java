package com.zhangying.oem1688.internet;


import com.zhangying.oem1688.constant.BuildConfig;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xlh
 * @date 2018/9/22.
 */
public class NetFactory {
    private static final Retrofit RETROFIT = new MRetrofit().getInstance();

    public static Retrofit getRetrofit() {
        return RETROFIT;
    }

    private static class MRetrofit {

        Retrofit getInstance() {
            OkHttpClient client = OkHttpClientProvider.client();

//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(new AddCookiesInterceptor())
//                    .addInterceptor(new ReceivedCookiesInterceptor())
//                    .connectTimeout(30, TimeUnit.SECONDS).build();

            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.URL)
                    .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
}
