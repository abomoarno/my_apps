package arnoarobase.cuisineafricaine.pages.pages_photos;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.activities.Photos;
import arnoarobase.cuisineafricaine.adapters.PhotoAdapter;
import arnoarobase.cuisineafricaine.classes.Photo;

/**
 *
 */
public class ListePhotos extends Fragment implements AdapterView.OnItemClickListener{

    private List<Photo> photos;
    private PhotoAdapter adapter;
    private GridView gridView;
    private PhotoManager manager;
    private String recette_id;

    private Photos activity;

    public ListePhotos() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (Photos)getActivity();
        manager = new PhotoManager(activity);
        recette_id = getArguments().getString("recette");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.liste_photos, container, false);
        gridView = view.findViewById(R.id.gridView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("photos");
        reference.orderByChild("recette").equalTo(recette_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                photos = new ArrayList<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue(String.class);
                    String titre = snapshot.child("titre").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String url = snapshot.child("url").getValue(String.class);
                    String user = snapshot.child("user").getValue(String.class);

                    Photo photo = new Photo(id,titre);
                    photo.setDate(date);
                    photo.setUser(user);
                    photo.setImage(url);
                    photo.setRecette(recette_id);
                    photos.add(photo);
                    adapter = new PhotoAdapter(activity,photos);
                    gridView.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        new PhotoDialog(activity,i).show();
    }

    private class PhotoDialog extends Dialog implements View.OnClickListener{

       ImageView image;
       private int position;

       PhotoDialog(Context context, int position){
           this(context);
           this.position = position;
       }
        PhotoDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.photo_dialog);
            setCancelable(true);
            image = findViewById(R.id.image);
            findViewById(R.id.next).setOnClickListener(this);
            findViewById(R.id.prev).setOnClickListener(this);
        }

        @Override
        protected void onStart() {
            super.onStart();
            loadPhoto(position);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.next:
                    if (position < photos.size()-1) {
                        position = position+1;
                        loadPhoto(position);
                    }
                    break;
                case R.id.prev:
                    if (position > 0) {
                        position = position-1;
                        loadPhoto(position);
                    }
                    break;
            }
            dismiss();
        }

        private void loadPhoto(int index){
           Photo photo = photos.get(index);
            StorageReference reference = FirebaseStorage.getInstance().getReference("photos/" + photo.getRecette_id());
            reference.child(photo.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(image);
                }
            });
        }
    }
}