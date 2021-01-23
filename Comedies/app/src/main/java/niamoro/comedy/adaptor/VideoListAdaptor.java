package niamoro.comedy.adaptor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.utilis.Video;
import niamoro.comedy.utilis.Utils;

public class VideoListAdaptor extends ArrayAdapter<Video> {

    private List<Video> videos;
    private Activity context;
    private boolean compactMode;

    public VideoListAdaptor(@NonNull final Activity context, List<Video> videos) {
        super(context, R.layout.video_list_view, videos);
        this.context = context;
        this.videos = videos;

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
        Video video = videos.get(position);

        Holder holder;
        if (view == null){

            holder = new Holder();
            view = context.getLayoutInflater().inflate(R.layout.video_list_view,null,true);
            Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");

            holder.image = view.findViewById(R.id.tendances_image);
            holder.titre = view.findViewById(R.id.titre);
            holder.source = view.findViewById(R.id.comedien);
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
            Picasso.get().load(video.getImage()).into(holder.image);
        else
            holder.image.setVisibility(View.GONE);
        String title = video.getTitre();
        title = title.substring(0,1).toUpperCase() + title.substring(1);
        holder.titre.setText(Html.fromHtml(title));
        holder.source.setText(video.getComedien());
        String vues = (video.getVues() == 0) ? context.getString(R.string.nouveau) : video.getVues()+" vues";
        String duree_vues = vues + " | " + video.getDuration();

        holder.duree_vues.setText(duree_vues);
        return view;
    }
}