package big.win.pages;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import big.win.R;
import big.win.activities.Accueil;

public class AvantagesGoal extends Fragment implements View.OnClickListener {

    private Accueil activity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (Accueil)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.avantages_goal,container,false);
        view.findViewById(R.id.subscription).setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.activateFooter(6);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = new Payement();
        Bundle b = new Bundle();
        b.putString("type","goal");
        fragment.setArguments(b);
        activity.setFragment(fragment);
    }
}
