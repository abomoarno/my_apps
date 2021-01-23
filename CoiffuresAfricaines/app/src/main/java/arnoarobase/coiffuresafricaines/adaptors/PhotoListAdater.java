package arnoarobase.coiffuresafricaines.adaptors;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.calsses.Photo;

public class PhotoListAdater extends ArrayAdapter<Photo> {

    private Activity context;
    private List<Photo> photos;

    public PhotoListAdater(@NonNull Activity context, List<Photo> photos) {
        super(context, R.layout.photo_grid_view, photos);

        this.context = context;
        this.photos = photos;
    }

    private class Holder{
        ImageView image;
        TextView titre;
        //AppCompatRatingBar note;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        final Holder holder;
        Photo photo = photos.get(position);

        if (view == null){
            holder = new Holder();
            view = context.getLayoutInflater().inflate(R.layout.photo_grid_view, null, false);

            holder.image = view.findViewById(R.id.photo);
            holder.titre = view.findViewById(R.id.titre);
            //holder.note = view.findViewById(R.id.note);

            view.setTag(holder);
        }
        else {
            holder = (Holder)view.getTag();
        }

        holder.titre.setText(photo.getTitre());
        //holder.note.setRating(photo.getNote());

        StorageReference reference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = reference.child("images");

        imageRef.child(photo.getUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.image);
            }
        });

        return view;
    }
}
