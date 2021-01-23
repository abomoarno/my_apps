package arnoarobase.cuisineafricaine.pages.teasers;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.activities.Accueil;
import arnoarobase.cuisineafricaine.classes.Recette;
import arnoarobase.cuisineafricaine.db_mangment.RecettesManager;

public class Teaser2 extends Fragment implements View.OnClickListener{

    private String recette_id;
    private Recette recette;
    private Accueil activity;

    private ImageView image;
    private TextView titre;
    private TextView description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Accueil)getActivity();
        recette_id = getArguments().getString("recette");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.teaser_view,null,false);
        image = view.findViewById(R.id.recette_image);
        titre = view.findViewById(R.id.recette_nom);
        description = view.findViewById(R.id.recette_description);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        recette = new RecettesManager(activity).getRecette(recette_id);
        titre.setText(recette.getName());
        description.setText(recette.getDescription());
        StorageReference ref = FirebaseStorage.getInstance().getReference("illustrations/" + recette.getIllustration());
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(image);
            }
        });
        }

    @Override
    public void onClick(View view) {

    }
}
