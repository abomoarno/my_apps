package niamoro.annonces.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import niamoro.annonces.R;
import niamoro.annonces.databases.managers.AnnonceManager;
import niamoro.annonces.databases.managers.PaysManager;
import niamoro.annonces.utils.Annonce;
import niamoro.annonces.utils.Pays;
import niamoro.annonces.utils.Utils;

public class Chargement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);
        setFragment(new niamoro.annonces.pages.pages_chargement.Chargement());
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this,getString(R.string.ads_id));
        signIn();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!Utils.PRIVACY) {
            startActivity(new Intent(this, Privacy.class));
            finish();
        }
        else if (!Utils.PRESENTATION) {
            startActivity(new Intent(this, Presentation.class));
            finish();
        }

        else if ((Utils.PAYS != null) && !(Utils.PAYS.isEmpty())){
            getAnnonces(Utils.PAYS);
        }
        else
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            preferences.edit().putString("pays","cm").apply();
            Utils.PAYS = "cm";
            getAnnonces(Utils.PAYS);
        }
    }

    private void next(){
        String id = getIntent().getStringExtra("id");
        Intent accueil = new Intent(this, Accueil.class);
        if (id != null)
            accueil.putExtra("id",id);
        startActivity(accueil);
        finish();
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,fragment).commit();
    }

    public void getAnnonces(final String pays){
        new Thread(new Runnable() {
            @Override
            public void run() {
                getPays();
            }
        }).start();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        final AnnonceManager manager = new AnnonceManager(this);
        final List<Annonce> toAdd = new ArrayList<>();
        final List<Annonce> toUpdate = new ArrayList<>();
        final List<String> ids = manager.getAllIds(pays);
        firestore.collection(pays)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Annonce annonce = document.toObject(Annonce.class);

                        if (ids.contains(annonce.getId())){
                            toUpdate.add(annonce);
                            ids.remove(annonce.getId());
                        }
                        else
                            toAdd.add(annonce);
                    }

                    manager.insertAnnonces(toAdd);
                    manager.updateAnnonces(toUpdate);
                    manager.deleteAnnonces(ids,pays);

                }
                else{
                    Log.e("Afrimoov","Erreur",task.getException());
                }

                next();
            }
        });
    }

    private void getPays(){
        final PaysManager manager = new PaysManager(this);
        final List<String> ids = manager.getAllIds();
        final List<Pays> toAdd = new ArrayList<>();
        final List<Pays> toUpdate = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pays");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,Pays>> t = new GenericTypeIndicator<HashMap<String, Pays>>() {};
                HashMap<String,Pays> pays = dataSnapshot.getValue(t);
                if (pays != null) {
                    for (String code : pays.keySet()) {
                        Pays country = pays.get(code);
                        if (ids.contains(code)){
                            toUpdate.add(country);
                            ids.remove(code);
                        }
                        else
                            toAdd.add(country);
                    }

                    manager.insertPays(toAdd);
                    manager.updatePays(toUpdate);
                    manager.deletePays(ids);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Utils.PAYS = preferences.getString("pays","");
        Utils.COMPACT_MODE = preferences.getBoolean("compact",false);
        Utils.NOTIFICATIONS = preferences.getBoolean("notifications",true);
        Utils.PRESENTATION = preferences.getBoolean("presentaion",false);
        Utils.PRIVACY = preferences.getBoolean("privacy",false);
    }

    private void signIn(){
        FirebaseAuth.getInstance().signInAnonymously();
    }
}