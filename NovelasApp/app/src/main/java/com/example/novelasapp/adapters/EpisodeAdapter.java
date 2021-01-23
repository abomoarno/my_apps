package com.example.novelasapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.novelasapp.R;
import com.example.novelasapp.entities.Episode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodeAdapter extends ArrayAdapter<Episode> {

    private List<Episode> episodes;
    private Context context;

    public EpisodeAdapter(Context context, List<Episode> episodes){
        super(context, R.layout.episode_item_view, episodes);

        this.context  = context;
        this.episodes = episodes;
    }

    class EpisodeHolder{
        TextView titre;
        TextView resume;
        ImageView illustration;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final EpisodeHolder holder;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.episode_item_view, null, false);

            holder = new EpisodeHolder();

            holder.illustration = view.findViewById(R.id.illustration);
            holder.titre = view.findViewById(R.id.title);
            holder.resume = view.findViewById(R.id.resume);

            view.setTag(holder);
        }
        else {
            holder = (EpisodeHolder) view.getTag();
        }

        holder.titre.setText(episodes.get(position).getTitle());
        holder.resume.setText(episodes.get(position).getResume());

        FirebaseStorage.getInstance().getReference("series_illustrations")
                .child(episodes.get(position).getIllustration())
                .getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Picasso.get().load(task.getResult()).into(holder.illustration);
                }
            }
        });

        return view;
    }
}
