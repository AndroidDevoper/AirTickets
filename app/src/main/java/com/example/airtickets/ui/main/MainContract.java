package com.example.airtickets.ui.main;

import android.widget.ArrayAdapter;

interface MainContract {
    void setSpinner(ArrayAdapter adapter);
    void setAdapter(TicketsAdapter adapter);
    void showProgressBar(boolean ch);
    void showToast(String text);
}
