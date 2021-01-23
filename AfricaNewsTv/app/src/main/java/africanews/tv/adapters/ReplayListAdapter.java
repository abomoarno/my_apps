package africanews.tv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import africanews.tv.R;
import africanews.tv.entities.Replay;


public class ReplayListAdapter extends ArrayAdapter<Replay> {

    private Context mContext;
    private List<Replay> replays;

    public ReplayListAdapter(Context context, List<Replay> replays){
        super(context, R.layout.replay_list_view, replays);

        mContext = context;
        this.replays = replays;
    }

    @Override
    public int getCount() {
        return replays.size();
    }

    class Holder{
        ImageView illustration;
        ImageView share;
        TextView titre;
        TextView duration;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final Holder holder;

        final Replay replay = replays.get(position);

        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.replay_list_view, null, false);

            holder = new Holder();

            holder.illustration = view.findViewById(R.id.illustration);
            holder.titre = view.findViewById(R.id.title);
            holder.duration = view.findViewById(R.id.duration);
            holder.share = view.findViewById(R.id.share);

            view.setTag(holder);

        }

        else {
            holder = (Holder) view.getTag();
        }

        holder.titre.setText(replay.getTitle());
        holder.duration.setText(replay.getDuration());

        Picasso.get().load(replay.getIllustration()).into(holder.illustration);


        return view;
    }
}
