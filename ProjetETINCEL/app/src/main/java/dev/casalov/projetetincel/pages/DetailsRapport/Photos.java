package dev.casalov.projetetincel.pages.DetailsRapport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dev.casalov.projetetincel.R;

import dev.casalov.projetetincel.activities.DetailsRapport;
import dev.casalov.projetetincel.adaptors.CompartimentAdapter;
import dev.casalov.projetetincel.adaptors.PhotoAdapter;
import dev.casalov.projetetincel.db_mangment.OperationsManager;
import dev.casalov.projetetincel.db_mangment.PhotoManager;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;
import dev.casalov.projetetincel.utils.Photo;
import dev.casalov.projetetincel.utils.Utils;

import static android.app.Activity.RESULT_OK;

public class Photos extends Fragment implements View.OnClickListener{

    private List<Photo> photos;
    private GridView gridView;
    private DetailsRapport activity;
    private PhotoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photos, null, false);
        gridView = view.findViewById(R.id.gridview);
        view.findViewById(R.id.ajouter).setOnClickListener(this);
        activity = (DetailsRapport) getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        activity.setSubTitle("Liste des Compartiments");
        DetailsRapport.selectedPage = 4;
        Bundle bundle = getArguments();
        String gamme = bundle.getString("maintenance_id");
        photos = new PhotoManager(activity).getFromGamme(gamme);
        adapter = new PhotoAdapter(activity,photos);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,1292);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1292 && resultCode == RESULT_OK){
            final ProgressDialog dialog = new ProgressDialog(activity);
            dialog.setMessage("Transfert de l'image en cours \nVeuillez patienter...");
            dialog.setTitle("Transfert de l'image");
            dialog.setCancelable(false);
            if (data != null){
                dialog.show();
                try {
                    final Uri myUri = data.getData();
                    InputStream inputStream = activity.getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    String nom = new Date().getTime()+"";
                    Utils.saveImage(activity,bitmap,nom);
                    final Photo photo = new Photo(getArguments().getString("maintenance_id"),nom);
                    photo.setStatus("0");
                    StorageReference reference = FirebaseStorage.getInstance().getReference("photos");
                    final StorageReference photoref = reference
                            .child(getArguments().getString("project_id"))
                            .child(photo.getLien()+".png");
                    photoref.putFile(myUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            photo.setStatus("1");
                            new PhotoManager(activity).updateDevis(photo);
                            photoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    photo.setNom(uri.toString());
                                    new PhotoManager(activity).insertPhoto(photo);
                                    photos.add(photo);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(activity,"Assurez-vous que vous êtes connecté à internet",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog.dismiss();
                            Toast.makeText(activity,"Assurez-vous que vous êtes connecté à internet",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }
    }
}