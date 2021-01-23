package arnoarobase.cuisineafricaine.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.classes.Recette;

public class RecetteRecyclerAdaptor extends RecyclerView.Adapter<RecetteRecyclerAdaptor.ViewHolder> {
    private DatabaseReference replayRef;
    private Context context;
    private List<Recette> recettes;
    private final OnItemClickListener listener;

    public RecetteRecyclerAdaptor(final Context context, List<Recette> recettes, OnItemClickListener listener) {
        this.context = context;
        this.recettes = recettes;
        this.listener = listener;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recette_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.bind(recettes.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return recettes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.recette_image);
            name = itemView.findViewById(R.id.recette_nom);
        }

        void bind(final Recette recette, final OnItemClickListener listener){

            name.setText(recette.getName());
            StorageReference reference = FirebaseStorage.getInstance().getReference("illustrations/" + recette.getIllustration());
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(image);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }

    public interface OnItemClickListener{

        void onItemClick(Recette recette);

    }
}
