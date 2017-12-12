package com.github.androidutils;

import android.content.Context;

public class DBFactory {
    private static Dao sInstance;

    public synchronized static Dao getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DaoImpl(context);
        }
        return sInstance;
    }
}
