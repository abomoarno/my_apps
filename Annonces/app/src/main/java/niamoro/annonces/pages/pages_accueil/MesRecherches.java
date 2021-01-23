package niamoro.annonces.pages.pages_accueil;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.activities.Accueil;
import niamoro.annonces.adapters.RechercheAdapter;
import niamoro.annonces.databases.managers.RechercheManager;
import niamoro.annonces.utils.Recherche;

public class MesRecherches extends Fragment {

    private ListView listView;
    private Accueil context;
    private RechercheAdapter adapter;
    private List<Recherche> recherches;
    private LinearLayout empty;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = (Accueil) getActivity();;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recherches,null,false);
        listView = view.findViewById(R.id.listView);
        empty = view.findViewById(R.id.empty);
        listView.setEmptyView(empty);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recherches = new RechercheManager(context).getRecherches();
        adapter = new RechercheAdapter(context,recherches);
        listView.setAdapter(adapter);

        context.activeColor(2);
        Accueil.SELECTED_PAGE = 2;

        context.setTitre("Historique");
        context.setSubTitre("Liste de vos recherches les plus récentes");
        context.setBackEnable(true);
    }
}
