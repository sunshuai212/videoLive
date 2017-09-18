package com.aihuishou.httplib.retrofit;


import com.aihuishou.httplib.HttpBaseContext;
import com.aihuishou.httplib.utils.LogUtil;
import com.aihuishou.httplib.utils.SignRequestUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


/**
 * 类名称：OkHttp3Utils
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/11/22 15:59
 * 描述：okHttp的配置
 */
public class OkHttp3Utils {
    //读超时长，单位：秒
    public static final int READ_TIME_OUT = 10;
    public static final int WRITE_TIME_OUT = 10;
    //连接时长，单位：秒
    public static final int CONNECT_TIME_OUT = 10;
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {
        LogInterceptor logInterceptor = new LogInterceptor();

        File cacheFile = new File(HttpBaseContext.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //增加头部信息
        Interceptor headerInterceptor =new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(SignRequestUtils.signRequest(chain.request()));
            }
        };
        if (null == mOkHttpClient) {
            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
//                    .addInterceptor(headerInterceptor)
                    .addInterceptor(logInterceptor)
                    .sslSocketFactory(HttpsUtils.getSslSocketFactory(null, null, null))
                    .cache(cache)
                    .build();
        }

        return mOkHttpClient;
    }

    public static class LogInterceptor implements Interceptor {
        private static final String F_BREAK = " %n";
        private static final String F_URL = " %s";
        private static final String F_TIME = " in %.1fms";
        private static final String F_HEADERS = "%s";
        private static final String F_RESPONSE = F_BREAK + "Response: %d";
        private static final String F_BODY = "body: %s";

        private static final String F_BREAKER = F_BREAK + "-------------------------------------------" + F_BREAK;
        private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS;
        private static final String F_RESPONSE_WITHOUT_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BREAKER;
        private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADERS + F_BODY + F_BREAK;
        private static final String F_RESPONSE_WITH_BODY = F_RESPONSE + F_BREAK + F_HEADERS + F_BODY + F_BREAK + F_BREAKER;

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long t1 = System.nanoTime();
                Response response = chain.proceed(chain.request());
                long t2 = System.nanoTime();
                MediaType contentType = null;
                String bodyString = null;
                String bodyLog = "";
                if (response.body() != null) {
                    contentType = response.body().contentType();
                    bodyString = response.body().string();
                    bodyLog = stringifyResponseBody(bodyString);
                }
                // 请求响应时间
                double time = (t2 - t1) / 1e6d;
                switch (request.method()) {
                    case "GET":
                        LogUtil.d("retrofit--> "+
                                String.format("GET " + F_REQUEST_WITHOUT_BODY  + F_RESPONSE_WITH_BODY,
                                        request.url(),
                                        time,
                                        request.headers(),
                                        response.code(),
                                        response.headers(),
                                        bodyLog));
                        break;
                    case "POST":
                        LogUtil.d("retrofit--> " +
                                String.format("POST " + F_REQUEST_WITH_BODY  + F_RESPONSE_WITH_BODY,
                                        request.url(),
                                        time,
                                        request.headers(),
                                        stringifyRequestBody(request),
                                        response.code(),
                                        response.headers(),
                                        bodyLog));
                        break;
                    case "PUT":
                        LogUtil.d("retrofit--> "+
                                String.format("PUT " + F_REQUEST_WITH_BODY  + F_RESPONSE_WITH_BODY,
                                        request.url(),
                                        time,
                                        request.headers(),
                                        request.body().toString(),
                                        response.code(),
                                        response.headers(),
                                        bodyLog));
                        break;
                    case "DELETE":
                        LogUtil.d("retrofit--> "+
                                String.format("DELETE " + F_REQUEST_WITHOUT_BODY  + F_RESPONSE_WITHOUT_BODY,
                                        request.url(),
                                        time,
                                        request.headers(),
                                        response.code(),
                                        response.headers()));
                        break;
                }
                if (response.body() != null) {
                    // 打印body后原ResponseBody会被清空，需要重新设置body
                    ResponseBody body = ResponseBody.create(contentType, bodyString);
                    return response.newBuilder().body(body).build();
                } else {
                    return response;
                }
            }
            private static String stringifyRequestBody(Request request) {
                try {
                    final Request copy = request.newBuilder().build();
                    final Buffer buffer = new Buffer();
                    copy.body().writeTo(buffer);
                    return buffer.readUtf8();
                } catch (final IOException e) {
                    return "did not work";
                }
            }

            public String stringifyResponseBody(String responseBody) {
//                LogUtil.d("++ "+responseBody);
                return unicode2String(responseBody);
//                return JSON.toJSONString(responseBody);
//                try {
//                    return new String(responseBody.getBytes("gbk"),"utf-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                return responseBody;
            }
        /**
         * unicode 转字符串
         */
        public static String unicode2String(String utfString) {
            StringBuilder sb = new StringBuilder();
            int i = -1;
            int pos = 0;
            while((i=utfString.indexOf("\\u", pos)) != -1){
                sb.append(utfString.substring(pos, i));
                if(i+5 < utfString.length()){
                    pos = i+6;
                    sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
                }
            }
            sb.append(utfString.substring(pos));
            LogUtil.d("-- "+sb.toString());
            return sb.toString();
        }
    }


}
