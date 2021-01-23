package niamoro.annonces.pages.pages_accueil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.activities.Accueil;
import niamoro.annonces.adapters.AnnonceAdapter;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.databases.managers.RechercheManager;
import niamoro.annonces.utils.Annonce;
import niamoro.annonces.utils.Recherche;
import niamoro.annonces.utils.Utils;

public class Resultats extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private List<Annonce> annonces;
    private AnnonceAdapter adapter;
    private String titre;
    private LinearLayout emptyView;
    private int selectedPage = 5;

    private SharedPreferences preferences;

    private InterstitialAd interstImage;
    private InterstitialAd interstVideo;

    private Annonce annonce;

    private Accueil context;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = (Accueil) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resultat,null,false);
        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        emptyView = view.findViewById(R.id.empty);
        listView.setEmptyView(emptyView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        preferences = PreferenceManager.getDefaultSharedPreferences(context);

        AnnonceManager manager = new AnnonceManager(context);
        Bundle bundle = getArguments();
        annonces = manager.getAnnonces(
                bundle.getString("type_bien"),bundle.getString("type_operation"),
                bundle.getString("ville"),bundle.getString("pays"));
        adapter = new AnnonceAdapter(context,annonces);
        listView.setAdapter(adapter);
        createHistorique();
        context.activeColor(5);
        Accueil.SELECTED_PAGE = selectedPage;

        context.setTitre(titre);
        context.setSubTitre("Annonces correspondant à vos critères");
        context.setBackEnable(true);

        initAds();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        preferences.edit().putInt("annonces_vues",preferences.getInt("annonces_vues",0)+1).apply();
        annonce = annonces.get(i);
        showAds();
    }

    private void createHistorique(){
        Bundle bundle = getArguments();

        if ((bundle == null) || (bundle.getBoolean("from"))) {
            if (bundle != null) {
                titre = bundle.getString("titre");

                if (bundle.getBoolean("from"))
                    selectedPage = 10;
            }

            return;
        }

        String id = FirebaseDatabase.getInstance().getReference().push().getKey();

        Recherche recherche = new Recherche(id);
        recherche.setDate(new Date().getTime());
        recherche.setPays(Utils.PAYS);
        recherche.setTypeBien(bundle.getString("type_bien"));
        recherche.setTypeOperation(bundle.getString("type_operation"));
        recherche.setVille(bundle.getString("ville"));
        titre = "Trouver " + recherche.getTitre();
        if (Utils.NOTIFICATIONS)
            recherche.setStatus(1);
        new RechercheManager(context).insertRecherche(recherche);
    }

    private void showAds(){
        if (preferences.getInt("annonces_vues",1) %3 == 0) {
            if (interstVideo.isLoaded())
                interstVideo.show();
            else if (interstImage.isLoaded())
                interstImage.show();
            else {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(annonce.getLien()));
                startActivity(intent);
            }
        }
        else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(annonce.getLien()));
            startActivity(intent);
        }
    }
    private void initAds(){
        interstImage = new InterstitialAd(context);
        interstVideo = new InterstitialAd(context);

        interstImage.setAdUnitId(getString(R.string.interst_image));
        interstVideo.setAdUnitId(getString(R.string.interst_video));

        final AdRequest request = new AdRequest.Builder().build();

        interstImage.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstImage.loadAd(request);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(annonce.getLien()));
                startActivity(intent);
            }
        });
        interstVideo.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstVideo.loadAd(request);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(annonce.getLien()));
                startActivity(intent);
            }
        });

        interstVideo.loadAd(request);
        interstImage.loadAd(request);
    }
}
