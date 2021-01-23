package afrimoov.ci.adaptor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import afrimoov.ci.R;
import afrimoov.ci.utilis.Replay;
import afrimoov.ci.utilis.Utils;

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
            holder.duree = view.findViewById(R.id.duration);
            holder.vues = view.findViewById(R.id.live_vues);
            view.setTag(holder);
        }
        else
            holder = (ViewHolder)view.getTag();
        if (!compactMode && new Utils(context).isNetworkReachable())
            Picasso.get().load(replay.getImage()).into(holder.image);
        else
            holder.image.setVisibility(View.GONE);

        String title = replay.getTitre();
        title = title.substring(0,1).toUpperCase() + title.substring(1);
        holder.name.setText(title);
        holder.duree.setText(replay.getDuration());
        String views = context.getString(R.string.vues);
        views = replay.getVues() + " " + views;
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
