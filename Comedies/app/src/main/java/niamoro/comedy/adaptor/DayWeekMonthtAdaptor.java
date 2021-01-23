package niamoro.comedy.adaptor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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

import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import java.util.List;

import niamoro.comedy.R;
import niamoro.comedy.db_manager.ComediensManager;
import niamoro.comedy.utilis.Comedien;
import niamoro.comedy.utilis.Utils;
import niamoro.comedy.utilis.Video;
import niamoro.comedy.utilis.WebImageView;

public class DayWeekMonthtAdaptor extends ArrayAdapter<Video> {

    private List<Video> videos;
    private Activity context;
    private boolean compactMode;


    public DayWeekMonthtAdaptor(@NonNull final Activity context, List<Video> videos) {
        super(context, R.layout.video_list_view, videos);
        this.context = context;
        this.videos = videos;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        compactMode = preferences.getBoolean(Utils.COMPACT_MODE,false);

    }

    private static class Holder{
        ImageView image;
        ImageView share;
        WebImageView comedien_image;
        TextView titre;
        TextView comedien;
        TextView duree;
        TextView vues;
        AdView mAdview;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final Video video = videos.get(position);
        Comedien comedien = new ComediensManager(context).getComedien(video.getComedien());
        final Holder holder;
        if (view == null){
            holder = new Holder();
            view = context.getLayoutInflater().inflate(R.layout.day_week_month_view,null,true);
            Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/opensans_regular.ttf");

            holder.image = view.findViewById(R.id.video_image);
            holder.share = view.findViewById(R.id.share);
            holder.titre = view.findViewById(R.id.titre);
            holder.comedien = view.findViewById(R.id.comedien);
            holder.duree = view.findViewById(R.id.duration);
            holder.vues = view.findViewById(R.id.vues);

            holder.comedien_image = view.findViewById(R.id.comedien_image);

            holder.duree.setTypeface(tf);
            holder.vues.setTypeface(tf);
            holder.titre.setTypeface(tf);
            //holder.comedien.setTypeface(tf);

            view.setTag(holder);

        }
        else{
            holder = (Holder)view.getTag();
        }

        if (!compactMode && new Utils(context).isNetworkReachable())
            Picasso.get().load(video.getImage()).into(holder.image);
        else
            holder.image.setVisibility(View.GONE);
        if (comedien != null) {
            holder.comedien.setText(comedien.getNom());
            Bitmap bitmap = Utils.loadImageBitmap(context, comedien.getImage());
            if (bitmap != null)
                holder.comedien_image.setImageBitmap(bitmap);
            else
                holder.comedien_image.setImageUrl2("https://afrimoov.com/comediens/images/" + comedien.getImage());
        } else {
            holder.comedien.setText(video.getComedien());
            holder.comedien_image.setVisibility(View.GONE);
        }

        String title = video.getTitre();
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        holder.titre.setText(Html.fromHtml(title));
        holder.duree.setText(video.getDuration());
        holder.vues.setText(video.getVues() + " vues");

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = context.getString(R.string.share_message);
                String link = video.getLink();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);

                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + link +
                        "\n\n\n" + message + " ==> " +
                        "https://play.google.com/store/apps/details?id=" + context.getPackageName());
                context.startActivity(sendIntent);
            }
        });

        return view;
    }
}