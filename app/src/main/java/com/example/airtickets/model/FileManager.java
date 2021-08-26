package com.example.airtickets.model;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {

    public interface CallBack{
        void onRead(Object obj);
    }

    private Context context;
    private CallBack callBack;

    public FileManager(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    public void read(String filename) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(context.getFilesDir() + "/" + filename)
            );
            Object object = inputStream.readObject();
            if (object != null)
                callBack.onRead(object);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void write(String filename, Object obj) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(context.getFilesDir() + "/" + filename)
            );
            outputStream.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
