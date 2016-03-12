package voiq.com.appvoiq.utils;

/**
 * Created by Andres Rubiano on 07/10/2015.
 *
 * Clase que contiene toda la información de constantes de la aplicación.
 */
public class UtilsConstants {

    public final static class POKEMON {
        public final static String ACTIVITY_POKEMON = "activityPokemon";

        public final static String JSON_RESULT           = "results";
        public final static String BUNDLE_LIST_POKEMON   = "bundleListaPokemon";

        public final static String POKEMON_DETAIL   = "bundleListaPokemon";

        public final static String OBJECT_POKEMON        = "pokemon";

        public final static String FRAGMENT_POKEMON_DETAIL = "pokemonDetail";

    }

    public final static class GENERAL {
        public final static String LOG_ERROR = "ERROR",
                                    LOG_INFO = "INFO";
        public final static CharSequence LOG_OK    = "OK";

        public final static String PREFERENCES = "preferencesApp";

        public final static String MENSAJE_GENERICO = "mensajeGenerico";
        public final static String WITHOUT_INTERNET = "No hay conexión a internet. Por favor, vuelva a intentarlo.";

    }

    public final static class  ERROR {
        public final static String FIELD_NULL = "Campo nulo";
        public final static String UNEXPECTED_ERROR = "Error inesperado en ";
    }

    public final static class FECHA {
        public final static String SIMPLE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    }

    public final static class URL {
        public final static String URL_BASE = "http://pokeapi.co/api/v2/";

        public final static String POKEMON_LIST = "pokemon/";

        public final static String PARAMETER_RESUMEN = "resumen";
        public final static String PARAMETER_MENSAJE = "mensaje";
        public final static String PARAMETER_RESUMEN_ERROR = "resumenError";
        public final static String PARAMETER_RESUMEN_OK = "resumenOk";
        public final static String PARAMETER_ACTIVIDAD_PROVENIENTE = "actividadProveniente";
    }

    public final static class JSON {
        public final static String JSON_SUCCESS = "success";
        public final static String JSON_VALOR = "valor";
        public final static String JSON_MENSAJE = "mensaje";
    }

}

