package com.example.airtickets;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetAccess {
    public interface OnListener {
        void isAccess(boolean ch);
    }

    private Context context;
    private OnListener listener;

    public InternetAccess(Context context, OnListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void scan() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info != null)
                listener.isAccess(true);
            else
                listener.isAccess(false);
        }
        else
            listener.isAccess(false);
    }
}
