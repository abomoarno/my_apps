package niamoro.annonces.pages.pages_accueil;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import niamoro.annonces.R;
import niamoro.annonces.activities.Accueil;
import niamoro.annonces.activities.ChoixPays;
import niamoro.annonces.activities.Disclaimer;
import niamoro.annonces.activities.ModeCompact;
import niamoro.annonces.activities.Notifications;

public class More extends Fragment implements View.OnClickListener {

    private Accueil context;
    private TextView version;
    private TextView pays;
    private ImageView drapeau;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = (Accueil) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more,null,false);
        version = view.findViewById(R.id.version);
        pays = view.findViewById(R.id.pays);
        drapeau = view.findViewById(R.id.drapeau);

        view.findViewById(R.id.contact).setOnClickListener(this);
        view.findViewById(R.id.choix_pays).setOnClickListener(this);
        view.findViewById(R.id.notifications).setOnClickListener(this);
        view.findViewById(R.id.disclaimer).setOnClickListener(this);
        view.findViewById(R.id.evaluer).setOnClickListener(this);
        view.findViewById(R.id.more_apps).setOnClickListener(this);
        view.findViewById(R.id.compact).setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        String vers = "2019 Afrimoov Annonces v" ;
        try {
            PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0);
            vers += packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            vers += "1.0.0";
            e.printStackTrace();
        }
        version.setText(vers);

        /*if (Utils.PAYS != null && !Utils.PAYS.isEmpty()){
            Pays country = new PaysManager(context).getPays(Utils.PAYS);
            pays.setText(country.getNom());

            try {
                InputStream is = context.getAssets().open(country.getCode()+".png");
                Drawable drawable = Drawable.createFromStream(is,null);
                drapeau.setImageDrawable(drawable);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }*/

        context.setTitre("Options");
        context.setSubTitre("Réglages, contact et bien d'autres options ...");
        context.setBackEnable(true);

        context.activeColor(4);
        Accueil.SELECTED_PAGE = 4;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notifications:
                startActivity(new Intent(context, Notifications.class));
                break;
            case R.id.choix_pays:
                startActivity(new Intent(context, ChoixPays.class));
                break;
            case R.id.compact:
                startActivity(new Intent(context, ModeCompact.class));
                break;
            case R.id.evaluer:
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
            case R.id.disclaimer:
                startActivity(new Intent(getActivity(), Disclaimer.class));
                break;
            case R.id.more_apps:
                final String userName = "6692475906886734588";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://dev?id=" + userName)));
                }
                catch (Exception e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=" +userName)));
                }
                break;
            case R.id.contact:
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setData(Uri.parse("mailto:"));
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"afrimoov237@gmail.com"});
                sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Afrimoov Annonces");
                startActivity(Intent.createChooser(sendIntent,"Nous Contacter"));
                break;
        }
    }
}
