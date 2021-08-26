package com.example.airtickets.ui.main;

import android.app.Activity;
import android.widget.ArrayAdapter;

import com.example.airtickets.InternetAccess;
import com.example.airtickets.model.Account;
import com.example.airtickets.model.Ticket;
import com.example.airtickets.model.api.RetrofitApi;

import java.util.ArrayList;
import java.util.List;

class MainPresenter {

    private MainContract view;
    private Activity activity;
    private Account account;
    private InternetAccess access;
    private RetrofitApi api;
    private TicketsAdapter adapter;

    public MainPresenter(MainContract view, Activity activity) {
        this.view = view;
        this.activity = activity;
        account = activity.getIntent().getParcelableExtra("account");
        initRetrofitApi();
        initInternetAccess();
        adapter = new TicketsAdapter(activity.getLayoutInflater());
        List<String> spinner = new ArrayList<>();
        spinner.add("Sort: by date");
        spinner.add("Sort: descending price");
        spinner.add("Sort: ascending price");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(activity.getBaseContext(),
                android.R.layout.simple_spinner_item, spinner);
        view.setSpinner(spinnerAdapter);
    }

    private void initInternetAccess() {
        access = new InternetAccess(activity.getBaseContext(),
                new InternetAccess.OnListener() {
                    @Override
                    public void isAccess(boolean ch) {
                        api.startDownloadTickets(account.getToken());
                    }
                });
        view.showProgressBar(true);
        access.scan();
    }

    private void initRetrofitApi() {
        api = new RetrofitApi(new RetrofitApi.TicketsListener() {
            @Override
            public void onTickets(List<Ticket> list) {
                view.showProgressBar(false);
                adapter.setList(list);
                adapter.sortDate();
                view.setAdapter(adapter);
            }

            @Override
            public void onResult(int result) {
                view.showProgressBar(false);
                if (result == RetrofitApi.ERROR)
                    view.showToast("error");
            }
        });
    }

    public void onClickBtnRefresh() {
        view.showProgressBar(true);
        adapter.sortDate();
        access.scan();
    }

    public void detach() {
        view = null;
        api.onDestroy();
    }

    public void onItemSelected(int i) {
        if (i == 0)
            adapter.sortDate();
        else if (i == 1)
            adapter.sortCostMin();
        else
            adapter.sortCostMax();
    }
}
