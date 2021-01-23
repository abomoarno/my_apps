package arnoarobase.cuisineafricaine.adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.classes.Photo;

public class PhotoAdapter extends ArrayAdapter<Photo> {

    private Activity context;
    private List<Photo> photos;
    public PhotoAdapter(@NonNull Activity context, List<Photo> photos) {
        super(context, R.layout.recette_view, photos);
        this.context = context;
        this.photos = photos;
    }

    private static class Holder{
        ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Photo photo = photos.get(position);
        View view = convertView;
        final Holder holder;

        if (view == null){
            view = context.getLayoutInflater().inflate(R.layout.photo_grid_view, null, false);
            holder = new Holder();
            holder.image = view.findViewById(R.id.recette_image);
            view.setTag(holder);
        }
        else
            holder = (Holder)view.getTag();

        StorageReference reference = FirebaseStorage.getInstance().getReference("photos/" + photo.getRecette_id());
        reference.child(photo.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.image);
            }
        });
        return view;
    }
}