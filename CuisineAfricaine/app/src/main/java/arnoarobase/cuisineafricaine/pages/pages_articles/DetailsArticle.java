package arnoarobase.cuisineafricaine.pages.pages_articles;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.activities.Articles;
import arnoarobase.cuisineafricaine.classes.Article;
import arnoarobase.cuisineafricaine.db_mangment.ArticleManager;

/**
 * Cette classe permet de lire un article selectionné
 */

public class DetailsArticle extends Fragment {

    private Articles activity;
    private WebView webView;
    private AdView adView;
    private String article_id;
    private Article article;

    public DetailsArticle() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Articles)getActivity();
        article_id = getArguments().getString("article");
        article = new ArticleManager(activity).getArticle(article_id);
        Articles.selectedPage = 2;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_article, container, false);
        webView = view.findViewById(R.id.webview);
        adView = view.findViewById(R.id.adView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setTitre(article.getTitre());
        webView.loadUrl(article.getUrl());
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("articles").child(article_id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int views = dataSnapshot.child("vues").getValue(Integer.class);
                ref.child("vues").setValue(views+1);
                article.setViews(views+1);
                new ArticleManager(activity).updateArticle(article);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}