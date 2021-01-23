package afrimoov.tg.pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.List;

import afrimoov.tg.R;
import afrimoov.tg.activities.Accueil;
import afrimoov.tg.adaptor.GridSourceAdaptor;
import afrimoov.tg.db_manager.SourcesManager;
import afrimoov.tg.utilis.Source;

public class Home_Replay extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{


    private List<Source> sources;
    private GridView gridView;
    private GridSourceAdaptor adaptor;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_replay,null,true);
        gridView = view.findViewById(R.id.gridView);

        sources = new SourcesManager(getActivity()).getAll();
        adaptor = new GridSourceAdaptor(sources,getActivity());
        gridView.setAdapter(adaptor);
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Accueil accueil = (Accueil)getActivity();
        if (accueil != null) {

            accueil.activateFooter(3);
            accueil.selectedPage = 3;
            accueil.getSupportActionBar().setTitle(accueil.getString(R.string.replays));
        }
    }

    @Override
    public void onClick(View v) {
        Accueil accueil = (Accueil)getActivity();
        if (accueil != null) {
            accueil.setFragment(new Page_Selected_Replay());
        }
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Accueil accueil = (Accueil)getActivity();
        accueil.activateFooter(3);
        accueil.selectedPage = 3;
        accueil.getSupportActionBar().setTitle(accueil.getString(R.string.replays));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Accueil accueil = (Accueil)getActivity();
        if (accueil != null) {
            Page_Selected_Replay selected = new Page_Selected_Replay();
            Bundle bundle = new Bundle();
            bundle.putString("channel",sources.get(i).getSource_id());
            bundle.putString("channel_name",sources.get(i).getNom());
            selected.setArguments(bundle);
            accueil.setFragment(selected);
        }
    }
}
