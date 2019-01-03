package com.zjh.administrat.torchbearer_power.bag.presenterbag;

import java.util.Map;

public interface IPresenter {
    /**
     *   post请求
     * */
    void pstartRequest(String url, Map<String, String> params, Class clazz);
    /**
     *   get请求
     * */
    void pgetRequest(String url, Class clazz);

}
