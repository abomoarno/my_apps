package dev.casalov.projetetincel.pages.detailsDevis;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsDevis;
import dev.casalov.projetetincel.adaptors.PagesAdaptor;

public class TabDetailsDevis extends Fragment implements ViewPager.OnPageChangeListener {

    private DetailsDevis activity;
    private String project_id;
    private static ViewPager viewPager;

    public TabDetailsDevis() {
        DetailsDevis.selectedDevis = 2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_details_devis,null,true);
        activity = (DetailsDevis) getActivity();

        viewPager = view.findViewById(R.id.container);
        viewPager.addOnPageChangeListener(this);
        Bundle bundle = getArguments();
        project_id = bundle.getString("devis_id");
        setupViewPager();
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void setupViewPager(){
        PagesAdaptor adapter = new PagesAdaptor(getFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putString("devis_id",project_id);

        Fragment genFrag = new InfosGenerales();
        genFrag.setArguments(bundle);
        adapter.addFragment(genFrag,"Généralités");

        Fragment clientFrag = new InfosClients();
        clientFrag.setArguments(bundle);
        adapter.addFragment(clientFrag,"Client");

        Fragment techFrag = new Charges();
        techFrag.setArguments(bundle);
        adapter.addFragment(techFrag,"Charges");

        Fragment redactFrag = new Prestations();
        redactFrag.setArguments(bundle);
        adapter.addFragment(redactFrag,"Prestations");

        viewPager.setAdapter(adapter);
    }
    public static void setPage(int item){
        viewPager.setCurrentItem(item);
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
                activity.setSubTitle("Liste des Charges");
                break;
            case 3:
                activity.setSubTitle("Prestations");
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onStart() {
        super.onStart();
        DetailsDevis.selectedDevis = 2;
    }
}
