package afrimoov.tg.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import java.util.List;

import afrimoov.tg.R;
import afrimoov.tg.activities.Accueil;
import afrimoov.tg.activities.ReplayShow;
import afrimoov.tg.adaptor.GridReplayAdaptor;
import afrimoov.tg.db_manager.ReplaysManager;
import afrimoov.tg.utilis.Replay;

public class Page_Selected_Replay extends Fragment implements AdapterView.OnItemClickListener{

    private GridView gridView;
    private GridReplayAdaptor adaptor;
    private String title = "Replays ";
    private List<Replay> replays;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selected_replay,null,true);
        gridView = view.findViewById(R.id.gridView);
        Bundle bundle = getArguments();
        final String channel = bundle.getString("channel");
        title += bundle.getString("channel_name");

        replays = new ReplaysManager(getActivity()).getFromTv(channel);

        adaptor = new GridReplayAdaptor(replays,getActivity());
        gridView.setAdapter(adaptor);
        gridView.setOnItemClickListener(this);

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Accueil accueil = (Accueil)getActivity();
        if (accueil != null) {
            accueil.selectedPage = 5;
            accueil.activateFooter(3);
            accueil.getSupportActionBar().setTitle(title);
            accueil.toogleIndicator(true);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), ReplayShow.class);
        intent.putExtra("videoId",replays.get(i).getReplay_id());
        intent.putExtra("videoPlateforme",replays.get(i).getPlateforme());
        intent.putExtra("videoType","replay");
        startActivity(intent);
    }
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Accueil accueil = (Accueil)getActivity();
        accueil.activateFooter(3);
        accueil.selectedPage = 5;
        accueil.getSupportActionBar().setTitle(title);
        accueil.toogleIndicator(true);
    }
    @Override
    public void onStop() {
        super.onStop();
        Accueil accueil = (Accueil)getActivity();
        accueil.toogleIndicator(false);
    }
}
