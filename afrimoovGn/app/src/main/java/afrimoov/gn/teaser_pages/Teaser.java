package afrimoov.gn.teaser_pages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import afrimoov.gn.R;
import afrimoov.gn.activities.Accueil;
import afrimoov.gn.pages.All_Direct_Tv;

public class Teaser extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teaser,null,true);
        Button discover = view.findViewById(R.id.discover);
        discover.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Accueil accueil = (Accueil)getActivity();
        accueil.setFragment(new All_Direct_Tv());
    }
}
