package voiq.com.appvoiq.connectivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import voiq.com.appvoiq.fragments.FragmentPokemonDetail;
import voiq.com.appvoiq.PokemonListActivity;
import voiq.com.appvoiq.objects.ObjectHead;
import voiq.com.appvoiq.objects.ObjectPokemon;
import voiq.com.appvoiq.utils.UtilsConstants;

/**
 * Created by Andres Rubiano on 11/11/2015.
 * Clase que se encarga de realizar toda la gestión de descarga del servidor.
 * Un clase que hereda de Async Task para no utilizar le hilo principal de
 * la aplicación.
 */
public class MainThread extends AsyncTask<String, String, Bundle> {

    PokemonListActivity mActivity;
    FragmentPokemonDetail mDetailPokemon;
    //Elemento para mostrar en pantalla.
    private ProgressDialog pDialog = null;
    //Clase que se encarga de descargar la información del servidor.
    private HttpUtils httppostaux;
    //Tendrá el contexto de la clase que está instanciando
    private Context mContext = null;
    //Título a mostrar
    private String mTitulo;
    private int mLayout;

    public MainThread(PokemonListActivity activity, String titulo) {
        this.mActivity = activity;
        httppostaux = HttpUtils.newInstance();
        this.mTitulo = titulo;
        this.pDialog =  new ProgressDialog(activity);
    }

    public MainThread(FragmentPokemonDetail fragmentPokemon, String titulo) {
        this.mDetailPokemon = fragmentPokemon;
        httppostaux = HttpUtils.newInstance();
        this.mTitulo = titulo;
        this.pDialog =  new ProgressDialog(fragmentPokemon.getActivity());
    }

