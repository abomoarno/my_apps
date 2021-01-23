package niamoro.comedy.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.activities.Accueil;
import niamoro.comedy.adaptor.GridComedienAdaptor;
import niamoro.comedy.db_manager.ComediensManager;
import niamoro.comedy.utilis.Comedien;

public class Home_Comediens extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
    private List<Comedien> comediens;
    private GridView gridView;
    private GridComedienAdaptor adaptor;

    private Accueil accueil;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_comediens,null,true);
        gridView = view.findViewById(R.id.gridView);
        accueil = (Accueil)getActivity();
        comediens = new ComediensManager(getActivity()).getAll(50);
        adaptor = new GridComedienAdaptor(comediens,getActivity());
        gridView.setAdapter(adaptor);
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (accueil != null) {
            accueil.activateFooter(3);
            accueil.toggleIndicator(false);
            accueil.toggleTitle(false);
            accueil.toogleIcone(true);
            accueil.selectedPage = 3;
        }
    }

    @Override
    public void onClick(View v) {
        if (accueil != null) {
            accueil.setFragment(new Comedien_Selected());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        accueil.desactivateFooter(3);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (accueil != null) {
            Comedien_Selected selected = new Comedien_Selected();
            Bundle bundle = new Bundle();
            bundle.putString("comedien", comediens.get(i).getComedien_id());
            bundle.putString("comedien_nom", comediens.get(i).getNom());
            selected.setArguments(bundle);
            accueil.setFragment(selected);
        }
    }
}