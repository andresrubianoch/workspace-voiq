package voiq.com.appvoiq.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import voiq.com.appvoiq.R;


/**
 * Fragment que muestra un ProgressBar para colocar en un Framelayout
 * para dar la opción de que se está esperando para terminar un proceso.
 */
public class FragmentProgressBar extends Fragment {

    public FragmentProgressBar() {
    }

    public static FragmentProgressBar newInstance (Bundle arguments){
        FragmentProgressBar fragment = new FragmentProgressBar();
        if(arguments != null) {
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Colocamos que la orientación por defecto que sea horizontal
        View v = inflater.inflate(R.layout.fragment_progress_bar, container, false);
        return v;
    }

}
