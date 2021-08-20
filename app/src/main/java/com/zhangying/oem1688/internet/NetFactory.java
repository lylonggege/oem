package com.zhangying.oem1688.internet;


import com.zhangying.oem1688.constant.BuildConfig;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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
            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.URL)
                    .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory(AndroidSchedulers.mainThread()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .client(OkHttpClientProvider.client())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
}
