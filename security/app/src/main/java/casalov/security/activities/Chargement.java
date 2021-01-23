package casalov.security.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;

import java.util.List;

import casalov.security.R;
import casalov.security.notifications.AlarmInit;
import casalov.security.utils.Utils;


public class Chargement extends Activity implements View.OnClickListener, PurchasesUpdatedListener {

    private SharedPreferences preferences;
    private BillingClient mBillingClient;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmInit.initNewFacture(this);
        AlarmInit.initGetFactures(this);
        AlarmInit.initGetEntreprise(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.chargement);
        Button connexion = findViewById(R.id.connexion);
        Button create_account = findViewById(R.id.creer_compte);
        Button subscribe = findViewById(R.id.abonnement);
        create_account.setOnClickListener(this);
        subscribe.setOnClickListener(this);
        connexion.setOnClickListener(this);
        dialog = new ProgressDialog(this);
        dialog.setTitle("SKEM EN COURS DE CHARGEMENT");
        dialog.setMessage("Patientez quelques instants ... ! ");
        dialog.setCancelable(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verifySubscribe();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.connexion:
                intent = new Intent(getApplicationContext(),Connexion.class);
                startActivity(intent);
                break;
            case R.id.creer_compte:
                intent = new Intent(getApplicationContext(),Inscription.class);
                startActivity(intent);
                break;
            case R.id.abonnement:
                intent = new Intent(getApplicationContext(),Abonnement.class);
                startActivity(intent);
                break;
        }
    }

    private void loadParams(){
        Utils.is_connected = preferences.getBoolean(Utils.IS_CCONNECTED,false);
        Utils.is_pay_ok = preferences.getBoolean(Utils.IS_PAY_OK,true);
        Utils.is_subscribed = preferences.getBoolean(Utils.IS_SUBSCRIBED,false);

        Utils.user_name = preferences.getString(Utils.USER_NAME,"");
        Utils.user_mail = preferences.getString(Utils.USER_MAIL,"");
        Utils.user_phone = preferences.getString(Utils.USER_PHONE,"");
        Utils.user_location = preferences.getString(Utils.USER_LOCATION,"");

        if (Utils.is_connected){
            Intent intent;

            if (!Utils.is_pay_ok){
                intent = new Intent(Chargement.this,Intervention.class);
                startActivity(intent);
                finish();
            }
            else if (Utils.is_subscribed){
                intent = new Intent(Chargement.this,Accueil.class);
                startActivity(intent);
                finish();
            }
            else {
                findViewById(R.id.must_subscribe).setVisibility(View.VISIBLE);
                findViewById(R.id.must_connect).setVisibility(View.GONE);
            }
        }
        else {
            findViewById(R.id.must_subscribe).setVisibility(View.GONE);
            findViewById(R.id.must_connect).setVisibility(View.VISIBLE);
        }
    }

    private void verifySubscribe(){
        dialog.show();
        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                if (responseCode == BillingClient.BillingResponse.OK){
                    Purchase.PurchasesResult result = mBillingClient.queryPurchases(BillingClient.SkuType.SUBS);
                    if (!(result == null)){
                        List<Purchase> purchases = result.getPurchasesList();
                        if (purchases !=null && purchases.size()>0) {
                            for (Purchase purchase : purchases) {
                                long diff = (System.currentTimeMillis() - purchase.getPurchaseTime());
                                diff /= 1000;
                                diff /= 3600;
                                diff /= 24;
                                if ((purchase.getSku().equals(Utils.ABONNEMENT_ENTREPRISE_6_MOIS)
                                        || purchase.getSku().equals(Utils.ABONNEMENT_PARTICULIER_6_MOIS)) && diff < 183) {
                                    preferences.edit().putBoolean(Utils.IS_SUBSCRIBED,true).apply();
                                    break;
                                } else if ((purchase.getSku().equals(Utils.ABONNEMENT_ENTREPRISE_12_MOIS)
                                        || purchase.getSku().equals(Utils.ABONNEMENT_PARTICULIER_12_MOIS)) && diff < 365) {
                                    preferences.edit().putBoolean(Utils.IS_SUBSCRIBED,true).apply();
                                    break;
                                }
                            }
                        }
                        else{
                            preferences.edit().putBoolean(Utils.IS_SUBSCRIBED,false).apply();
                        }
                    }
                }

                loadParams();

                dialog.dismiss();
            }

            @Override
            public void onBillingServiceDisconnected() {
                loadParams();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

    }
}
