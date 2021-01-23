package dev.casalov.projetetincel.pages.DetailsRapport;

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
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.db_mangment.AppareilsMesureManager;
import dev.casalov.projetetincel.db_mangment.ElementsManager;
import dev.casalov.projetetincel.db_mangment.MaintenanceManager;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.db_mangment.PhotoManager;
import dev.casalov.projetetincel.db_mangment.RapportsManager;
import dev.casalov.projetetincel.db_mangment.SurveillerConformiteManager;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;
import dev.casalov.projetetincel.pages.DetailsRapport.informations_generales.TabInfosGenerales;
import dev.casalov.projetetincel.utils.Maintenance;
import dev.casalov.projetetincel.utils.Operation;
import dev.casalov.projetetincel.utils.Photo;
import dev.casalov.projetetincel.utils.Rapport;

public class AccueilDetailsRapport extends Fragment implements View.OnClickListener{

    private DetailsRapport activity;
    private String project_id;
    private Rapport rapport;
    private LinearLayout print;

    public AccueilDetailsRapport() {
        DetailsRapport.selectedPage = 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accueil_details_rapport,null,true);
        view.findViewById(R.id.maintenances).setOnClickListener(this);
        view.findViewById(R.id.infos).setOnClickListener(this);
        view.findViewById(R.id.supprimer).setOnClickListener(this);
        view.findViewById(R.id.ecarts).setOnClickListener(this);
        view.findViewById(R.id.surveiller).setOnClickListener(this);
        view.findViewById(R.id.appareils).setOnClickListener(this);
        print = view.findViewById(R.id.print);
        print.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DetailsRapport.selectedPage = 1;
        activity = (DetailsRapport) getActivity();
        Bundle bundle = getArguments();
        project_id = bundle.getString("project_id");
        rapport = new RapportsManager(activity).getRapport(project_id);
        activity.setTitle(rapport.getNom());
        activity.setSubTitle("");

    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("project_id",project_id);
        Fragment fragment;
        switch (view.getId()){
            case R.id.infos:
                fragment = new TabInfosGenerales();
                fragment.setArguments(bundle);
                activity.setFragment(fragment);
                break;
            case R.id.maintenances:
                fragment = new ListeMaintenances();
                fragment.setArguments(bundle);
                activity.setFragment(fragment);
                break;
            case R.id.surveiller:
                bundle.putString("title", "Liste des points à surveiller");
                bundle.putString("type","surveiller");
                fragment = new SurveillerConformite();
                fragment.setArguments(bundle);
                activity.setFragment(fragment);
                break;
            case R.id.ecarts:
                bundle.putString("title", "Liste des écarts / non conformités");
                bundle.putString("type","conformite");
                fragment = new SurveillerConformite();
                fragment.setArguments(bundle);
                activity.setFragment(fragment);
                break;
            case R.id.appareils:
                bundle.putString("title", "Appareils de mesure");
                fragment = new AppareilsMesure();
                fragment.setArguments(bundle);
                activity.setFragment(fragment);
                break;
            case R.id.print:
                if (new OperationsManager(activity).getGammes(project_id).size()<=0){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle("Impression du Rapport");
                    dialog.setMessage("Vous n'avez ajouté aucune gamme de maintenance !!");
                    dialog.show();
                }
                else {
                    constructJson();
                }
                break;
            case R.id.supprimer:
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("Suppression du Rapport !!");
                dialog.setMessage("Voulez-vous Vraiment Supprimer ce rapport ? Attention !! Cette action est irrevrsible !!!");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new RapportsManager(activity).deleteRapport(rapport.getRapport_id());
                        activity.onBackPressed();
                    }
                });
                dialog.show();
                break;
        }
    }

    private void constructJson(){
        JSONObject postData = new JSONObject();

        try {
            postData.put("rapport_id", rapport.getRapport_id());
            postData.put("titre",rapport.getNom());
            postData.put("client",rapport.getClient_id());
            postData.put("type",rapport.getType());
            postData.put("date_debut",rapport.getDate_debut());
            postData.put("date_fin",rapport.getDate_fin());
            postData.put("redacteur",rapport.getRedacteur());
            postData.put("verificateur",rapport.getVerificateur());
            postData.put("approbateur",rapport.getApprobateur());
            postData.put("contact_site",rapport.getContact_sur_site());
            postData.put("charge_affaires",rapport.getCharge_affaires());
            postData.put("charge_travaux",rapport.getCharge_travaux());
            postData.put("techniciens",rapport.getTechniciens());


            List<Map<String,String>> elements = new SurveillerConformiteManager(
                    activity).getFromProject(rapport.getRapport_id());
            JSONArray surv = new JSONArray();
            JSONArray conf = new JSONArray();

            for (Map<String,String> el:elements){
                String txt =
                        el.get("element_nom") + ";" +
                        el.get("element_valeur") + ";" +
                        el.get("element_type") + ";";
                if (el.get("element_type").equals("surveiller"))
                    surv.put(txt);
                else
                    conf.put(txt);

            }

            postData.put("surveiller",surv);
            postData.put("conformite",conf);

            List<Map<String,String>> appareils = new AppareilsMesureManager(activity).getFromProject(rapport.getRapport_id());

            JSONArray appareils_json = new JSONArray();

            for (Map<String,String> el:appareils){
                String txt =
                        el.get("element_nom") + ";" +
                                el.get("element_serie") + ";" +
                                el.get("element_validite") + ";";
               appareils_json.put(txt);

            }
            postData.put("appareils",appareils_json);
            List<Maintenance> maintenances = new MaintenanceManager(activity).getAllMaintenances(project_id);
            JSONObject allMaintenances = new JSONObject();

            for (Maintenance maintenance:maintenances){

                JSONObject maintenance_json = new JSONObject();
                maintenance_json.put("nom",maintenance.getTitle());

                List<Photo> photos = new PhotoManager(activity).getFromGamme(maintenance.getMaintenance_id());

                JSONArray images = new JSONArray();

                for (Photo photo:photos){
                    images.put(photo.getNom().substring(0,photo.getNom().indexOf("?")));
                }

                List<Map<String,String>> compartiments = new
                        NativeCompartimentsManager(activity).getFromGamme(maintenance.getInitial_id());
                JSONObject allCompartiments = new JSONObject();
                for (Map<String,String> map:compartiments){
                    JSONObject compartiment_json = new JSONObject();
                    compartiment_json.put("nom",map.get("compartiment_nom"));

                    List<Operation> taches = new
                            OperationsManager(activity).getFromCompartiment(map.get("compartiment_id"),maintenance.getMaintenance_id(),rapport.getRapport_id());
                    JSONArray op_json = new JSONArray();
                    for (Operation op:taches){
                        if (op.isStatut() == 1)
                            op_json.put(op.getNom() + ";" + op.getObservations());
                    }
                    compartiment_json.put("taches",op_json);

                    allCompartiments.put(map.get("compartiment_id"),compartiment_json);
                }

                List<Map<String,String>> gamme_elts = new
                        ElementsManager(activity).getFromGamme(maintenance.getInitial_id(),rapport.getRapport_id());

                JSONArray allEltss = new JSONArray();
                for (Map<String,String> map:gamme_elts){
                    allEltss.put(
                            map.get("element_nom") + ";" +
                            map.get("element_valeur")
                    );
                }
                maintenance_json.put("photos",images);
                //maintenance_json.put("caracteristiques",allEltss);
                maintenance_json.put("compartiments",allCompartiments);
                maintenance_json.put("infos_gen", allEltss);
                allMaintenances.put(maintenance.getMaintenance_id(),maintenance_json);
            }

            postData.put("maintenances",allMaintenances);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new SendRapport().execute("http://afrimoov.com/etincel/rapport.php",
                postData.toString()
        );
    }

    private class SendRapport extends AsyncTask<String, Void, String> {

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

           // Log.e("JSON",strings[1]);

            try {

                connection = (HttpURLConnection) new URL(strings[0]).openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                dos.writeBytes("data=" + strings[1]);
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
            dialog.dismiss();
            Log.e("JSON",s);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
            startActivity(intent);

        }
    }

}
