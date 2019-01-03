package com.zjh.administrat.torchbearer_power.bag.callbackbag;

public interface MyCallBack<T> {
    void OnSuccess(T data);
    void OnFail(String error);
}
