package com.example.manivaandroapp.controllers.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetController {

    //check whether internet connection is exist
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
