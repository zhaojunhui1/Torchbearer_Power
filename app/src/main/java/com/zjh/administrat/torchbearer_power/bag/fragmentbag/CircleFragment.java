package com.zjh.administrat.torchbearer_power.bag.fragmentbag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.adapterbag.CircleAdapter;
import com.zjh.administrat.torchbearer_power.bag.beanbag.CircleBeanLB;
import com.zjh.administrat.torchbearer_power.bag.beanbag.UserIDBean;
import com.zjh.administrat.torchbearer_power.bag.presenterbag.IPresenterImpl;
import com.zjh.administrat.torchbearer_power.bag.utilsbag.Apis;
import com.zjh.administrat.torchbearer_power.bag.viewbag.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CircleFragment extends Fragment implements IView {
    private IPresenterImpl iPresenter;
    private RecyclerView recycleView;
    private int userid;
    private String sessionid;
    private CircleAdapter circleAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_circle, container, false);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus接收数据
        EventBus.getDefault().register(this);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter = new IPresenterImpl(this);
        recycleView = view.findViewById(R.id.circle_recycleView);
        initData();
    }
    //EventBus接收数据
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBus(UserIDBean userIDBean) {
        userid = userIDBean.userid;
        sessionid = userIDBean.sessionid;
    }


    //获取数据参数
    private void initData() {
        iPresenter.pgetRequest(Apis.URL_Circle_GET, CircleBeanLB.class);
        circleAdapter = new CircleAdapter(getActivity());
        recycleView.setAdapter(circleAdapter);

        //圈子
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
    }

    //请求返回的数据
    @Override
    public void viewDataSuccess(Object data) {
        if (data instanceof CircleBeanLB){
            CircleBeanLB circleBeanLB = (CircleBeanLB) data;
            circleAdapter.setDataAll(circleBeanLB.getResult());
        }
    }

    /*
     * 处理内存
     * */
    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
        //EventBus反注册
        EventBus.getDefault().unregister(this);
    }

}
