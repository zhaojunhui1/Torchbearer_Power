package com.zjh.administrat.torchbearer_power.bag.activitybag;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hjm.bottomtabbar.BottomTabBar;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.fragmentbag.BillFragment;
import com.zjh.administrat.torchbearer_power.bag.fragmentbag.CircleFragment;
import com.zjh.administrat.torchbearer_power.bag.fragmentbag.HomeFragment;
import com.zjh.administrat.torchbearer_power.bag.fragmentbag.MyFragment;
import com.zjh.administrat.torchbearer_power.bag.fragmentbag.ShopCarFragment;

import java.util.ArrayList;

public class AllFragmentActivity extends AppCompatActivity {

    private ViewPager common_viewPager;
    //private BottomBar mBottomTabBar;
    private BottomTabBar mBottomTabBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_fragment);
        initView();
        initData();
    }

    //初始化view视图
    private void initView() {
        common_viewPager = findViewById(R.id.common_viewPager);
        mBottomTabBar = findViewById(R.id.mBottomTabBar);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CircleFragment());
        fragments.add(new ShopCarFragment());
        fragments.add(new BillFragment());
        fragments.add(new MyFragment());
        //适配fragment
        common_viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
/*
        //滑动状态
        common_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //滑动时
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //页面选中时
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mBottomTabBar.selectTabWithId(R.id.tab_shouye);
                        break;
                    case 1:
                        mBottomTabBar.selectTabWithId(R.id.tab_all);
                        break;
                    case 2:
                        mBottomTabBar.selectTabWithId(R.id.tab_cart);
                        break;
                    case 3:
                        mBottomTabBar.selectTabWithId(R.id.tab_account);
                        break;
                    case 4:
                        mBottomTabBar.selectTabWithId(R.id.tab_account);
                        break;
                    default:
                        break;
                }
            }

            //滑动状态改变时
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //Tab选择监听事件
        mBottomTabBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_souye:
                        common_viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.tab_all:
                        common_viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.tab_cart:
                        common_viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.tab_account:
                        common_viewPager.setCurrentItem(3, false);
                        break;
                    case R.id.tab_account:
                        common_viewPager.setCurrentItem(4, false);
                        break;
                    default:
                        break;
                }

            }
        });
*/

    }

    private void initData() {
        //加载自定义的布局
        mBottomTabBar.init((AllFragmentActivity.this).getSupportFragmentManager())
                .setImgSize(50, 50)
                .setChangeColor(Color.RED, Color.DKGRAY)
                .addTabItem("首页", R.mipmap.tab_home_bottom_shouye, HomeFragment.class)
                .addTabItem("圈子", R.mipmap.tab_home_bottom_quanzi, CircleFragment.class)
                .addTabItem("购物车", R.mipmap.tab_button_gouwuche1, ShopCarFragment.class)
                .addTabItem("账单", R.mipmap.tab_home_bottom_zhangdan, BillFragment.class)
                .addTabItem("我的", R.mipmap.tab_home_bottom_wode, MyFragment.class);

    }

}