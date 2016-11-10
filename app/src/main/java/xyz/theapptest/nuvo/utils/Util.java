package xyz.theapptest.nuvo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import xyz.theapptest.nuvo.services.AppController;


/**
 * Created by user on 11/09/2015.
 */
public class Util {
    public static void showToast(Context ctx, String toast) {
        Toast.makeText(ctx, toast, Toast.LENGTH_SHORT).show();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting())
                    || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;
    }

    public static void printLog(String tag, String msg) {
        Log.e(tag, msg);
    }
    // to cancel webservice request
    public static void cancelWebserviceRequest(Context mContext){
        try {
            AppController.getInstance().cancelPendingRequests(mContext.getClass().getSimpleName());
            Util.printLog("Volley:", "Request Canceled " + mContext.getClass().getSimpleName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
