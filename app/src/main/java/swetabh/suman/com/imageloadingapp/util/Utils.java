package swetabh.suman.com.imageloadingapp.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by abhi on 28/01/17.
 */

public class Utils {
    public static boolean isNetworkConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        // should check null because in air plan mode it will be null
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;

    }

    public static void showToast(final Activity context, final String text) {
        if (context.getMainLooper().getThread().equals(Thread.currentThread())) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
