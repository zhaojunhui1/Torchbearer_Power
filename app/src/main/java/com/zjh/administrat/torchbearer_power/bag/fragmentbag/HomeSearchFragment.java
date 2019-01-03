package com.zjh.administrat.torchbearer_power.bag.fragmentbag;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.adapterbag.SearchAdapter;
import com.zjh.administrat.torchbearer_power.bag.beanbag.HomeSearchBean;
import com.zjh.administrat.torchbearer_power.bag.presenterbag.IPresenterImpl;
import com.zjh.administrat.torchbearer_power.bag.utilsbag.Apis;
import com.zjh.administrat.torchbearer_power.bag.viewbag.IView;

public class HomeSearchFragment extends Fragment implements IView {
    private IPresenterImpl iPresenter;
    private RecyclerView search_recycleView;
    private SharedPreferences sharedPreferences;
    private String search_value;
    private SearchAdapter searchAdapter;
    private int mSpanCount = 2;
    private LinearLayout search_data, nohave_shop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_search_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter = new IPresenterImpl(this);
        search_recycleView = view.findViewById(R.id.search_recycleView);
        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        search_value = sharedPreferences.getString("search_value", "");
        search_data = view.findViewById(R.id.search_data);
        nohave_shop = view.findViewById(R.id.nohave_shop);
        initData();
    }
    //处理接口的拼接
    private void initData() {
        iPresenter.pgetRequest(String.format(Apis.URL_SEARCH_GET, search_value), HomeSearchBean.class);
        searchAdapter = new SearchAdapter(getActivity());
        search_recycleView.setAdapter(searchAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), mSpanCount);
        gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        search_recycleView.setLayoutManager(gridLayoutManager);
    }

    //返回的数据
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof HomeSearchBean){
            HomeSearchBean homeSearchBean = (HomeSearchBean) data;
            if (homeSearchBean.getStatus().equals("0000")){
                search_recycleView.setVisibility(View.VISIBLE);
                nohave_shop.setVisibility(View.INVISIBLE);
                searchAdapter.setDatas(homeSearchBean.getResult());
            }else {

            }
            if (homeSearchBean.getResult().size() == 0){
                search_recycleView.setVisibility(View.INVISIBLE);
                nohave_shop.setVisibility(View.VISIBLE);
            }else {

            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }
}
