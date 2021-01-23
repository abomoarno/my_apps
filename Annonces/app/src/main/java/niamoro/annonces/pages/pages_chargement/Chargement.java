package niamoro.annonces.pages.pages_chargement;

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

public class Chargement extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading,null,false);

        TextView title = view.findViewById(R.id.title);
        TextView version = view.findViewById(R.id.app_version);

        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),"fonts/opensans_extrabold.ttf");
        title.setTypeface(tf);
        version.setTypeface(tf);

        return view;
    }
}
