package niamoro.annonces.pages.pages_presentation;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import niamoro.annonces.R;

public class Personnaliser extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personnaliser,null,false);

        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"fonts/opensans_extrabold.ttf");

        TextView titre = view.findViewById(R.id.titre);
        TextView message = view.findViewById(R.id.message);

        titre.setTypeface(tf);
        message.setTypeface(tf);

        return view;
    }
}
