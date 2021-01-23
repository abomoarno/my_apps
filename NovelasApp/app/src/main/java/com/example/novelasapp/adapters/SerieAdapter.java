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
import com.example.novelasapp.entities.Serie;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SerieAdapter extends ArrayAdapter<Serie> {

    private List<Serie> series;
    private Context context;

    public SerieAdapter(Context context, List<Serie> series){
        super(context, R.layout.serie_item_view, series);

        this.context  = context;
        this.series = series;
    }

    class SerieHolder{
        TextView titre;
        ImageView illustration;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final SerieHolder holder;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.serie_item_view, null, false);

            holder = new SerieHolder();

            holder.illustration = view.findViewById(R.id.illsutration);
            holder.titre = view.findViewById(R.id.titre);

            view.setTag(holder);
        }
        else {
            holder = (SerieHolder) view.getTag();
        }

        holder.titre.setText(series.get(position).getTitle());

        FirebaseStorage.getInstance().getReference("series_illustrations")
                .child(series.get(position).getIllustration())
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
