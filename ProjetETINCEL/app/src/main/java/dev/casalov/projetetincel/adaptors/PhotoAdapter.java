package dev.casalov.projetetincel.adaptors;

import android.app.Activity;
import android.graphics.Bitmap;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.utils.Photo;
import dev.casalov.projetetincel.utils.Utils;

public class PhotoAdapter extends ArrayAdapter<Photo> {

    private List<Photo> operations;
    private Activity context;

    public PhotoAdapter(@NonNull Activity context, List<Photo> techniciens) {
        super(context, R.layout.photos_view,techniciens);
        this.context = context;
        this.operations = techniciens;
    }

    private static class Holder
    {
        ImageView photo;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        final Holder holder;

        if (rowView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.photos_view,null,true);
            holder = new Holder();
            holder.photo = rowView.findViewById(R.id.photo);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder)rowView.getTag();
        }

        Bitmap bitmap = Utils.loadImageBitmap(context,operations.get(position).getLien());
        holder.photo.setImageBitmap(bitmap);

        return rowView;
    }
}