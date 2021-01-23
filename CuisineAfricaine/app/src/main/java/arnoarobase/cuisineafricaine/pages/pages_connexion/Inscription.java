package arnoarobase.cuisineafricaine.pages.pages_connexion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import arnoarobase.cuisineafricaine.R;

/**
 * Comme son nom l'indique, cette page permet à un utilisateur de s'inscrire
 * pour pouvoir bénéficier de certains aspects de l'application
 */

public class Inscription extends Fragment {


    public Inscription() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.inscription, container, false);

        return view;
    }

}
