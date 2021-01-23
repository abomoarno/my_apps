package casalov.security.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

import casalov.security.R;
import casalov.security.utils.Utils;

public class Abonnement extends AppCompatActivity implements View.OnClickListener, PurchasesUpdatedListener {

    private BillingClient mBillingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abonnement);
        findViewById(R.id.subscribe_6months).setOnClickListener(this);
        findViewById(R.id.subscribe_year).setOnClickListener(this);

        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.abonnement_title);
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        final List<String> skuList = new ArrayList<>();
        String sku;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        switch (view.getId()){
            case R.id.subscribe_6months:
                sku = (preferences.getString(Utils.USER_TYPE,Utils.CLENT_PARTICULIER).
                        equals(Utils.CLENT_PARTICULIER)) ? Utils.ABONNEMENT_PARTICULIER_6_MOIS:Utils.ABONNEMENT_ENTREPRISE_6_MOIS;
                break;
            case R.id.subscribe_year:
                sku = (preferences.getString(Utils.USER_TYPE,Utils.CLENT_PARTICULIER).
                        equals(Utils.CLENT_PARTICULIER)) ? Utils.ABONNEMENT_PARTICULIER_12_MOIS:Utils.ABONNEMENT_ENTREPRISE_12_MOIS;
                break;
            default:
                sku = (preferences.getString(Utils.USER_TYPE,Utils.CLENT_PARTICULIER).
                        equals(Utils.CLENT_PARTICULIER)) ? Utils.ABONNEMENT_PARTICULIER_6_MOIS:Utils.ABONNEMENT_ENTREPRISE_6_MOIS;
        }
        skuList.add(sku);
        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                    params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
                    mBillingClient.querySkuDetailsAsync(params.build(),
                            new SkuDetailsResponseListener() {
                                @Override
                                public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                    if (responseCode == BillingClient.BillingResponse.OK && (skuDetailsList != null)
                                            && skuDetailsList.size()>0) {
                                        for (SkuDetails details : skuDetailsList) {
                                            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                    .setSku(details.getSku())
                                                    .setType(BillingClient.SkuType.SUBS) // SkuType.SUB for subscription
                                                    .build();
                                            int response = mBillingClient.launchBillingFlow(Abonnement.this,flowParams);
                                        }
                                    }
                                }

                            });
                }

                else if (billingResponseCode == BillingClient.BillingResponse.BILLING_UNAVAILABLE
                        || billingResponseCode == BillingClient.BillingResponse.SERVICE_UNAVAILABLE
                        || billingResponseCode == BillingClient.BillingResponse.FEATURE_NOT_SUPPORTED){
                    Toast.makeText(Abonnement.this,"Assurez-vous que votre appareil" +
                            "supporte Google Play",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases !=null){
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit().putBoolean(Utils.IS_SUBSCRIBED,true).apply();
            startActivity(new Intent(this,Chargement.class));
            finish();
        }
    }
}