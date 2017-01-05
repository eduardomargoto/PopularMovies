package br.com.etm.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import br.com.etm.popularmovies.R;

/**
 * Created by EDUARDO_MARGOTO on 11/30/2016.
 */

public class Utils {

    public static String getPreferredOrder(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.key_pref_order),
                context.getString(R.string.value_default_pref_order));
    }

    public static void setPreferredOrder(Context context, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        prefs.edit().putString(context.getString(R.string.key_pref_order), value).commit();

    }

    /**
     *  Checking the internet connection
     *
     * @param context
     * @return A boolean.
     */
    public static boolean checkConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        return i.isAvailable();
    }
}
