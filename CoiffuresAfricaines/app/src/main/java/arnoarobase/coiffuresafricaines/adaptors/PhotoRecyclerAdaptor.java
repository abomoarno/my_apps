package arnoarobase.coiffuresafricaines.adaptors;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.calsses.Photo;


public class PhotoRecyclerAdaptor extends RecyclerView.Adapter<PhotoRecyclerAdaptor.ViewHolder> {
    private DatabaseReference replayRef;
    private List<Photo> photos;
    private Context context;
    private final OnItemClickListener listener;


    public PhotoRecyclerAdaptor(final Context context, List<Photo> photos, OnItemClickListener listener) {
        this.context = context;
        this.photos = photos;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_recylcer_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(photos.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView titre;
        //RatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.photo);
            titre = itemView.findViewById(R.id.titre);
            //ratingBar = itemView.findViewById(R.id.note);

        }

        void bind(final Photo photo, final OnItemClickListener listener){

            titre.setText(photo.getTitre());
            //ratingBar.setRating(photo.getNote());

            StorageReference reference = FirebaseStorage.getInstance().getReference();
            StorageReference imageRef = reference.child("images");

            imageRef.child(photo.getUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(image);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(photo);
                }
            });

        }
    }

    public interface OnItemClickListener{
        void onItemClick(Photo photo);
    }

}
