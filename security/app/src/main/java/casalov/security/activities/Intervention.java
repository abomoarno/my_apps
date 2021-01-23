package casalov.security.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import casalov.security.R;
import casalov.security.db_managment.InterventionsManager;
import casalov.security.utils.Utils;

public class Intervention extends AppCompatActivity implements View.OnClickListener, PurchasesUpdatedListener {

    private BillingClient mBillingClient;
    private String sku;

    private static final int REQUEST_CODE = 1;
    private HashMap<String,String> params;

    private casalov.security.classes.Intervention intervention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intervention);
        findViewById(R.id.payer).setOnClickListener(this);
        List<casalov.security.classes.Intervention> interventions =
                new InterventionsManager(this).getUnpayInterventions();
        intervention = (interventions.size()>0)?interventions.get(0):null;

        if (intervention == null){
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putBoolean(Utils.IS_PAY_OK,true)
                    .apply();
            startActivity(new Intent(getApplicationContext(),Chargement.class));
            finish();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle(getString(R.string.intervention_bar_message));
            actionBar.setHomeAsUpIndicator(R.drawable.left_arrow);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mBillingClient = BillingClient.newBuilder(this).setListener(this).build();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.payer:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                sku = (preferences.getString(Utils.USER_TYPE,Utils.CLENT_PARTICULIER).
                        equals(Utils.CLENT_PARTICULIER))?Utils.INTERVENTION_PARTICULIER:Utils.INTERVENTION_ENTREPRISE;

                final List<String> skuList = new ArrayList<>();
                skuList.add(sku);

                mBillingClient.startConnection(new BillingClientStateListener() {
                    @Override
                    public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                        if (billingResponseCode == BillingClient.BillingResponse.OK) {
                            SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                            params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                            mBillingClient.querySkuDetailsAsync(params.build(),
                                    new SkuDetailsResponseListener() {
                                        @Override
                                        public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                            if (responseCode == BillingClient.BillingResponse.OK && (skuDetailsList != null)
                                                    && skuDetailsList.size()>0) {
                                                for (SkuDetails details : skuDetailsList) {
                                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                                            .setSku(details.getSku())
                                                            .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                                                            .build();
                                                    int response = mBillingClient.launchBillingFlow(Intervention.this,flowParams);
                                                }
                                            }
                                            else {
                                                startActivity(new Intent(Intervention.this,Accueil.class));
                                                finish();
                                            }
                                        }

                                    });
                        }

                        else {
                            startActivity(new Intent(Intervention.this,Accueil.class));
                            finish();
                        }
                    }
                    @Override
                    public void onBillingServiceDisconnected() {
                        // Try to restart the connection on the next request to
                        // Google Play by calling the startConnection() method.
                    }
                });
                break;
        }
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

        if (BillingClient.BillingResponse.OK == responseCode && purchases != null){
            for (Purchase purchase:purchases){
                try {
                    JSONObject object = new JSONObject(purchase.getOriginalJson());
                    String token = object.optString("purchaseToken");

                    mBillingClient.consumeAsync(token, new ConsumeResponseListener() {
                        @Override
                        public void onConsumeResponse(int responseCode, String purchaseToken) {
                            Log.i("Consumption","OK");
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Intervention.this);
            preferences.edit().putBoolean(Utils.IS_PAY_OK,true).apply();
            new InterventionsManager(Intervention.this).updateStatut(intervention.getId(),Utils.STATUT_OK);
            startActivity(new Intent(getApplicationContext(),Chargement.class));
            finish();
        }
    }

   /* private class GetToken extends AsyncTask {

        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialog = new ProgressDialog(Intervention.this,android.R.style.Theme_DeviceDefault_Dialog);
            mDialog.setCancelable(false);
            mDialog.setMessage("Please wait");
            mDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            token = responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Log.d("EDMT_ERROR",exception.toString());
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mDialog.dismiss();
        }
    }

   private void sendPayment() {

        RequestQueue queue = Volley.newRequestQueue(Intervention.this);
        StringRequest request = new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.toString().contains("Successful")){
                            Toast.makeText(Intervention.this, "Transaction successful !" ,Toast.LENGTH_SHORT).show();
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Intervention.this);
                            preferences.edit().putBoolean(Utils.IS_PAY_OK,true).apply();
                            new InterventionsManager(Intervention.this).updateStatut(intervention.getId(),Utils.STATUT_OK);
                            startActivity(new Intent(getApplicationContext(),Chargement.class));
                            finish();
                        }
                        else{
                            Toast.makeText(Intervention.this, "Transaction Failed !!" ,Toast.LENGTH_SHORT).show();
                        }
                        Log.d("EDMT_LOG",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_ERROR",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params == null)
                    return null;
                Map<String,String> newParams = new HashMap<>();

                for (String key:params.keySet()){

                    newParams.put(key,params.get(key));
                }

                return newParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){

            if (resultCode == RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                params = new HashMap<>();
                params.put("amount",intervention.getPrix()+"");
                params.put("nonce",strNonce);

                sendPayment();
            }

            else{
                Toast.makeText(this, "Some problem occures" ,Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == RESULT_CANCELED){
            Toast.makeText(this, "User Cancel" ,Toast.LENGTH_SHORT).show();
        }
        else{
            Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);

            Log.d("EDMT_ERROR",error.toString());
        }
    }

    private void submitPayment() {
        DropInRequest dropInRequest = new DropInRequest().clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this),REQUEST_CODE);
    }*/
}
