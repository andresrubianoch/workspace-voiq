package voiq.com.appvoiq.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import voiq.com.appvoiq.PokemonDetailActivity;
import voiq.com.appvoiq.fragments.FragmentPokemonDetail;
import voiq.com.appvoiq.PokemonListActivity;
import voiq.com.appvoiq.R;
import voiq.com.appvoiq.objects.ObjectPokemon;
import voiq.com.appvoiq.utils.UtilsConstants;

/**
 * Created by Andres Rubiano on 07/03/2016.
 * Clase que se encarga de adjuntar la información en la lista.
 */
public class AdapterListPokemon extends RecyclerView.Adapter<AdapterListPokemon.ViewHolder> {
    //Lista que muestra la información de todos los pokemones descargados.
    private final List<ObjectPokemon> mListaPokemon;
    //Variable global que se encarga de validar si el dispositivo es mayor a 7"
    private boolean mTwoPane;
    //Actividad que recibimos para msotrar la información
    private PokemonListActivity mActivity;

    public AdapterListPokemon(PokemonListActivity activity, List<ObjectPokemon> items, boolean twoPane) {
        mListaPokemon = items;
        this.mTwoPane = twoPane;
        this.mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //Obtenemos el objeto dependiendo de la posición
        holder.mItem = mListaPokemon.get(position);
        //Colocar el  nombre del pokemon en la lista.
        holder.mNombrePokemon.setText(mListaPokemon.get(position).getName());
        //Listener al presionar el elemento
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Si estamos usando una pantalla superior a las 7"
                if (mTwoPane) {
                    //Creamos un Bundle donde pasaremos toda la información para colocarla en el Fragment.
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(FragmentPokemonDetail.ARG_ITEM_ID, mListaPokemon.get(position));
                    FragmentPokemonDetail fragment = new FragmentPokemonDetail();
                    fragment.setArguments(arguments);
                    mActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pokemon_detail_container, fragment)
                            .commit();
                } else {
                    // En caso de que sea un dispositivo, creamos una intención para colocar la actividad.
                    Context context = v.getContext();
                    Intent intent = new Intent(context, PokemonDetailActivity.class);
                    ObjectPokemon pokemon = mListaPokemon.get(position);
                    Bundle bundle =  new Bundle();
                    bundle.putParcelable(UtilsConstants.POKEMON.OBJECT_POKEMON, pokemon);
                    intent.putExtra(FragmentPokemonDetail.ARG_ITEM_ID, bundle);

                    context.startActivity(intent);
                }
            }
        });
    }

    //Devuelve la cantidad de elementos del adapter.
    @Override
    public int getItemCount() {
        return mListaPokemon.size();
    }

    //Clase View que se encarga de colocar la información respectiva en el Adapter.
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNombrePokemon;
        public ObjectPokemon mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNombrePokemon = (TextView) view.findViewById(R.id.list_pokemon_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNombrePokemon.getText() + "'";
        }
    }
}
