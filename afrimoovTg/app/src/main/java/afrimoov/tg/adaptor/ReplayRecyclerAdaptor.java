package afrimoov.tg.adaptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import afrimoov.tg.utilis.Live_Tv;
import afrimoov.tg.utilis.Replay;
import afrimoov.tg.utilis.Utils;

public class ReplayRecyclerAdaptor extends RecyclerView.Adapter<ReplayRecyclerAdaptor.ViewHolder> {
    private DatabaseReference replayRef;
    private List<Replay> replays;
    private Context context;
    private final OnItemClickListener listener;

    private boolean compactMode;

    public ReplayRecyclerAdaptor(final Context context, List<Replay> replays, OnItemClickListener listener) {
        this.context = context;
        this.replays = replays;
        this.listener = listener;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        compactMode = preferences.getBoolean(Utils.COMPACT_MODE,false);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.replay_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(replays.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return replays.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        TextView source;
        TextView duree;
        TextView vues;
        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.replay_image);
            name = itemView.findViewById(R.id.titre);
            source = itemView.findViewById(R.id.source);
            duree = itemView.findViewById(R.id.duree);
            vues = itemView.findViewById(R.id.vues);
        }

        void bind(final Replay replay, final OnItemClickListener listener){

            if (!compactMode && new Utils(context).isNetworkReachable())
                Picasso.get().load(replay.getImage()).into(image);
            else
                image.setVisibility(View.GONE);
            String title = replay.getTitle();
            title = title.substring(0,1).toUpperCase() + title.substring(1);
            name.setText(title);
            source.setText(replay.getChaine());
            duree.setText(replay.getDuree());
            String views;
            if (replay.getViews() != 0) {
                views = context.getString(R.string.views);
                views = replay.getViews() + " " + views;
            }
            else{
                views = context.getString(R.string.nouveau);
            }
            vues.setText(views);

            Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");
            name.setTypeface(tf);
            vues.setTypeface(tf);
            duree.setTypeface(tf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(replay);
                }
            });

        }
    }

    public interface OnItemClickListener{

        void onItemClick(Replay replay);

    }

}
