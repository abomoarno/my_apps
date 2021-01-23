package africanews.tv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import africanews.tv.R;
import africanews.tv.entities.TvChannel;


public class TvListAdapter extends ArrayAdapter<TvChannel> {

    private Context mContext;
    private List<TvChannel> tvs;

    public TvListAdapter(Context context, List<TvChannel> tvChannels){
        super(context, R.layout.tv_list_view, tvChannels);

        mContext = context;
        this.tvs = tvChannels;
    }

    @Override
    public int getCount() {
        return tvs.size();
    }

    class Holder{
        ImageView illustration;
        TextView titre;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final Holder holder;

        final TvChannel tv = tvs.get(position);

        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.tv_list_view, null, false);

            holder = new Holder();

            holder.illustration = view.findViewById(R.id.illustration);
            holder.titre = view.findViewById(R.id.title);

            view.setTag(holder);

        }

        else {
            holder = (Holder) view.getTag();
        }

        holder.titre.setText(tv.getName());

        Picasso.get().load(tv.getIllustration()).into(holder.illustration);


        return view;
    }
}
