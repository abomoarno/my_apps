package arnoarobase.coiffuresafricaines.pages;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import arnoarobase.coiffuresafricaines.R;
import arnoarobase.coiffuresafricaines.activities.PhotoView;
import arnoarobase.coiffuresafricaines.adaptors.CommentaireAdater;
import arnoarobase.coiffuresafricaines.calsses.Commentaire;
import arnoarobase.coiffuresafricaines.calsses.OnSwipeTouchListener;
import arnoarobase.coiffuresafricaines.calsses.Photo;
import arnoarobase.coiffuresafricaines.db_mangers.PhotosManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoViewer extends Fragment implements View.OnClickListener{

    private PhotoView activity;
    //private LinearLayout commentaires;
    private LinearLayout itemLayout;
    //private LinearLayout commentsLayout;
    //private LinearLayout newCommentLayout;
    private LinearLayout circularLayout;
    //private ListView listView;
    private ImageView photo;
    //private ImageView photo_min;
    private ImageView liked;
    private Photo selectedPhoto;

    private TextView titre;
    private TextView description;

    public static boolean commentsShow;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commentsShow = false;
        activity = (PhotoView)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.photo_viwer, container, false);
        //commentaires = view.findViewById(R.id.commentaires);
        //commentsLayout = view.findViewById(R.id.commentLayout);
        //newCommentLayout = view.findViewById(R.id.layout_new_comment);
        circularLayout = view.findViewById(R.id.circularbar);
        itemLayout = view.findViewById(R.id.item);

        titre = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        liked = view.findViewById(R.id.like);

        photo = view.findViewById(R.id.photo);
        //photo_min = view.findViewById(R.id.photo_mini);

        Bundle bundle = getArguments();

        selectedPhoto = new PhotosManager(activity).getPhoto(bundle.getString("photo_id"));

        liked.setOnClickListener(this);

        setLiked();

        titre.setText(selectedPhoto.getTitre());
        description.setText(selectedPhoto.getDescription());

        StorageReference reference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = reference.child("images");

        imageRef.child(selectedPhoto.getUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(photo);
                Picasso.get().load(uri).into(photo, new Callback() {
                    @Override
                    public void onSuccess() {
                        circularLayout.setVisibility(View.GONE);
                        photo.setVisibility(View.VISIBLE);
                        //photo_min.setImageDrawable(photo.getDrawable());
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });


        itemLayout.setOnTouchListener(new OnSwipeTouchListener(activity){
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                activity.setFragment(activity.getPrevId());
            }
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                activity.setFragment(activity.getNextId());
            }
        });

        return view;
    }

 private void setLiked(){

     if (selectedPhoto.getLiked() == 1) {
         liked.setImageDrawable(activity.getResources().getDrawable(R.drawable.like));
     }
     else {
         liked.setImageDrawable(activity.getResources().getDrawable(R.drawable.heart));
     }
 }

    private void updateLiked(){
        final DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("images/" + selectedPhoto.getId());

        if (selectedPhoto.getLiked() == 1) {
            liked.setImageDrawable(activity.getResources().getDrawable(R.drawable.heart));
            selectedPhoto.setLiked(0);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int notes = dataSnapshot.child("likes").getValue(Integer.class);
                    reference.child("likes").setValue(notes-1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });

        }
        else {
            liked.setImageDrawable(activity.getResources().getDrawable(R.drawable.like));
            selectedPhoto.setLiked(1);

            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int notes = dataSnapshot.child("likes").getValue(Integer.class);
                    reference.child("likes").setValue(notes+1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        new PhotosManager(activity).updateLiked(selectedPhoto);
    }

    @Override
    public void onClick(View view) {
        updateLiked();
    }
}
