package com.example.manivaandroapp.controller.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetController {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
