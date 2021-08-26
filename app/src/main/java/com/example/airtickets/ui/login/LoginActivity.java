package com.example.airtickets.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.airtickets.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements LoginContract {

    private ActivityLoginBinding binding;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new LoginPresenter(this, this);
        init();
    }

    private void init() {
        binding.activityLoginPb.setVisibility(View.GONE);
        binding.activityLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickLoginIn();
            }
        });
        binding.activityLoginEtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onClickLoginIn();
            }
        });
    }

    @Override
    public String getLogin() {
        return binding.activityLoginEtLogin.getText().toString();
    }

    @Override
    public String getPassword() {
        return binding.activityLoginEtPassword.getText().toString();
    }

    @Override
    public void showProgressBar(boolean ch) {
        if (ch) {
            binding.activityLoginPb.setVisibility(View.VISIBLE);
            binding.activityLoginBtn.setVisibility(View.GONE);
        }
        else {
            binding.activityLoginPb.setVisibility(View.GONE);
            binding.activityLoginBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorLogin(String text) {
        binding.activityLoginEtLogin.setError(text);
    }

    @Override
    public void showErrorPassword(String text) {
        binding.activityLoginEtPassword.setError(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
        binding = null;
    }
}