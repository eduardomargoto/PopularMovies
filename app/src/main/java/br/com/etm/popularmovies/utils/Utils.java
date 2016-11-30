package br.com.etm.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by EDUARDO_MARGOTO on 11/30/2016.
 */

public class Utils {

    public static int getHeightDensity(Context context) {
        return (getWidthDensity(context) / 16) * 9;
    }

    public static int getWidthDensity(Context context) {
        float scale = context.getResources().getDisplayMetrics().density;
        return context.getResources().getDisplayMetrics().widthPixels - (int) (14 * scale + 0.5f);
    }

    /**
     *  Checking the internet connection
     *
     * @param context
     * @return A boolean.
     */
    public static boolean checkConn(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }
}
