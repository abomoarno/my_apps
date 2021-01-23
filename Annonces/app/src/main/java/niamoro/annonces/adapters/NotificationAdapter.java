package niamoro.annonces.adapters;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.activities.Notifications;
import niamoro.annonces.databases.managers.RechercheManager;
import niamoro.annonces.utils.Recherche;
import niamoro.annonces.utils.Utils;

public class NotificationAdapter extends ArrayAdapter<Recherche> {

    private niamoro.annonces.pages.pages_setting.Notifications fragment;
    private List<Recherche> recherches;
    private Notifications context;

    public NotificationAdapter(Notifications context, List<Recherche> objects, niamoro.annonces.pages.pages_setting.Notifications fragment) {
        super(context, R.layout.notification_view, objects);
        this.context = context;
        this.recherches = objects;
        this.fragment = fragment;
    }

    private static class Holder{
        TextView ville;
        Switch checked;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        final Recherche recherche = recherches.get(position);

        View view = convertView;
        final Holder holder;

        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.notification_view,null,false);
            holder = new Holder();
            holder.checked = view.findViewById(R.id.checked);
            holder.ville = view.findViewById(R.id.ville);
            view.setTag(holder);
        }
        else {
            holder = (Holder)view.getTag();
        }

        holder.ville.setText(recherche.getNotifMessage());
        if (recherche.getStatus() == 1){
            holder.checked.setChecked(true);
            holder.ville.setTextColor(context.getResources().getColor(R.color.myBlue));
        }
        else {
            holder.checked.setChecked(false);
            holder.ville.setTextColor(Color.BLACK);
        }

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                recherche.setStatus((b)? 1:0);
                new RechercheManager(context).updateStatus(recherche);

                if (b){
                    if (!Utils.NOTIFICATIONS){
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        preferences.edit().putBoolean("notifications",true).apply();
                        Utils.NOTIFICATIONS = true;
                        fragment.setSwitch(true);
                    }
                    else
                        Toast.makeText(context,"Alertes activées pour cette recherche",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context,"Alertes désactivées pour cette recherche ...",Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        };

        holder.checked.setOnCheckedChangeListener(listener);

        return view;
    }
}
