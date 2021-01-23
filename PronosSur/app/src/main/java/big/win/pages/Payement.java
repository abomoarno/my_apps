package big.win.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cinetpay.sdkjs.CinetPayActivity;

import java.util.Date;

import big.win.R;
import big.win.activities.Accueil;
import big.win.activities.Payment;

import static big.win.classes.Utils.GOAL_12_MOIS;
import static big.win.classes.Utils.GOAL_1_MOIS;
import static big.win.classes.Utils.GOAL_3_MOIS;
import static big.win.classes.Utils.GOAL_6_MOIS;
import static big.win.classes.Utils.PREMIUM_12_MOIS;
import static big.win.classes.Utils.PREMIUM_1_MOIS;
import static big.win.classes.Utils.PREMIUM_3_MOIS;
import static big.win.classes.Utils.PREMIUM_6_MOIS;

public class Payement extends Fragment implements View.OnClickListener {

    private Accueil activity;
    private String type;

    private Button un_mois;
    private Button trois_mois;
    private Button six_mois;
    private Button douze_mois;

    private int prix_1_mois;
    private int prix_3_mois;
    private int prix_6_mois;
    private int prix_12_mois;

    private int amount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Accueil)getActivity();
        type = getArguments().getString("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payment,container,false);

        un_mois = view.findViewById(R.id.one_month);
        trois_mois = view.findViewById(R.id.three_month);
        six_mois = view.findViewById(R.id.six_month);
        douze_mois = view.findViewById(R.id.one_year);

        un_mois.setOnClickListener(this);
        trois_mois.setOnClickListener(this);
        six_mois.setOnClickListener(this);
        douze_mois.setOnClickListener(this);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (type.equals("premium")) {
            activity.activateFooter(7);

            prix_1_mois = PREMIUM_1_MOIS;
            prix_3_mois = PREMIUM_3_MOIS;
            prix_6_mois = PREMIUM_6_MOIS;
            prix_12_mois = PREMIUM_12_MOIS;
        }
        else {
            activity.activateFooter(8);

            prix_1_mois = GOAL_1_MOIS;
            prix_3_mois = GOAL_3_MOIS;
            prix_6_mois = GOAL_6_MOIS;
            prix_12_mois = GOAL_12_MOIS;
        }

        un_mois.setText(prix_1_mois + " EUROS");
        trois_mois.setText(prix_3_mois + " EUROS");
        six_mois.setText(prix_6_mois + " EUROS");
        douze_mois.setText(prix_12_mois + " EUROS");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.one_month:
                amount = prix_1_mois;
                break;
            case R.id.three_month:
                amount = prix_3_mois;
                break;
            case R.id.six_month:
                amount = prix_6_mois;
                break;
            case R.id.one_year:
                amount = prix_12_mois;
                break;
        }

        startPayement();
    }

    private void startPayement(){

        String api_key = "16214868215bbe4166f02da5.73088131"; // A remplacer par votre clé API
        int site_id = 329705; // A remplacer par votre Site ID
        String notify_url = "afrimoov.com";
        String trans_id = String.valueOf(new Date().getTime());
        String currency = "CFA";
        String designation = "Achat test";
        String custom = "Premier test";

        Intent intent = new Intent(activity, Payment.class);
        intent.putExtra(CinetPayActivity.KEY_API_KEY, api_key);
        intent.putExtra(CinetPayActivity.KEY_SITE_ID, site_id);
        intent.putExtra(CinetPayActivity.KEY_NOTIFY_URL, notify_url);
        intent.putExtra(CinetPayActivity.KEY_TRANS_ID, trans_id);
        intent.putExtra(CinetPayActivity.KEY_AMOUNT, 500);
        intent.putExtra(CinetPayActivity.KEY_CURRENCY, currency);
        intent.putExtra(CinetPayActivity.KEY_DESIGNATION, designation);
        intent.putExtra(CinetPayActivity.KEY_CUSTOM, custom);
        startActivity(intent);

    }
}
