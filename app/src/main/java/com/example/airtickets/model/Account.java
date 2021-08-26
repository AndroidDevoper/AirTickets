package com.example.airtickets.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    private String name;
    private String login;
    private String password;
    private String token;

    public Account(String name,String login, String password, String token) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    protected Account(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        name = data[0];
        token = data[1];
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {name, token});
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
