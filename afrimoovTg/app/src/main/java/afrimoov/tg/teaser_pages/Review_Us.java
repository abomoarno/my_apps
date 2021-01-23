package afrimoov.tg.teaser_pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import afrimoov.tg.R;

public class Review_Us extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.review_us,null,true);
        Button review = view.findViewById(R.id.review);
        review.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        final String appName = getActivity().getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                    "market://details?id=" + appName)));
        }
        catch (Exception e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                    "https://play.google.com/store/apps/details?id=" + appName)));
        }
    }

}