    protected void onPreExecute() {
        if (null != pDialog) {
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage(mTitulo);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

    @Override
    protected void onPostExecute(Bundle bundle) {
        // Si se ha ejecutado bien las peticiones a la base de datos
        if (bundle != null) {
            switch (bundle.getString(UtilsConstants.URL.PARAMETER_ACTIVIDAD_PROVENIENTE)) {
                //en caso de que venga de la lista de pokemon, hará:
                case UtilsConstants.POKEMON.ACTIVITY_POKEMON:
                    try {
                        validateListPokemon(bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                //Si estamos validando el detallo de un pokemón
                case UtilsConstants.POKEMON.FRAGMENT_POKEMON_DETAIL:
                    try {
                        validateListPokemonDetail(bundle);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
            //Permite cerrar el diáogo de la pantalla.
            closeProgressDialog();
        }
    }

    /**
     * Método que se encarga de mostrar la inforamción detallada del pokemón
     * @param bundle
     * @throws Exception
     */
    private void validateListPokemonDetail(Bundle bundle) throws Exception {
        if (null != bundle) {
            ObjectPokemon object = bundle.getParcelable(UtilsConstants.POKEMON.POKEMON_DETAIL);
            if (null != mDetailPokemon) {
                mDetailPokemon.putInfo(object);
            }
        }
    }

    /**
     * Método que se encarga de mostrar la información del encabezado del pokemon
     * @param bundle
     * @throws NullPointerException
     */
    private void validateListPokemon(Bundle bundle) throws NullPointerException {
        if (null != bundle) {
            ObjectHead objectEncabezado = bundle.getParcelable(UtilsConstants.POKEMON.BUNDLE_LIST_POKEMON);
            if (null != mActivity) {
                //Inicializamos el Adapter para colocar la información
                mActivity.setupRecyclerView(objectEncabezado.getPokemones());
            }
        }
    }

    // Acciones a realizar mientas se está descargando los datos de servidor.
    // args[0] = Tipo para descargar
    @Override
    protected Bundle doInBackground(String... args) {
        //args[0] : Actividad proveniente
        String actividadProveniente = args[0];
        if (actividadProveniente.length() > 0) {
            switch (actividadProveniente) {
                case UtilsConstants.POKEMON.ACTIVITY_POKEMON:
                    try {
                        return downloadPokemon(actividadProveniente);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case UtilsConstants.POKEMON.FRAGMENT_POKEMON_DETAIL:
                    try {
                        return downloadPokemonDetail(actividadProveniente, args[1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return null;
    }

    /**
     * Metodo que se encarga de descargar la información del servidor.
     * @param actividadProveniente : Referencia a la actividad que estamos usando para saber
     *                             si se está dejando de utilizar.
     * @param url : URL de la página para descargar la información del servidor.
     * @return Retorna un Objeto Bundle con la información descargada.
     * @throws Exception
     */
    private Bundle downloadPokemonDetail(String actividadProveniente, String url) throws Exception {
        Bundle bundle = new Bundle();
        try {
            //Preugntamos si el hilo no se ha sido detenido.
            if (!this.isCancelled()) {
                //Obtenemos la url del servicio.
                if (url.length() > 0) {
                    Gson gson = new Gson();
                    //OBtenemos todos los datos del servidor.
                    JSONObject objectUser = descargarJsonObject(url, "");
                    if (objectUser != null) {
                        //Guadamos una referencia a la actividad que está generando la información.
                        bundle.putString(UtilsConstants.URL.PARAMETER_ACTIVIDAD_PROVENIENTE, actividadProveniente);
                        String name = objectUser.getString("name");
                        //Obtenemos el arrayPokemon de la información que vamos a guardar.
                        JSONObject arraySprite = objectUser.getJSONObject("sprites");
                        String urlImage = arraySprite.getString("front_default");
                        //Agregamos la información al Bundle para poder usarla.
                        bundle.putParcelable(UtilsConstants.POKEMON.POKEMON_DETAIL, new ObjectPokemon(name, urlImage));
                        bundle.putString(UtilsConstants.URL.PARAMETER_RESUMEN, UtilsConstants.URL.PARAMETER_RESUMEN_OK);
                    } else {
                        bundle.putString(UtilsConstants.URL.PARAMETER_RESUMEN, UtilsConstants.URL.PARAMETER_RESUMEN_ERROR);
                        bundle.putString(UtilsConstants.JSON.JSON_MENSAJE, objectUser.getString(UtilsConstants.JSON.JSON_MENSAJE));
                    }
                }
            } else {
                logErrorMessage("La URL debe tener una url válida.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bundle == null ? getBundleError(actividadProveniente) : bundle;
    }

    /**
     * Método que se encarga de descargar la lista de pokemon para mostrar en la lista.
     * @param actividadProveniente
     * @return
     */
    private Bundle downloadPokemon(String actividadProveniente) {
        Bundle bundle = new Bundle();
        try {
            //Preugntamos si el hilo no se ha sido detenido.
            if (!this.isCancelled()) {
                //Clase que se encarga de tener las url de los servicios web.
                UtilsConnectionServer connection = UtilsConnectionServer.getInstance((mActivity));
                if (null != connection) {
                    //Obtenemos la url del servicio.
                    String url = connection.getUrlListPokemon();
                    if (url.length() > 0) {
                        Gson gson = new Gson();
                        //OBtenemos todos los datos del servidor.
                        JSONObject objectUser = descargarJsonObject(url, "");
                        if (objectUser != null) {
                            //Guadamos una referencia a la actividad que está generando la información.
                            bundle.putString(UtilsConstants.URL.PARAMETER_ACTIVIDAD_PROVENIENTE, actividadProveniente);
                            int count = objectUser.getInt("count");
                            String next = objectUser.getString("next");
                            //Obtenemos el arrayPokemon de la información que vamos a guardar.
                            JSONArray arrayPokemon = objectUser.getJSONArray(UtilsConstants.POKEMON.JSON_RESULT);
                            int tamanio = arrayPokemon.length();
                            if (tamanio > 0) {
                                ArrayList<ObjectPokemon> listaPokemones = new ArrayList<>();
                                for (int i = 0; i < tamanio; i++) {
                                    JSONObject pokemon = (JSONObject) arrayPokemon.get(i);
                                    listaPokemones.add(gson.fromJson(String.valueOf(pokemon), ObjectPokemon.class));
                                }
                                //Agregamos la información al Bundle para poder usarla.
                                bundle.putParcelable(UtilsConstants.POKEMON.BUNDLE_LIST_POKEMON, new ObjectHead(count, next, listaPokemones));
                                bundle.putString(UtilsConstants.URL.PARAMETER_RESUMEN, UtilsConstants.URL.PARAMETER_RESUMEN_OK);
                            } else {
                                bundle.putString(UtilsConstants.URL.PARAMETER_RESUMEN, UtilsConstants.URL.PARAMETER_RESUMEN_ERROR);
                                bundle.putString(UtilsConstants.JSON.JSON_MENSAJE, objectUser.getString(UtilsConstants.JSON.JSON_MENSAJE));
                            }
                        }
                    } else {
                        logErrorMessage("La URL debe tener una url válida.");
                    }
                } else {
                    logErrorMessage("Clase de constantes de URL no encontrada.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bundle == null ? getBundleError(actividadProveniente) : bundle;
    }

    //Método que se encarga de devolver un Bundle que contiene información errónea.
    private Bundle getBundleError(String actividadProveniente) {
        Bundle bundle = new Bundle();
        bundle.putString(UtilsConstants.URL.PARAMETER_ACTIVIDAD_PROVENIENTE, actividadProveniente);
        bundle.putString(UtilsConstants.URL.PARAMETER_MENSAJE, String.valueOf("Se ha presentado un error en el servidor. Por favor, vuelva a intentarlo."));
        bundle.putString(UtilsConstants.URL.PARAMETER_RESUMEN, UtilsConstants.URL.PARAMETER_RESUMEN_ERROR);
        return bundle;
    }

    private JSONObject descargarJsonObject(String url, String json) {
        String respuesta = "";
        try {
            return JSONParser.makeHttpRequest(url, "GET", null);
//            respuesta = getResponseBody(null);
        } catch (Exception ie) {
            ie.printStackTrace();
        }
        return null;
    }

    /**
     * Método que se encarga de descargar un JSON Array.
     * @param url
     * @param authorization
     * @return
     */
    private JSONArray descargarGetJsonObject(String url, String authorization) {
        String respuesta = "";
        try {
            JSONArray array = JSONParser.getJSONFromUrl(url, authorization);
            return array;
        } catch (Exception ie) {
        }
        return null;
    }

    /**
     * Valida la respuesta del protocolo HTTP. (Pendiente por actualizar)
     * @param response
     * @return
     */
    public static String getResponseBody(HttpResponse response) {
        String response_text = null;
        HttpEntity entity = null;
        try {
            entity = response.getEntity();
            response_text = _getResponseBody(entity);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            if (entity != null) {

                try {
                    entity.consumeContent();
                } catch (IOException e1) {
                }
            }
        }
        return response_text;
    }


    public static String _getResponseBody(final HttpEntity entity)
            throws IOException, ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }

        InputStream instream = entity.getContent();

        if (instream == null) {
            return "";
        }

        if (entity.getContentLength() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(

                    "HTTP entity too large to be buffered in memory");
        }

        String charset = getContentCharSet(entity);

        if (charset == null) {

            charset = HTTP.DEFAULT_CONTENT_CHARSET;

        }

        Reader reader = new InputStreamReader(instream, charset);

        StringBuilder buffer = new StringBuilder();

        try {

            char[] tmp = new char[1024];

            int l;

            while ((l = reader.read(tmp)) != -1) {

                buffer.append(tmp, 0, l);

            }

        } finally {

            reader.close();

        }

        return buffer.toString();

    }

    public static String getContentCharSet(final HttpEntity entity)
            throws ParseException {

        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }

        String charset = null;

        if (entity.getContentType() != null) {

            HeaderElement values[] = entity.getContentType().getElements();

            if (values.length > 0) {

                NameValuePair param = values[0].getParameterByName("charset");

                if (param != null) {

                    charset = param.getValue();

                }

            }

        }

        return charset;

    }

    /**
     * Método que se encarga de cerrar el diálogo que se le muestra en al interfaz del usuario.
     */
    private void closeProgressDialog() {
        if (null != this.pDialog) {
            if (this.pDialog.isShowing()) {
                this.pDialog.dismiss();
            }
        }
    }

    /**
     * Método que permite mostrar mensajes en el LOG para la solución de problemas.
     * @param message
     */
    private void logErrorMessage(String message) {
        if (message.length() > 0) {
            Log.i(UtilsConstants.GENERAL.LOG_ERROR, message);
        }
    }
}

