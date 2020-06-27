package com.example.clock;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.cardview.widget.CardView;

import com.example.clock.bean.ReportBean;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import com.example.clock.bean.UserBean;
import com.example.clock.base.BaseActivity;
import butterknife.BindView;
import com.example.clock.http.LoginService;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.clock.utils.ToastUtil;

import org.litepal.LitePal;
public class LoginActivity extends BaseActivity {

    @BindView(R.id.edt_account)
    TextInputEditText edtAccount;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.cv_login)
    CardView cvLogin;
    @BindView(R.id.card_input)
    CardView cardInput;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        ReportBean reportBean = LitePal.find(ReportBean.class,1);
        if (reportBean != null && reportBean.getName() != null ){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        initClick();
    }

    private void initClick() {
        cvLogin.setOnClickListener(v -> {
            String account = edtAccount.getText().toString();
            String password = edtPassword.getText().toString();
            login(account, password);
        });
    }


    private void login(String username, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hn216.api.yesapi.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        LoginService loginService = retrofit.create(LoginService.class);
        String nameAndPassword = username + "," + password;
        loginService.login(nameAndPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) { }
                    @Override
                    public void onNext(UserBean userBean) {
                        Log.d("LoginActivity", "login|onNext");
                        List<UserBean.DataBean.ItemsBean> userList = userBean.getData().getItems();
                        if (userList != null && userList.size() > 0) {
                            UserBean.DataBean.ItemsBean itemsBean = userList.get(0);
                            loginSuccess(itemsBean);
                        } else {
                            loginFailed("登陆失败!");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d("LoginActivity", "login|onError");
                        loginFailed("网络请求失败，请重试");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("LoginActivity", "login|onComplete");
                    }
                });
    }



    private void loginSuccess(UserBean.DataBean.ItemsBean itemsBean) {
        ToastUtil.show("登录成功");
        ReportBean reportBean = new ReportBean();
        reportBean.setName(itemsBean.getName());
        reportBean.setUserId(itemsBean.getId());
        int sum = LitePal.count(ReportBean.class);
        if (sum == 0){
            reportBean.save();
        }else {
            reportBean.update(1);
        }
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void loginFailed(String message) {
        ToastUtil.show(message);
    }

}
