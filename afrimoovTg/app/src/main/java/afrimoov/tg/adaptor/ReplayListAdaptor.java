package afrimoov.tg.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import afrimoov.tg.R;
import afrimoov.tg.db_manager.ReplaysManager;
import afrimoov.tg.utilis.Replay;
import afrimoov.tg.utilis.Utils;

public class ReplayListAdaptor extends ArrayAdapter {

    private List<Replay> replays;
    private Activity context;
    private boolean compactMode;


    public ReplayListAdaptor(@NonNull final Activity context, List<Replay> replays) {
        super(context, R.layout.replay_list_view,replays);
        this.context = context;
        this.replays = replays;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        compactMode = preferences.getBoolean(Utils.COMPACT_MODE,false);

    }

    private static class Holder{
        ImageView image;
        TextView titre;
        TextView source;
        TextView duree_vues;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Replay replay = replays.get(position);

        Holder holder;
        if (view == null){

            holder = new Holder();
            view = context.getLayoutInflater().inflate(R.layout.replay_list_view,null,true);
            Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");

            holder.image = view.findViewById(R.id.replay_image);
            holder.titre = view.findViewById(R.id.titre);
            holder.source = view.findViewById(R.id.source);
            holder.duree_vues = view.findViewById(R.id.duree_vues);

            holder.duree_vues.setTypeface(tf);
            holder.titre.setTypeface(tf);
            holder.source.setTypeface(tf);

            view.setTag(holder);

        }
        else{

            holder = (Holder)view.getTag();

        }
        if (!compactMode && new Utils(context).isNetworkReachable())
            Picasso.get().load(replay.getImage()).into(holder.image);
        else
            holder.image.setVisibility(View.GONE);
        String title = replay.getTitle();
        title = title.substring(0,1).toUpperCase() + title.substring(1);
        holder.titre.setText(title);
        holder.source.setText(replay.getChaine());
        String vues = (replay.getViews() == 0) ? context.getString(R.string.nouveau) : replay.getViews()+" vues";
        String duree_vues = vues + " | " + replay.getDuree();

        holder.duree_vues.setText(duree_vues);
        return view;
    }
}
