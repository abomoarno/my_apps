package wiredstartups.yakjayexpo.resto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import wiredstartups.yakjayexpo.R;
import wiredstartups.yakjayexpo.entry_exit.EntryExit;

public class HomePage extends Fragment {

    private Resto context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.resto_home, container, false);

        context = (Resto) getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        context.setTitre("DirLabas Expo 2019");
    }
}
