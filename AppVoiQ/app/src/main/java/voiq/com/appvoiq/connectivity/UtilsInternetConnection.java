package voiq.com.appvoiq.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Andres Rubiano on 19/11/2015.
 * Clase que permite saber la conexión del dispositivo si cuenta con conexión
 * Vía WI-fi o por Red.
 *
 * Usa el Patrón Singleton.
 */
public class UtilsInternetConnection {
    private static UtilsInternetConnection ourInstance = new UtilsInternetConnection();

    public static UtilsInternetConnection getInstance() {
        return ourInstance;
    }

    private UtilsInternetConnection() {
    }

    public boolean isInternetConnection(Context mContext) throws Exception{
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

        return false;
    }
}
