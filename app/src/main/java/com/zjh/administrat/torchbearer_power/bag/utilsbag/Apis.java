package com.zjh.administrat.torchbearer_power.bag.utilsbag;

public class Apis {
    /***
     * \域名头
     */
    public static final String BASE_UEL = "http://172.17.8.100/small/";
    /**
     *
    //注册*/
    public static final String URL_REGISTER_POST = "user/v1/register";
    /*/
    *
    * /登录*/
    public static final String URL_LOGIN_POST = "user/v1/login";
    /*
    *  首页轮播
    * */
    public static final String UEL_BANNER_GET = "commodity/v1/bannerShow";
    /*
    *  首页商品
    * */
    public static final String URL_HOMEDATA_GET = "commodity/v1/commodityList";
    /*
    *   首页搜索商品
    * */
    public static final String URL_SEARCH_GET = "commodity/v1/findCommodityByKeyword?page=1&count=5&keyword=%s";


    /*
     *  圈子首页
     * */
    public static final String URL_Circle_GET = "circle/v1/findCircleList?page=1&count=5";


}
