package com.zjh.administrat.torchbearer_power.bag.activitybag;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.beanbag.LoginBean;
import com.zjh.administrat.torchbearer_power.bag.beanbag.RegisterBean;
import com.zjh.administrat.torchbearer_power.bag.constants.Constants;
import com.zjh.administrat.torchbearer_power.bag.presenterbag.IPresenterImpl;
import com.zjh.administrat.torchbearer_power.bag.utilsbag.Apis;
import com.zjh.administrat.torchbearer_power.bag.viewbag.IView;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private IPresenterImpl mPresenter;
    private EditText mPhone, mPassword;
    private View mSend;
    private RegisterBean registerBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mPresenter = new IPresenterImpl(this);
        initView();
    }
    //初始化视图
    private void initView() {
        mPhone = findViewById(R.id.main_mPhone);
        mPassword = findViewById(R.id.main_mPassword);
        mSend = findViewById(R.id.send_number);
        //注册
        findViewById(R.id.button_main_register).setOnClickListener(this);
        //已有账号，去登陆
        findViewById(R.id.have_login).setOnClickListener(this);
    }

    //点击方法
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main_register:
                checkPermission();
                break;
            case R.id.have_login:
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                break;
            default:
                break;
        }
    }
    //动态权限
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            } else {
                startRequest();
            }
        } else {
            startRequest();
        }
    }
    //回调权限版本
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRequest();
        }
    }

    private void startRequest() {
                Map<String , String > map = new HashMap<>();
                map.put(Constants.POST_REGISTER_PHONE, mPhone.getText().toString());
                map.put(Constants.POST_REGISTER_PASSWORD, mPassword.getText().toString());
                mPresenter.pstartRequest(Apis.URL_REGISTER_POST, map, RegisterBean.class);
    }


    //请求回来数据成功
    @Override
    public void viewDataSuccess(Object data) {
        if(data instanceof RegisterBean){
            registerBean = (RegisterBean) data;
            Toast.makeText(this, registerBean.getMessage(), Toast.LENGTH_SHORT).show();
            if (registerBean.getStatus().equals("1001")){
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        }
    }
  /*  //请求数据失败
    @Override
    public void viewDataFail(String error) {

    }*/

}
