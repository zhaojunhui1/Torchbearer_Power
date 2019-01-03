package com.zjh.administrat.torchbearer_power.bag.modelbag;

import com.zjh.administrat.torchbearer_power.bag.callbackbag.MyCallBack;

import java.util.Map;

public interface IModel {
    //post请求
    void mrequestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
    //get请求
    void getRequestData(String url, Class clazz, MyCallBack myCallBack);

}
