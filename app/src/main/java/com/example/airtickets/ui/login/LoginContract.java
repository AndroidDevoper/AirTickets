package com.example.airtickets.ui.login;

interface LoginContract {
    String getLogin();
    String getPassword();
    void showProgressBar(boolean ch);
    void showToast(String text);
    void showErrorLogin(String text);
    void showErrorPassword(String text);
}
