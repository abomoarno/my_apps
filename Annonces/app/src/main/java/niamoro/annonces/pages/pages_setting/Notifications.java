package niamoro.annonces.pages.pages_setting;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.adapters.NotificationAdapter;
import niamoro.annonces.databases.managers.RechercheManager;
import niamoro.annonces.utils.Recherche;
import niamoro.annonces.utils.Utils;

public class Notifications extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private List<Recherche> recherches;
    private niamoro.annonces.activities.Notifications context;
    private NotificationAdapter adapter;
    private ListView listView;
    private LinearLayout empty;

    private Switch globalNotifs;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = (niamoro.annonces.activities.Notifications) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notifications,null,false);
        listView = view.findViewById(R.id.listView);
        globalNotifs = view.findViewById(R.id.notifs_cheked);
        empty = view.findViewById(R.id.empty);
        listView.setEmptyView(empty);
        globalNotifs.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        recherches = new RechercheManager(context).getActives();
        adapter = new NotificationAdapter(context,recherches,this);
        listView.setAdapter(adapter);
        setSwitch(Utils.NOTIFICATIONS);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

        if (!b){

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false)
                    .setTitle("ATTENTION !!")
                    .setMessage("Vous êtes sur le point de désactiver toutes vos alertes.\n\nVous avez plus de chances de trouver ce que " +
                            "vous cherchez en recevant des alertes.\n\nSi vous réactivez les notifications plus tard, il faudra " +
                            "réactiver vos alertes manuellement une par une !!")
                    .setPositiveButton("Désactiver", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            for(Recherche recherche:recherches){
                                recherche.setStatus(0);
                            }
                            new RechercheManager(context).updateStatus(recherches);
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                            preferences.edit().putBoolean("notifications",false).apply();
                            Utils.NOTIFICATIONS = false;
                            adapter.notifyDataSetChanged();

                            Toast.makeText(context,"Notifications désactivées ...",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            globalNotifs.setChecked(true);
                        }
                    })
                    .show();
        }
        else {
            if (!Utils.NOTIFICATIONS)
                Toast.makeText(context,"Notifications activées ...",Toast.LENGTH_SHORT).show();
            for(Recherche recherche:recherches){
                recherche.setStatus(1);
            }
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            preferences.edit().putBoolean("notifications",true).apply();
            Utils.NOTIFICATIONS = true;
        }
    }

    public void setSwitch(boolean b) {
        globalNotifs.setChecked(b);
    }
}
