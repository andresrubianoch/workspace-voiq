package voiq.com.appvoiq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import voiq.com.appvoiq.fragments.FragmentPokemonDetail;

/**
 * Actividad para poder mostrar el detalle, donde colocará por defecto, un
 * Fragment que es el encargado de mostrar la información del Pokemón.
 */
public class PokemonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FragmentPokemonDetail fragment = new FragmentPokemonDetail();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.pokemon_detail_container, fragment)
                .commit();
    }
}

