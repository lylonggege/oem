package com.zhangying.oem1688.internet;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.zhangying.oem1688.util.LogUtil;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by xialihao on 2018/12/3.
 */
public class OkHttpClientProvider {

    public static OkHttpClient client() {
        return Holder.okHttpClient;
    }


    private static class MyHolder {
        OkHttpClient mOkhttpClient = OkhttpManager.getInstance().build();

        private static OkHttpClient myOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .connectTimeout(50, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new ParamsInterceptor())
                .addInterceptor(new LoggerInterceptor())
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    private static class Holder {
        private static OkHttpClient okHttpClient =
                new OkHttpClient.Builder()
                        .readTimeout(50, TimeUnit.SECONDS)
                        .writeTimeout(50, TimeUnit.SECONDS)
                        .connectTimeout(50, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .addInterceptor(new ParamsInterceptor())
                        .addInterceptor(new LoggerInterceptor())
//                        .addInterceptor(new Interceptor() {
//                            @Override
//                            public Response intercept(Chain chain) throws IOException {
//                                Request request = chain.request();
//                                request = request.newBuilder()
//                                        .header("Authorization", "")
//                                        .build();
//                                return chain.proceed(request);
//                            }
//                        })
                        .build();

    }

    /**
     * 服务器传参需要，需在每个url接口添加token等相关信息
     */
    private static class ParamsInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            HttpUrl.Builder builder = request.url().newBuilder();
            HttpUrl httpUrl = builder
                    .build();
            request = request.newBuilder().url(httpUrl).build();
            return chain.proceed(request);
        }
    }

    private static class LoggerInterceptor implements Interceptor {
        private final JsonParser parser = new JsonParser();

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();
            String log = "";

            Response response = chain.proceed(request);
            if (request.method().equals("POST") && request.body().contentType() != null && request.body().contentType().type() != null
                    && !request.body()
                    .contentType()
                    .type()
                    .equals("multipart") && !request.url().toString().contains("sms/user/img")) {
                try {
                    Buffer buffer = new Buffer();
                    request.body().writeTo(buffer);
                    String body = buffer.readUtf8();
                    body = Utils.paramsToJson(body);
                    log = body;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            String srt2 = new String(response.body().bytes(), "UTF-8");

            if (request.method().equals("POST") && request.body() != null && request.body().contentType() != null
                    && !request.body()
                    .contentType()
                    .toString()
                    .equals("image/jpeg")) {
                LogUtil.e("URL==", "{\"url\":\""
                        + request.url().toString()
                        + "\",\"request\":"
                        + log
                        + ",\"response\":"
                        + srt2
                        + "}");
            } else {
                LogUtil.e("URL==", "{\"url\":\"" + request.url().toString() + "\",\"response\":" + srt2 + "}");
            }

            JsonElement rootElement = parser.parse(srt2);
            if (!rootElement.isJsonObject()) {
                throw new JsonParseException("Root is not JsonObject");
            }

            SimpleResult simpleResult = GsonProvider.gson().fromJson(srt2, SimpleResult.class);
            if (simpleResult.isError()) {
                throw new ResponseException(simpleResult);
            }


            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), srt2.getBytes("UTF-8")))
                    .build();
        }
    }

}
