package com.zjh.administrat.torchbearer_power.bag.utilsbag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.zjh.administrat.torchbearer_power.bag.utilsbag.Apis.BASE_UEL;

public class RetrofitManager {
    /***
     * 使用单例
     */
    private static volatile RetrofitManager retrofitManager;
    public static RetrofitManager getInstance(){
        if (retrofitManager == null){
            synchronized (RetrofitManager.class){
                retrofitManager = new RetrofitManager();
            }
        }
        return retrofitManager;
    }

    private BaseApi baseApi;

    /***
     * 日志拦截器，超时处理
     */
    public RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.retryOnConnectionFailure(true);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_UEL)
                .client(client)
                .build();
        baseApi = retrofit.create(BaseApi.class);

    }
    /***
     * 处理map集合的类型
     */
    public Map<String, RequestBody> generatrRequestBody(Map<String, String> requestDataMap){
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }


    /**
     * get请求
     */
    public void get(String url, HttpListener listener) {
        baseApi.get(url)
                //后台执行在哪个线程中
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                //设置我们的rxJava
                .subscribe(getObserver(listener));
    }

    /**
     * 表单post请求
     */
    public void postFormBody(String url, Map<String, RequestBody> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }

        baseApi.postFormBody(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }

    /**
     * 普通post请求
     */
    public void post(String url, Map<String, String> map, HttpListener listener) {
        if (map == null) {
            map = new HashMap<>();
        }
        baseApi.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(listener));
    }


    private Observer getObserver(final HttpListener mListener){
        rx.Observer observer = new rx.Observer<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if (mListener != null){
                        mListener.onSuccess(string);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (mListener != null){
                        mListener.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mListener != null) {
                    mListener.onFail(e.getMessage());
                }
            }

        };
        return observer;
    }

    /***
     * 成员变量
     */
    private HttpListener mListener;
    /***
     * set方法
     */
    public void setHttpLinstener(HttpListener httpLinstener) {
        this.mListener = httpLinstener;
    }
    /***
     * 定义接口
     */
    public interface HttpListener {
        void onSuccess(String data);
        void onFail(String error);
    }

}
