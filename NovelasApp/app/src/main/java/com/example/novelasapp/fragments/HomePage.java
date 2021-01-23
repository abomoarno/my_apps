package com.example.novelasapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.novelasapp.R;
import com.example.novelasapp.activities.MainActivity;
import com.example.novelasapp.adapters.SerieAdapter;
import com.example.novelasapp.entities.Serie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomePage extends Fragment implements AdapterView.OnItemClickListener {

    private MainActivity context;

    private List<Serie> series;
    private ListView listView;
    private SerieAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page, container, false);

        context = (MainActivity) getActivity();

        listView = view.findViewById(R.id.lis_view);

        series = new ArrayList<>();

        adapter = new SerieAdapter(context, series);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getSeriesFromFirebase();

        context.setTitre("Nouvelas App");
    }

    private void getSeriesFromFirebase(){

        FirebaseFirestore.getInstance()
                .collection("series")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Serie serie = document.toObject(Serie.class);
                        serie.setSerie_id(document.getId());
                        series.add(serie);
                        series.add(serie);
                        series.add(serie);
                    }

                    Collections.shuffle(series);

                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle args = new Bundle();
        args.putParcelable("serie",series.get(i));

        Fragment fragment = new DetailsSerie();

        fragment.setArguments(args);

        context.setPage(fragment, true);
    }
}
