package arnoarobase.cuisineafricaine.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ListView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.adapters.PagesAdaptor;
import arnoarobase.cuisineafricaine.adapters.RecetteAdapter;
import arnoarobase.cuisineafricaine.adapters.RecetteRecyclerAdaptor;
import arnoarobase.cuisineafricaine.classes.Article;
import arnoarobase.cuisineafricaine.classes.Recette;
import arnoarobase.cuisineafricaine.db_mangment.RecettesManager;
import arnoarobase.cuisineafricaine.pages.teasers.Teaser1;
import arnoarobase.cuisineafricaine.pages.teasers.Teaser2;
import arnoarobase.cuisineafricaine.pages.teasers.Teaser3;

public class Accueil extends AppCompatActivity {

    private ViewPager teaser;

    private Teaser1 teaser1;
    private Teaser2 teaser2;
    private Teaser3 teaser3;

    private List<Recette> suggestions;
    private List<Recette> populaires;
    private List<Recette> recents;
    private List<Article> buzz;

    private RecyclerView recycler_suggestion;
    private RecyclerView recycler_populaire;
    private RecyclerView recycler_recent;
    private RecyclerView recycler_buzz;
    private RecyclerView recycler_a_la_une;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil);
        teaser = findViewById(R.id.teaser);
        recycler_suggestion = findViewById(R.id.suggestions);
        recycler_populaire = findViewById(R.id.populaires);
        recycler_recent = findViewById(R.id.nouveaux);
        recycler_buzz = findViewById(R.id.articles);
        recycler_a_la_une = findViewById(R.id.a_la_une);

        recycler_suggestion.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_populaire.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_recent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_buzz.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recycler_a_la_une.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Recette> recettes = new RecettesManager(this).getRecents(3);

        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();

        bundle.putString("recette",recettes.get(0).getId());
        bundle2.putString("recette",recettes.get(1).getId());
        bundle3.putString("recette",recettes.get(2).getId());

        teaser1 = new Teaser1();
        teaser2 = new Teaser2();
        teaser3 = new Teaser3();

        teaser1.setArguments(bundle);
        teaser2.setArguments(bundle2);
        teaser3.setArguments(bundle3);

        PagesAdaptor adaptor = new PagesAdaptor(getSupportFragmentManager());
        adaptor.addFragment(teaser1);
        adaptor.addFragment(teaser2);
        adaptor.addFragment(teaser3);
        teaser.setAdapter(adaptor);

        recycler_populaire.setAdapter(new RecetteRecyclerAdaptor(this,recettes, new RecetteRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Recette recette) {

            }
        }));
        recycler_recent.setAdapter(new RecetteRecyclerAdaptor(this,recettes, new RecetteRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Recette recette) {

            }
        }));
        recycler_buzz.setAdapter(new RecetteRecyclerAdaptor(this,recettes, new RecetteRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Recette recette) {

            }
        }));

        recycler_suggestion.setAdapter(new RecetteRecyclerAdaptor(this,recettes, new RecetteRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Recette recette) {

            }
        }));

        recycler_a_la_une.setAdapter(new RecetteRecyclerAdaptor(this,recettes, new RecetteRecyclerAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(Recette recette) {

            }
        }));


    }
}