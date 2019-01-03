package com.zjh.administrat.torchbearer_power.bag.presenterbag;

import android.support.v4.app.NavUtils;
import android.util.Log;

import com.zjh.administrat.torchbearer_power.bag.callbackbag.MyCallBack;
import com.zjh.administrat.torchbearer_power.bag.modelbag.IModel;
import com.zjh.administrat.torchbearer_power.bag.modelbag.IModelImpl;
import com.zjh.administrat.torchbearer_power.bag.viewbag.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelImpl iModel;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }


    /**
     *   post请求p层
     * */
    @Override
    public void pstartRequest(String url, Map<String, String> params, Class clazz) {
        iModel.mrequestData(url, params, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }
            @Override
            public void OnFail(String error) {

            }
        });
    }

    /*
    *   get请求
    * */
    @Override
    public void pgetRequest(String url, Class clazz) {
        iModel.getRequestData(url, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewDataSuccess(data);
            }

            @Override
            public void OnFail(String error) {

            }
        });
    }







    /**
     * 内存处理
     */
    public void onDetch(){
        if (iView != null){
            iView = null;
        }
        if (iModel != null){
            iModel = null;
        }
    }


}
