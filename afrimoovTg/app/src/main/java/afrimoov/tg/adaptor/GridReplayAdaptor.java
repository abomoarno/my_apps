package afrimoov.tg.adaptor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

public class GridReplayAdaptor extends BaseAdapter {

    private List<Replay> replays;
    private Activity context;
    private boolean compactMode;

    public GridReplayAdaptor(List<Replay> replays, final Activity context) {
        this.replays = replays;
        this.context = context;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        compactMode = preferences.getBoolean(Utils.COMPACT_MODE,false);
    }

    @Override
    public int getCount() {
        return replays.size();
    }

    @Override
    public Replay getItem(int position) {
        return replays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Replay replay = replays.get(position);
        ViewHolder holder;
        View view = convertView;

        if (view == null){
            holder = new ViewHolder();
            view = context.getLayoutInflater().inflate(R.layout.replay_grid_view,null,true);
            holder.image = view.findViewById(R.id.replay_image);
            holder.name = view.findViewById(R.id.titre);
            holder.duree = view.findViewById(R.id.duree);
            holder.vues = view.findViewById(R.id.vues);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder)view.getTag();
        if (!compactMode && new Utils(context).isNetworkReachable())
            Picasso.get().load(replay.getImage()).into(holder.image);
        else
            holder.image.setVisibility(View.GONE);

        String title = replay.getTitle();
        title = title.substring(0,1).toUpperCase() + title.substring(1);
        holder.name.setText(title);
        holder.duree.setText(replay.getDuree());
        String views = context.getString(R.string.views);
        views = replay.getViews() + " " + views;
        holder.vues.setText(views);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
        holder.name.setTypeface(tf);
        holder.vues.setTypeface(tf);
        holder.duree.setTypeface(tf);



        return view;
    }

    private static class ViewHolder{
        ImageView image;
        TextView name;
        TextView duree;
        TextView vues;
    }
}
