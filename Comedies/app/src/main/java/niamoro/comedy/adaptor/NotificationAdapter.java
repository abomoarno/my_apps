package niamoro.comedy.adaptor;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import java.util.List;
import niamoro.comedy.R;
import niamoro.comedy.activities.Notifications;
import niamoro.comedy.db_manager.ComediensManager;
import niamoro.comedy.utilis.Comedien;
import niamoro.comedy.utilis.Utils;

public class NotificationAdapter extends ArrayAdapter<Comedien> {
    private List<Comedien> recherches;
    private Notifications context;
    public NotificationAdapter(Notifications context, List<Comedien> objects) {
        super(context, R.layout.notif_view, objects);
        this.context = context;
        this.recherches = objects;
    }
    private static class Holder{
        TextView ville;
        Switch checked;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Comedien comedien = recherches.get(position);
        View view = convertView;
        final Holder holder;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.notif_view,null,false);
            holder = new Holder();
            holder.checked = view.findViewById(R.id.checked);
            holder.ville = view.findViewById(R.id.ville);
            view.setTag(holder);
        }
        else {
            holder = (Holder)view.getTag();
        }

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_extrabold.ttf");
        holder.ville.setTypeface(tf);
        holder.ville.setText(comedien.getNom());
        if (comedien.isFollowed()){
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
                comedien.setFollowed(b);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                new ComediensManager(context).updateComedien(comedien.getComedien_id(),b);
                if (b && (!preferences.getBoolean(Utils.NOTIFICATIONS,true))){
                    context.setGlobalNotifications(true);
                }
                notifyDataSetChanged();
            }
        };
        holder.checked.setOnCheckedChangeListener(listener);
        return view;
    }
}