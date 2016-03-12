package voiq.com.appvoiq.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import voiq.com.appvoiq.R;
import voiq.com.appvoiq.connectivity.MainThread;
import voiq.com.appvoiq.objects.ObjectPokemon;
import voiq.com.appvoiq.utils.UtilsConstants;

/**
 * Fragment que se encarga de mostrar la información del Pokemón seleccionado.
 */
public class FragmentPokemonDetail extends Fragment {
    //Id para identificar el pokemos a mostrar.
    public static final String ARG_ITEM_ID = "item_id";
    //Objecto del Pokemos para mostrar la información
    private ObjectPokemon mItem;

    private TextView mTvName;
    private TextView mTvNationalId;
    private TextView mTvSex;
    private ImageView mIvSprite;
    //Hilo que se encarga de descargar la información
    private MainThread mHilo;

    public FragmentPokemonDetail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            //Obtenemos la información que recibimos del Bundle.
            Bundle bundle = getArguments().getBundle(ARG_ITEM_ID);
            if (null != bundle) {
                mItem = bundle.getParcelable("pokemon");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pokemon_detail, container, false);

        // Llenamos la información de la vista con la información recibida.
        if (mItem != null) {
            mTvName = (TextView) rootView.findViewById(R.id.detail_pokemon_nombre);
            mTvNationalId = (TextView) rootView.findViewById(R.id.detail_pokemon_national_id);
            mTvSex = (TextView) rootView.findViewById(R.id.detail_pokemon_nombre_sex);
            mIvSprite = (ImageView) rootView.findViewById(R.id.iv_pokemon_photo);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        //Validamos que hayamos enviado el objeto
        if (null != mItem){
            //Obtenemos la URL para descargar la información del objeto asociado.
            String newUrl = mItem.getUrl();
            //Validamos que tengamos información en la URL
            if (newUrl.length() > 0){
                mHilo = new MainThread(FragmentPokemonDetail.this, "Descargando detalle pokemon...");
                mHilo.execute(UtilsConstants.POKEMON.FRAGMENT_POKEMON_DETAIL, newUrl);
            } else {
                //TODO debemos volver a la ventana para escoger algún pokemon de la lista.
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Si salimos de la actividad, cancelamos el Hilo.
        if (null !=  mHilo){
            mHilo.cancel(true);
        }
    }

    public void putInfo(ObjectPokemon pokemon){
        if (null != mTvName){
            if (null != pokemon){
                //Colocamos el nombre del POkemón encontrado.
                mTvName.setText("Pokemon : " + pokemon.getName());
            }
        }

        if (null != mIvSprite){
            //Colocamos la imagen de la URL generada, usando la librería tercera Picasso.
            Picasso.with(getActivity()).load(pokemon.getUrl()).into(mIvSprite);
        }
    }
}
