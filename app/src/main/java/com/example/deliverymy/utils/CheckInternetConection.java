package com.example.deliverymy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetConection {

    public static boolean isInternetConnection(Context mContext) {
        final ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connMgr != null;
        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return wifi.isAvailable() && wifi.getState() == NetworkInfo.State.CONNECTED || mobile.isAvailable() && mobile.getState() == NetworkInfo.State.CONNECTED;
    }

}
