package voiq.com.appvoiq.connectivity;

import android.app.Activity;
import android.content.Context;

import voiq.com.appvoiq.utils.UtilsConstants;
import voiq.com.appvoiq.utils.UtilsSharedPreferences;

/**
 * Created by Andres Rubiano on 17/11/2015.
 */
public class UtilsConnectionServer {

    private Context context = null;
    private static UtilsSharedPreferences preferences = null;

    private static UtilsConnectionServer ourInstance = new UtilsConnectionServer();

    public static UtilsConnectionServer getInstance(Context context) {
        if (context != null) {
            preferences = UtilsSharedPreferences.newInstance((Activity) context);
        }
        return ourInstance;
    }

    private UtilsConnectionServer() {
    }

    //Devuelve la ruta de la carpeta
    private String getRoute() throws Exception {
        return UtilsConstants.URL.URL_BASE;
    }

    //URL para descargar la lista de los pokemones.
    public String getUrlListPokemon() throws Exception {
        return getRoute() + UtilsConstants.URL.POKEMON_LIST;
    }

}
