package com.zjh.administrat.torchbearer_power.bag.modelbag;

import com.google.gson.Gson;
import com.zjh.administrat.torchbearer_power.bag.callbackbag.MyCallBack;
import com.zjh.administrat.torchbearer_power.bag.utilsbag.RetrofitManager;

import java.util.Map;

import okhttp3.RequestBody;

public class IModelImpl implements IModel {

   /*
   *    post请求
   * */
    @Override
    public void mrequestData(String url, Map<String, String> params, final Class clazz, final MyCallBack myCallBack) {
        //final Map<String, RequestBody> map = RetrofitManager.getInstance().generatrRequestBody(params);
        RetrofitManager.getInstance().post(url, params, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(myCallBack != null){
                        myCallBack.OnFail(e.getMessage());
                    }
                }
            }
            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.OnFail(error);
                }
            }
        });
    }


    /**
     *    get请求
     * */
    @Override
    public void getRequestData(String url, final Class clazz, final MyCallBack myCallBack) {
        RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(myCallBack != null){
                        myCallBack.OnSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String error) {
                if(myCallBack != null){
                    myCallBack.OnFail(error);
                }
            }
        });
    }

    /**
     *
     * */

}
