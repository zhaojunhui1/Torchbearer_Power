package com.zjh.administrat.torchbearer_power.bag.utilsbag;

import com.zjh.administrat.torchbearer_power.bag.beanbag.UserIDBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface BaseApi<T> {

    @GET
    rx.Observable<ResponseBody> get(@Url String urlStr);

    @POST
    rx.Observable<ResponseBody> post(@Url String urlStr, @QueryMap Map<String, String> map);

    @Multipart
    @POST
    rx.Observable<ResponseBody> postFormBody(@Url String urlStr, @PartMap Map<String, RequestBody> requestBodyMap);


}
