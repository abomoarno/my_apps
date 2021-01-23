package arnoarobase.cuisineafricaine.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import arnoarobase.cuisineafricaine.R;
import arnoarobase.cuisineafricaine.classes.Recette;
import arnoarobase.cuisineafricaine.db_mangment.RecettesManager;

public class Chargement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);
        getRecettes();
    }

    private void getRecettes(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference recetesRef = reference.child("recettes");
        final RecettesManager manager = new RecettesManager(this);
        final List<String> ids = manager.getAllIds();

        recetesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Recette recette;
                List<Recette> toUpdate = new ArrayList<>();
                List<Recette> toAdd = new ArrayList<>();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    try {
                        String id = snapshot.getKey();
                        String titre = snapshot.child("name").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);
                        String image = snapshot.child("illustration").getValue(String.class);
                        String video = snapshot.child("video").getValue(String.class);
                        String temps = snapshot.child("cuisson").getValue(String.class);
                        String prix = snapshot.child("prix").getValue(String.class);
                        String difficulte = snapshot.child("difficulte").getValue(String.class);
                        String categories = snapshot.child("categories").getValue(String.class);
                        long date = snapshot.child("date").getValue(Long.class);
                        String pays = snapshot.child("pays").getValue(String.class);
                        String ingredients = snapshot.child("ingrediens").getValue(String.class);
                        String preparation = snapshot.child("etapes").getValue(String.class);
                        int note = snapshot.child("note").getValue(Integer.class);
                        int personnes = snapshot.child("personnes").getValue(Integer.class);
                        int views = snapshot.child("vues").getValue(Integer.class);
                        int favories = snapshot.child("favories").getValue(Integer.class);
                        int add_list = snapshot.child("add_list").getValue(Integer.class);

                        recette = new Recette(id,titre);
                        recette.setAdd_list(add_list);
                        recette.setFavories(favories);
                        recette.setVues(views);
                        recette.setPays(pays);
                        recette.setEtapes(preparation);
                        recette.setIngrediens(ingredients);
                        recette.setDate(date+"");
                        recette.setCategories(categories);
                        recette.setDiificulte(difficulte);
                        recette.setPrix(prix);
                        recette.setNote(note);
                        recette.setPersonnes(personnes);
                        recette.setCuisson(temps);
                        recette.setVideo(video);
                        recette.setIllustration(image);
                        recette.setDescription(description);

                        if (ids.contains(id)){
                            ids.remove(id);
                            toUpdate.add(recette);
                        }
                        else
                        {
                            recette.setInMyList(false);
                            recette.setInFavourites(false);
                            toAdd.add(recette);
                        }

                        Log.e("Titre",titre);

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }

                manager.delete(ids);
                manager.updateRecettes(toUpdate);
                manager.inserRecettes(toAdd);

                next();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                next();
            }
        });
    }

    private void next(){
        startActivity(new Intent(this, Accueil.class));
        finish();
    }
}
