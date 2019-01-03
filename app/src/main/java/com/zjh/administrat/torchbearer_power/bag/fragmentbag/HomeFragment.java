package com.zjh.administrat.torchbearer_power.bag.fragmentbag;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.adapterbag.HomeDataMlssAdapter;
import com.zjh.administrat.torchbearer_power.bag.adapterbag.HomeDataPzshAdapter;
import com.zjh.administrat.torchbearer_power.bag.adapterbag.HomeDataRxxpAdapter;
import com.zjh.administrat.torchbearer_power.bag.beanbag.HomeBannerBean;
import com.zjh.administrat.torchbearer_power.bag.beanbag.HomeDataBean;
import com.zjh.administrat.torchbearer_power.bag.beanbag.HomeSearchBean;
import com.zjh.administrat.torchbearer_power.bag.presenterbag.IPresenterImpl;
import com.zjh.administrat.torchbearer_power.bag.utilsbag.Apis;
import com.zjh.administrat.torchbearer_power.bag.utilsbag.RetrofitManager;
import com.zjh.administrat.torchbearer_power.bag.viewbag.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements IView {
    private IPresenterImpl iPresenter;
    private Banner banner;
    private RecyclerView rxxp_recycleView, mlss_recycleView, pzsh_recycleView;
    private HomeDataRxxpAdapter rxxpAdapter;
    private HomeDataMlssAdapter mlssAdapter;
    private HomeDataPzshAdapter pzshAdapter;
    private int mSpanCount = 2;
    private ImageView home_srarch;  //放大镜
    private TextView search_btn;    //搜索
    private EditText edit_home;     //输入框
    private NestedScrollView scrollView; //滑动层
    private RelativeLayout home_relative;  //静态fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter = new IPresenterImpl(this);
        banner = view.findViewById(R.id.common_banner);
        home_srarch = view.findViewById(R.id.home_search);
        search_btn = view.findViewById(R.id.search_btn);
        edit_home = view.findViewById(R.id.edit_home);
        scrollView = view.findViewById(R.id.scrollView);
        home_relative = view.findViewById(R.id.home_relative);
        //首页搜索框
        home_srarch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home_srarch.setVisibility(View.INVISIBLE);
                home_relative.setVisibility(View.VISIBLE);
            }
        });
        //获取资源id
        rxxp_recycleView = view.findViewById(R.id.rxxp_recycleView);
        mlss_recycleView = view.findViewById(R.id.mlss_recycleView);
        pzsh_recycleView = view.findViewById(R.id.pzsh_recycleView);
        //处理滑动不显示冲突
        rxxp_recycleView.setNestedScrollingEnabled(false);
        mlss_recycleView.setNestedScrollingEnabled(false);
        pzsh_recycleView.setNestedScrollingEnabled(false);
        initBanner();  //轮播
        initDataAll();  //首页
        initSearch();   //搜索kuang
    }

    //上方轮播图
    private void initBanner() {
        //返回轮播的数据
        iPresenter.pgetRequest(Apis.UEL_BANNER_GET, HomeBannerBean.class);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });
    }

    //首页所有商品
    private void initDataAll() {
        iPresenter.pgetRequest(Apis.URL_HOMEDATA_GET, HomeDataBean.class);
        rxxpAdapter = new HomeDataRxxpAdapter(getActivity());
        mlssAdapter = new HomeDataMlssAdapter(getActivity());
        pzshAdapter = new HomeDataPzshAdapter(getActivity());
        rxxp_recycleView.setAdapter(rxxpAdapter);
        mlss_recycleView.setAdapter(mlssAdapter);
        pzsh_recycleView.setAdapter(pzshAdapter);

        //热销新品
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        rxxp_recycleView.setLayoutManager(linearLayoutManager);
        //魔力时尚
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(OrientationHelper.VERTICAL);
        mlss_recycleView.setLayoutManager(linearLayoutManager1);
        //品质生活
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), mSpanCount);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        pzsh_recycleView.setLayoutManager(gridLayoutManager);
    }

    //搜索框 查询商品
    private void initSearch() {
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_value = edit_home.getText().toString().trim();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("search_value", search_value);
                editor.commit();
                scrollView.setVisibility(View.INVISIBLE);
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeSearchFragment searchFragment = new HomeSearchFragment();
                fragmentTransaction.add(R.id.search_fragment_framelayout, searchFragment);
                fragmentTransaction.commit();

            }
        });


    }



    //请求回传数据
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof HomeBannerBean) {
           HomeBannerBean bannerBean = (HomeBannerBean) data;
            ArrayList list = new ArrayList();
            for (int i = 0; i < bannerBean.getResult().size(); i++) {
                list.add(bannerBean.getResult().get(i).getImageUrl());
            }
            banner.setImages(list);
            banner.start();
        }else if (data instanceof HomeDataBean){
            HomeDataBean dataBean = (HomeDataBean) data;
            rxxpAdapter.setRxxpData(dataBean.getResult().getRxxp().get(0).getCommodityList());
            mlssAdapter.setMlssData(dataBean.getResult().getMlss().get(0).getCommodityList());
            pzshAdapter.setPzshData(dataBean.getResult().getPzsh().get(0).getCommodityList());
        }

    }


    /*
     * 处理内存
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
