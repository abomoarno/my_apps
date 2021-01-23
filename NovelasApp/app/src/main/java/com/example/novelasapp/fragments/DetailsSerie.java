package com.example.novelasapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.novelasapp.R;
import com.example.novelasapp.activities.MainActivity;
import com.example.novelasapp.adapters.EpisodeAdapter;
import com.example.novelasapp.entities.Episode;
import com.example.novelasapp.entities.Serie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailsSerie extends Fragment {

    private List<Episode> episodes;
    private ListView listView;
    private EpisodeAdapter adapter;

    private Serie serie;

    private MainActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_serie, container, false);
        context = (MainActivity) getActivity();

        listView = view.findViewById(R.id.lis_view);
        episodes = new ArrayList<>();

        adapter = new EpisodeAdapter(context, episodes);

        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();

        if (args != null){
            serie = args.getParcelable("serie");

            getEpisodesFromFirebase();

            context.setTitre(serie.getTitle());
        }


    }

    private void getEpisodesFromFirebase(){

        FirebaseFirestore.getInstance()
                .collection("episodes")
                .whereEqualTo("serie_id",serie.getSerie_id())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        Log.e("Episode",document.getId());
                        Episode episode = document.toObject(Episode.class);
                        episode.setEpisode_id(document.getId());
                        episodes.add(episode);
                        episodes.add(episode);
                        episodes.add(episode);
                    }

                    Collections.shuffle(episodes);

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
