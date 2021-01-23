package arnoarobase.cuisineafricaine.pages.pages_articles;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.activities.Articles;
import arnoarobase.cuisineafricaine.adapters.ArticleAdapter;
import arnoarobase.cuisineafricaine.classes.Article;
import arnoarobase.cuisineafricaine.db_mangment.ArticleManager;

/**
 *  Cette Classe permet d'afficher une liste d'articles,
 *  suivant un critère de recherche.
 */

public class ListArticles extends Fragment implements AdapterView.OnItemClickListener{
    private List<Article> articles;
    private ArticleManager manager;
    private ListView listView;
    private ArticleAdapter adapter;

    private Articles activity;

    public ListArticles() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Articles)getActivity();
        manager = new ArticleManager(activity);
        Articles.selectedPage = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_articles, container, false);
        listView = view.findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        articles = manager.getArticles();
        adapter = new ArticleAdapter(activity,articles);
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle bundle = new Bundle();
        bundle.putString("article",articles.get(i).getId());
        Fragment fragment = new DetailsArticle();
        fragment.setArguments(bundle);
        activity.setPage(fragment);
    }
}
