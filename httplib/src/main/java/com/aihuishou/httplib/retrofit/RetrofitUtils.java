package com.aihuishou.httplib.retrofit;

import com.aihuishou.httplib.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 *封装一个retrofit集成0kHttp3的抽象基类
 */
public abstract class RetrofitUtils {

    private static Retrofit mRetrofit = null;
    private static OkHttpClient mOkHttpClient = null;
    /**
     * 获取Retrofit对象
     *
     * @return
     */
    protected static Retrofit getRetrofit() {
        if (null == mRetrofit) {
            mRetrofit = initRetrofitBuilder().build();
        }
        return mRetrofit;
    }

    public static Retrofit getRetrofit(String baseUrl){
        if (null == mRetrofit) {
            mRetrofit = initRetrofitBuilder(baseUrl).build();
        }else if (!mRetrofit.baseUrl().url().url().toString().equals(baseUrl)){
            mRetrofit = initRetrofitBuilder(baseUrl).build();
        }
        return mRetrofit;
    }

    private static Retrofit.Builder  initRetrofitBuilder(){
        return initRetrofitBuilder("");
    }

    private static Retrofit.Builder  initRetrofitBuilder(String baseUrl){
        if (!baseUrl.endsWith("/")){
            baseUrl = baseUrl +"/";
        }
        if (null == mOkHttpClient) {
            mOkHttpClient = OkHttp3Utils.getOkHttpClient();
        }

        LogUtil.d("baseUrl:"+baseUrl);
        //Retrofit2后使用build设计模式
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();
        Retrofit.Builder builder = new Retrofit.Builder()
                                //设置服务器路径
                                .baseUrl(baseUrl)
                                //添加转化库，默认是Gson
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                //添加回调库，采用RxJava
                                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                //设置使用okhttp网络请求
                                .client(mOkHttpClient);
        return builder;
    }
}
