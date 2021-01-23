package dev.casalov.projetetincel.pages.DetailsRapport.informations_generales;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.PagesAdaptor;

/**
    Ce fragment présente les informations générales
    du projet sous forme de tabs
 */
public class TabInfosGenerales extends Fragment implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private DetailsRapport activity;
    private String project_id;

    public TabInfosGenerales(){
        DetailsRapport.selectedPage = 2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_infos_generales, null, false);
        viewPager = view.findViewById(R.id.container);
        activity = (DetailsRapport)getActivity();

        viewPager.addOnPageChangeListener(this);
        Bundle bundle = getArguments();
        project_id = bundle.getString("project_id");
        setupViewPager();
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setSubTitle("Informations Générales");
        DetailsRapport.selectedPage = 2;
    }

    private void setupViewPager(){
        PagesAdaptor adapter = new PagesAdaptor(getFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("project_id",project_id);

        Fragment genFrag = new InfosGeneales();
        genFrag.setArguments(bundle);
        adapter.addFragment(genFrag,"Généralités");

        Fragment clientFrag = new InfosClients();
        clientFrag.setArguments(bundle);
        adapter.addFragment(clientFrag,"Client");

        Fragment techFrag = new InfosTechniciens();
        techFrag.setArguments(bundle);
        adapter.addFragment(techFrag,"Techniciens");

        Fragment redactFrag = new InfosRedaction();
        redactFrag.setArguments(bundle);
        adapter.addFragment(redactFrag,"Rédaction");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i){
            case 0:
                activity.setSubTitle("Information Générales");
                break;
            case 1:
                activity.setSubTitle("Informations Clients");
                break;
            case 2:
                activity.setSubTitle("Liste des Techniciens");
                break;
            case 3:
                activity.setSubTitle("Information Rédaction du rapport");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

}
