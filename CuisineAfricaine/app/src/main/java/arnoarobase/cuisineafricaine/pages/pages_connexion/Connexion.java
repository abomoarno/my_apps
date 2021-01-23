package arnoarobase.cuisineafricaine.pages.pages_connexion;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.activities.Compte;

/**
 * C'est cette page qui permet à un utilisateur d'entrer ses
 * identifiants afin de se connecter.
 */

public class Connexion extends Fragment {

    private Compte activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (Compte)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.connexion, container, false);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity = null;
    }
}
