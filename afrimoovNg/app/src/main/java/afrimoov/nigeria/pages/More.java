package afrimoov.nigeria.pages;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import afrimoov.nigeria.R;

import afrimoov.nigeria.activities.Accueil;
import afrimoov.nigeria.activities.Disclaimer;
import afrimoov.nigeria.activities.ModeCompact;
import afrimoov.nigeria.activities.Notifications;

public class More extends Fragment implements View.OnClickListener{

    private TextView contactText;
    private TextView reviewText;
    private TextView notifText;
    private TextView dataText;
    private TextView moreText;
    private TextView disclaimerText;

    private TextView version;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more,null,true);

        contactText =view.findViewById(R.id.contactText);
        reviewText =view.findViewById(R.id.reviewText);
        notifText =view.findViewById(R.id.notificationText);
        dataText =view.findViewById(R.id.datatext);
        moreText =view.findViewById(R.id.moreText);
        disclaimerText =view.findViewById(R.id.disclaimerText);
        version = view.findViewById(R.id.app_version);

        String vers = "2019 Afrimoov TV v" ;
        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
            vers += packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            vers += "1.0.0";
            e.printStackTrace();
        }
        version.setText(vers);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/opensans_regular.ttf");
        contactText.setTypeface(tf);
        reviewText.setTypeface(tf);
        notifText.setTypeface(tf);
        dataText.setTypeface(tf);
        moreText.setTypeface(tf);
        disclaimerText.setTypeface(tf);
        version.setTypeface(tf);

        view.findViewById(R.id.contact).setOnClickListener(this);
        view.findViewById(R.id.review).setOnClickListener(this);
        view.findViewById(R.id.notification).setOnClickListener(this);
        view.findViewById(R.id.data).setOnClickListener(this);
        view.findViewById(R.id.more).setOnClickListener(this);
        view.findViewById(R.id.disclaimer).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Accueil accueil = (Accueil)getActivity();
        if (accueil != null) {
            accueil.activateFooter(4);
            accueil.selectedPage = 4;
            accueil.getSupportActionBar().setTitle(accueil.getString(R.string.autres));
        }
    }
    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Accueil accueil = (Accueil)getActivity();
        accueil.activateFooter(4);
        accueil.selectedPage = 4;
        accueil.getSupportActionBar().setTitle(accueil.getString(R.string.autres));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.contact:
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setData(Uri.parse("mailto:"));
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"afrimoov237@gmail.com"});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT,"TV NIGERIA");
                startActivity(Intent.createChooser(sendIntent,"Contact Us"));

                break;
            case R.id.review:
                final String appName = getActivity().getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                            "market://details?id=" + appName)));
                }
                catch (Exception e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                            "https://play.google.com/store/apps/details?id=" + appName)));
                }
                break;
            case R.id.notification:
                startActivity(new Intent(getActivity(), Notifications.class));
                break;
            case R.id.data:
                startActivity(new Intent(getActivity(), ModeCompact.class));
                break;
            case R.id.disclaimer:
                startActivity(new Intent(getActivity(), Disclaimer.class));
                break;
            case R.id.more:
                final String userName = "Afrimoov+Apps+:+African+Apps+and+Games";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                            "market://developer?id=" + userName)));
                }
                catch (Exception e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("" +
                            "https://play.google.com/store/apps/developer?id=" +userName)));
                }
                break;
        }
    }

}
