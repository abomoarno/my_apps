package dev.casalov.projetetincel.pages.detailsDevis;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsDevis;
import dev.casalov.projetetincel.db_mangment.DevisManager;
import dev.casalov.projetetincel.db_mangment.PrestationsManager;
import dev.casalov.projetetincel.utils.Prestation;

public class AccueilDetailsDevis extends Fragment implements View.OnClickListener {

    private DetailsDevis activity;
    private String devis_id;

    public AccueilDetailsDevis() {
        DetailsDevis.selectedDevis = 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accueil_details_devis,null,true);
        activity = (DetailsDevis) getActivity();

        view.findViewById(R.id.supprimer).setOnClickListener(this);
        view.findViewById(R.id.edit).setOnClickListener(this);
        view.findViewById(R.id.generate).setOnClickListener(this);

        Bundle bundle = getArguments();
        devis_id = bundle.getString("devis_id");

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit:
                Fragment fragment = new TabDetailsDevis();
                Bundle bundle = new Bundle();
                bundle.putString("devis_id", devis_id);
                fragment.setArguments(bundle);
                activity.setFragment(fragment);
                break;
            case R.id.generate:
                JSONObject data = constructJson();
                new SendDevis().execute("http://afrimoov.com/etincel/devis.php",data.toString());
                break;
            case R.id.supprimer:
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("Suppression du Rapport !!");
                dialog.setMessage("Voulez-vous Vraiment Supprimer ce dévis ? Attention !! Cette action est irrevrsible !!!");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new DevisManager(activity).delete(devis_id);
                        activity.onBackPressed();
                    }
                });
                dialog.show();
                break;
        }
    }

    private JSONObject constructJson(){
        JSONObject postData = new JSONObject();

        try {
            postData.put("devis_id",DetailsDevis.devis.getDevis_id());
            postData.put("titre",DetailsDevis.devis.getNom());
            postData.put("client_nom",DetailsDevis.devis.getClient_nom());
            postData.put("client_entreprise",DetailsDevis.devis.getClient_entreprise());
            postData.put("client_id",DetailsDevis.devis.getClient_id());
            postData.put("client_mail",DetailsDevis.devis.getClient_mail());
            postData.put("client_mobile",DetailsDevis.devis.getClient_mobile());
            postData.put("client_fixe",DetailsDevis.devis.getClient_fixe());
            postData.put("client_adresse",DetailsDevis.devis.getClient_adresse());
            postData.put("ville",DetailsDevis.devis.getVille());
            postData.put("date_redaction",DetailsDevis.devis.getDate_document());
            postData.put("date_realisation",DetailsDevis.devis.getDate_realisation());
            postData.put("redacteur",DetailsDevis.devis.getRedacteur());
            postData.put("poste_redacteur",DetailsDevis.devis.getRedacteur_poste());
            postData.put("charges_client",DetailsDevis.devis.getCharges_client());
            postData.put("charges_etincel",DetailsDevis.devis.getCharges_etincel());

            JSONArray prestations = new JSONArray();
            List<Prestation> myPrests = new PrestationsManager(activity).getFromDevis(DetailsDevis.devis.getDevis_id());

            for (Prestation prestation:myPrests){
                prestations.put(prestation.getNom()+";"+prestation.getPrix()+";"+prestation.getQuantite());
            }
            postData.put("prestations",prestations);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("JSON",postData.toString());
        return postData;
    }

    private class SendDevis extends AsyncTask<String, Void, String>{

        ProgressDialog dialog = new ProgressDialog(activity);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setTitle("Construction du document");
            dialog.setMessage("Veuillez Patienter ...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            HttpURLConnection connection = null;

            try {

                connection = (HttpURLConnection) new URL(strings[0]).openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);

                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.writeBytes("PostData=" + strings[1]);
                dos.flush();
                dos.close();

                InputStream in = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(in);

                int inputStreamData = isr.read();

                while (inputStreamData != -1){
                    char current = (char) inputStreamData;
                    inputStreamData = isr.read();
                    data += current;
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }

            finally {
                if (connection != null)
                    connection.disconnect();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
            startActivity(intent);
            dialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DetailsDevis.selectedDevis = 1;
    }
}
