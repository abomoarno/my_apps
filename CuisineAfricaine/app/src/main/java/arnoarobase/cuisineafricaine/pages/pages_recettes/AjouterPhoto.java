package arnoarobase.cuisineafricaine.pages.pages_recettes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import arnoarobase.cuisineafricaine.R;

/**
 *
 */
public class AjouterPhoto extends Fragment {

    public AjouterPhoto() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ajouter_photo, container, false);

        return view;
    }
}