package com.example.airtickets.ui.login;

import android.app.Activity;
import android.content.Intent;

import com.example.airtickets.InternetAccess;
import com.example.airtickets.model.Account;
import com.example.airtickets.model.FileManager;
import com.example.airtickets.model.api.RetrofitApi;
import com.example.airtickets.ui.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

class LoginPresenter {
    
    private LoginContract view;
    private Activity activity;
    private Account account;
    private FileManager fileManager;
    private InternetAccess access;
    private RetrofitApi retrofit;

    public LoginPresenter(LoginContract view, Activity activity) {
        this.view = view;
        this.activity = activity;
        initRetrofitApi();
        initInternetAccess();
        initFileManager();
    }

    private void initInternetAccess() {
        access = new InternetAccess(activity.getBaseContext(),
                new InternetAccess.OnListener() {
                    @Override
                    public void isAccess(boolean ch) {
                        if (ch) {
                            String login = view.getLogin();
                            if (login == null || login.equals("")) {
                                view.showErrorLogin("empty");
                                return;
                            }
                            String password = view.getPassword();
                            if (password == null || password.equals("")) {
                                view.showErrorPassword("empty");
                                return;
                            }
                            view.showProgressBar(true);
                            retrofit.startLoginIn(login, password);
                        }
                        else
                            view.showToast("Network error");
                    }
                });
    }

    private void initFileManager() {

        fileManager = new FileManager(activity.getBaseContext(),
                new FileManager.CallBack() {
                    @Override
                    public void onRead(Object obj) {
                        Map<String, String> map = (Map<String, String>) obj;
                        fileManager.write("account", map);
                        retrofit.startLoginIn(map.get("login"), map.get("password"));
                    }
                });
        fileManager.read("account");
    }

    private void initRetrofitApi() {
        retrofit = new RetrofitApi(new RetrofitApi.AccountListener() {
            @Override
            public void onResult(int result) {
                view.showProgressBar(false);
                if (result == RetrofitApi.INVALID_LOGIN)
                    view.showErrorLogin("invalid login");
                else if (result == RetrofitApi.INVALID_PASSWORD)
                    view.showErrorPassword("invalid password");
                else if (result == RetrofitApi.ERROR)
                    view.showToast("error");
            }

            @Override
            public void onAccount(Account acc) {
                account = acc;
                writeFile(account);
                startMainActivity();
            }
        });
    }

    private void writeFile(Account account) {
        Map<String, String> map = new HashMap<>();
        map.put("login", account.getLogin());
        map.put("password", account.getPassword());
        fileManager.write("account", map);
    }

    private void startMainActivity() {
        Intent intent = new Intent(activity.getBaseContext(), MainActivity.class);
        intent.putExtra("account", account);
        activity.startActivity(intent);
        activity.finish();
    }

    public void detach() {
        view = null;
        retrofit.onDestroy();
    }

    public void onClickLoginIn() {
       access.scan();
    }

}
