package afrimoov.ke.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import afrimoov.ke.R;
import afrimoov.ke.activities.Accueil;
import afrimoov.ke.activities.ReplayShow;
import afrimoov.ke.adaptor.GridTvAdaptor;
import afrimoov.ke.db_manager.TvsManager;
import afrimoov.ke.utilis.Live_Tv;

public class All_Direct_Tv extends Fragment implements AdapterView.OnItemClickListener{

        private GridView gridView;
        private GridTvAdaptor adaptor;
        private List<Live_Tv> tvs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.all_directs,null,true);

        gridView = view.findViewById(R.id.gridView);
        tvs = new TvsManager(getActivity()).getAll();
        adaptor = new GridTvAdaptor(tvs, getActivity());
        gridView.setAdapter(adaptor);
        gridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Accueil accueil = (Accueil)getActivity();
        if (accueil != null) {
            accueil.activateFooter(2);
            accueil.selectedPage = 2;
            accueil.getSupportActionBar().setTitle(accueil.getString(R.string.tv_channel));
        }
    }
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Accueil accueil = (Accueil)getActivity();
        accueil.activateFooter(2);
        accueil.selectedPage = 2;
        accueil.getSupportActionBar().setTitle(accueil.getString(R.string.tv_channel));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), ReplayShow.class);
        intent.putExtra("videoId",tvs.get(i).getTv_id());
        intent.putExtra("videoPlateforme",tvs.get(i).getPlateforme());
        intent.putExtra("videoType","live");
        startActivity(intent);

    }
}
