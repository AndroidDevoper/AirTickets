package com.example.airtickets.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airtickets.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements MainContract{

    private ActivityMainBinding binding;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        presenter = new MainPresenter(this, this);
        init();
    }

    private void init() {
        binding.activityMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.activityMainSpinner.setSelection(0);
                presenter.onClickBtnRefresh();
            }
        });
    }

    @Override
    public void setSpinner(ArrayAdapter adapter) {
        Spinner spinner = binding.activityMainSpinner;
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                presenter.onItemSelected(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void setAdapter(TicketsAdapter adapter) {
        binding.activityMainListView.setAdapter(adapter);
    }

    @Override
    public void showProgressBar(boolean ch) {
        if (ch) {
            binding.activityMainListView.setVisibility(View.INVISIBLE);
            binding.activityMainPb.setVisibility(View.VISIBLE);
        }
        else {
            binding.activityMainListView.setVisibility(View.VISIBLE);
            binding.activityMainPb.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
        binding = null;
    }
}
