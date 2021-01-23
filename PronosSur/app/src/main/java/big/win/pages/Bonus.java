package big.win.pages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import big.win.R;
import big.win.activities.Accueil;
import big.win.adapaters.Matchs_Adaptor;
import big.win.classes.Pronostique;
import big.win.db_managment.Pronos_Manager;

public class Bonus extends Fragment {

    private Pronos_Manager manager;
    private Matchs_Adaptor adaptor;
    private ListView listView;
    private Accueil activity;

    private List<Pronostique> pronostiques;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (Accueil)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_pronos,container,false);
        listView = view.findViewById(R.id.listView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        manager = new Pronos_Manager(activity);
        pronostiques = manager.getTodayBonus();
        adaptor = new Matchs_Adaptor(activity,pronostiques);
        listView.setAdapter(adaptor);
        activity.activateFooter(2);
    }
}
