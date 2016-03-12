package voiq.com.appvoiq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import voiq.com.appvoiq.adapter.AdapterListPokemon;
import voiq.com.appvoiq.connectivity.MainThread;
import voiq.com.appvoiq.objects.ObjectPokemon;
import voiq.com.appvoiq.utils.UtilsConstants;

/**
 * Actividad principal que se encarga de mostrar una lista de la información
 * que se va a descargar del servicio web.
 */
public class PokemonListActivity extends AppCompatActivity {
    //Variable que permite saber si estamos usando una tablet o un dispositivo.
    private boolean mTwoPane;
    //Clase que se encargará de descargar la información del servidor.
    private MainThread mHilo = null;
    //Variable global del Recycler que contendrá la información
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_list);
        //Inicializamos la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        //Obtenemos el recycler de la vista.
        recyclerView = (RecyclerView) findViewById(R.id.pokemon_list);
        //Validamos si nos encontramos en una tablet o en un dispositivo.
        if (findViewById(R.id.pokemon_detail_container) != null) {
            mTwoPane = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //descargamos los datos del servidor
        try {
            downloadServiceWebInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de crear el hilo para descargar la información.
     * @throws Exception
     */
    private void downloadServiceWebInfo() throws Exception {
        mHilo =  new MainThread(PokemonListActivity.this, getString(R.string.download_pokemon_title));
        mHilo.execute(UtilsConstants.POKEMON.ACTIVITY_POKEMON);
    }

    //Método público que se encarga de colocar la información que se descarga del servidor.
    public void setupRecyclerView(List<ObjectPokemon> listPokemon) {
        recyclerView.setAdapter(new AdapterListPokemon(PokemonListActivity.this, listPokemon, mTwoPane));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Si salimos de la actividad, cancelamos el Hilo.
        if (null !=  mHilo){
            mHilo.cancel(true);
        }
    }

}
