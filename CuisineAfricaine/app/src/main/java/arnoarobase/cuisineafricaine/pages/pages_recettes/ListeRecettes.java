package arnoarobase.cuisineafricaine.pages.pages_recettes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.activities.Recettes;

/**
 *
 */
public class ListeRecettes extends Fragment {

    private Recettes activity;
    private GridView gridView;
    public ListeRecettes() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.liste_recettes, container, false);

        return view;
    }
}