package arnoarobase.cuisineafricaine.pages.pages_connexion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import arnoarobase.cuisineafricaine.R;

/**
 * Lorsque l'utilisateur décide de se connecter ou essaye d'entrer dans une
 * section de l'application réservée aux utilisateurs connectés, il est renvoyé
 * sur cette page qui lui permet alors soit de se connecter ou de créer un compte
 */

public class ChoixConIns extends Fragment {


    public ChoixConIns() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.choix_con_ins, container, false);

        return view;
    }

}
