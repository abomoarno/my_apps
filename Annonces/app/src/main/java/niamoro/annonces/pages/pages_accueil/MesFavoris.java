package niamoro.annonces.pages.pages_accueil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.activities.Accueil;
import niamoro.annonces.activities.AnnonceView;
import niamoro.annonces.adapters.AnnonceAdapter;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.utils.Annonce;

public class MesFavoris extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private AnnonceAdapter adapter;
    private Accueil context;
    private List<Annonce> annonces;
    private LinearLayout empty;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = (Accueil) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.favories,null,false);
        listView = view.findViewById(R.id.listView);
        empty = view.findViewById(R.id.empty);
        listView.setEmptyView(empty);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        annonces = new AnnonceManager(context).getFavories();

        adapter = new AnnonceAdapter(context,annonces);
        listView.setAdapter(adapter);

        context.activeColor(3);
        Accueil.SELECTED_PAGE = 3;

        context.setTitre("Mes Favoris");
        context.setSubTitre("Les annonces qui vous intéressent");
        context.setBackEnable(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, AnnonceView.class);
        intent.putExtra("annonce",annonces.get(i).getId());
        startActivity(intent);
    }
}
