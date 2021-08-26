package com.example.airtickets.model.api;

import com.example.airtickets.MyApplication;
import com.example.airtickets.model.Account;
import com.example.airtickets.model.Ticket;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RetrofitApi {

    public final static int ERROR = -1;
    public final static int OK = 0;
    public final static int INVALID_LOGIN = 1;
    public final static int INVALID_PASSWORD = 2;
    public final static int INVALID_TOKEN = 3;

    private interface Callback {
        void onResult(int result);
    }

    public interface AccountListener extends Callback{
        void onAccount(Account account);
    }

    public interface TicketsListener extends Callback{
        void onTickets(List<Ticket> list);
    }

    private CompositeDisposable disposable;
    private Callback callback;

    public RetrofitApi(Callback callback) {
        this.callback = callback;
        disposable = new CompositeDisposable();
    }

    public void startLoginIn(String login, String password) {
        AccountListener listener = (AccountListener) callback;
        disposable.add(MyApplication.API.getAccount(login, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginRequest>() {
                    @Override
                    public void accept(LoginRequest loginRequest) throws Exception {
                        String result = loginRequest.getResult();
                        if (result != null) {
                            if (result.equals("invalid_login")) {
                                listener.onResult(INVALID_LOGIN);
                            }
                            else if (result.equals("invalid_password")) {
                                listener.onResult(INVALID_PASSWORD);
                            }
                            return;
                        }
                        listener.onResult(OK);
                        listener.onAccount(new Account(loginRequest.getName(),
                                login,
                                password,
                                loginRequest.getToken()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        listener.onResult(ERROR);
                    }
                }));
    }

    public void startDownloadTickets(String token) {
        TicketsListener listener = (TicketsListener) callback;
        disposable.add(MyApplication.API.getTickets(token)
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<TicketRequest>() {
            @Override
            public void accept(TicketRequest ticketRequest) throws Exception {
                if (ticketRequest.equals("invalid_token")) {
                    listener.onResult(INVALID_TOKEN);
                    return;
                }
                listener.onResult(OK);
                listener.onTickets(ticketRequest.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                listener.onResult(ERROR);
            }
        }));
    }

    public void onDestroy() {
        disposable.clear();
    }
}
