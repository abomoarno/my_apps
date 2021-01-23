package wiredstartups.yakjayexpo.entry_exit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import wiredstartups.yakjayexpo.R;

public class HomePage extends Fragment {

    private EntryExit context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entry_exit_home, container, false);

        context = (EntryExit)getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        context.setTitre("DirLabas Expo 2019");
    }
}
