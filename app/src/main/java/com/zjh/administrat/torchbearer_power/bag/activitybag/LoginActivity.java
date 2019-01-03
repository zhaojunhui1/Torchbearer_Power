package com.zjh.administrat.torchbearer_power.bag.activitybag;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zjh.administrat.torchbearer_power.R;
import com.zjh.administrat.torchbearer_power.bag.beanbag.LoginBean;
import com.zjh.administrat.torchbearer_power.bag.beanbag.RegisterBean;
import com.zjh.administrat.torchbearer_power.bag.beanbag.UserIDBean;
import com.zjh.administrat.torchbearer_power.bag.constants.Constants;
import com.zjh.administrat.torchbearer_power.bag.presenterbag.IPresenterImpl;
import com.zjh.administrat.torchbearer_power.bag.utilsbag.Apis;
import com.zjh.administrat.torchbearer_power.bag.viewbag.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements IView, View.OnClickListener {
    private IPresenterImpl mPresenter;
    private EditText mPhone, mPassword;
    private CheckBox main_check_pwd;
    private boolean mbDisplayFlg = false;
    private LoginBean loginBean;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mPresenter = new IPresenterImpl(this);
        initView();
    }

    //处理view资源
    private void initView() {
        mPhone = findViewById(R.id.main_mPhone);
        mPassword = findViewById(R.id.main_mPassword);
        main_check_pwd = findViewById(R.id.main_check_pwd);
        findViewById(R.id.eye_hide).setOnClickListener(this);
        findViewById(R.id.button_main_login).setOnClickListener(this);
        findViewById(R.id.button_main_register).setOnClickListener(this);
        //记住密码
        sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean JZ = sharedPreferences.getBoolean("JZ", false);
        if (JZ){
            String username = sharedPreferences.getString("username", "");
            String password = sharedPreferences.getString("password", "");
            mPhone.setText(username);
            mPassword.setText(password);
            main_check_pwd.setChecked(true);
        }
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main_login:
                checkPermission();
                break;
            case R.id.button_main_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.eye_hide:
                if (!mbDisplayFlg) {
                    // display password text, for example "123456"
                    mPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password, display "."
                    mPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                mbDisplayFlg = !mbDisplayFlg;
                mPassword.postInvalidate();
                CharSequence charSequence = mPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }

                break;
            default:
                break;
        }
    }
    //动态权限
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},1);
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
        String username = mPhone.getText().toString();
        String password = mPassword.getText().toString();
        if (main_check_pwd.isChecked()){
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("JZ", true);
            editor.commit();
        }else {
            editor.clear();
            editor.commit();
        }


        Map<String, String> params = new HashMap<>();
          params.put(Constants.POST_LOGIN_PHONE, mPhone.getText().toString());
          params.put(Constants.POST_LOGIN_PASSWORD, mPassword.getText().toString());
          mPresenter.pstartRequest(Apis.URL_LOGIN_POST, params, LoginBean.class);
    }

    //请求数据回传数据成功
    @Override
    public void viewDataSuccess(Object data) {
        if(data instanceof LoginBean) {
            loginBean = (LoginBean) data;
            Toast.makeText(this, ""+ loginBean.getMessage(), Toast.LENGTH_SHORT).show();
            //登录成功跳转页面
            if (loginBean.getStatus().equals("0000")){
                startActivity(new Intent(LoginActivity.this, AllFragmentActivity.class));
            }
            //EventBus传递数据
            UserIDBean idBean = new UserIDBean(loginBean.getResult().getUserId(), loginBean.getResult().getSessionId());
            EventBus.getDefault().postSticky(idBean);
        }
    }

    /**
     * 处理内存
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDetch();
    }

}
