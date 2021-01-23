package dev.casalov.projetetincel.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dev.casalov.projetetincel.R;
import dev.casalov.projetetincel.native_db_managment.NativeCompartimentsManager;
import dev.casalov.projetetincel.native_db_managment.NativeElementsManager;
import dev.casalov.projetetincel.native_db_managment.NativeGammesManager;
import dev.casalov.projetetincel.native_db_managment.NativeOperationsManager;

public class Chargement extends AppCompatActivity {

    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargement);

        new Thread(new Runnable() {
            @Override
            public void run() {
                updateGamme();
                updateCompartiments();
                updateTaches();
                updateElements();
                SystemClock.sleep(6000);
                startActivity(new Intent(Chargement.this, Accueil.class));
                finish();
            }
        }).start();
    }

    private void updateGamme(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("gammes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                NativeGammesManager manager = new NativeGammesManager(Chargement.this);

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue(String.class);
                    String titre = snapshot.child("titre").getValue(String.class);
                    manager.inserGamme(id,titre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateCompartiments(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("compartiments");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                NativeCompartimentsManager manager = new NativeCompartimentsManager(Chargement.this);
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue(String.class);
                    String gamme_id = snapshot.child("gamme_id").getValue(String.class);
                    String titre = snapshot.child("titre").getValue(String.class);
                    Map<String,String> compartiment = new HashMap<>();
                    compartiment.put("compartiment_id",id);
                    compartiment.put("compartiment_nom",titre);
                    compartiment.put("gamme_id",gamme_id);
                    manager.insertCompartiment(compartiment);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void updateTaches(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("taches");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                NativeOperationsManager manager = new NativeOperationsManager(Chargement.this);
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue(String.class);
                    String compartiment_id = snapshot.child("compartiment_id").getValue(String.class);
                    String titre = snapshot.child("titre").getValue(String.class);

                    Map<String,String> operation = new HashMap<>();
                    operation.put("compartiment_id",compartiment_id);
                    operation.put("operation_nom",titre);
                    operation.put("operation_id",id);
                    manager.insertOperation(operation);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void updateElements(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("elements");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                NativeElementsManager manager = new NativeElementsManager(Chargement.this);
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String id = snapshot.child("id").getValue(String.class);
                    String gamme_id = snapshot.child("gamme_id").getValue(String.class);
                    String titre = snapshot.child("titre").getValue(String.class);

                    Map<String,String> element = new HashMap<>();
                    element.put("gamme_id",gamme_id);
                    element.put("element_nom",titre);
                    element.put("element_id",id);
                    manager.insertElement(element);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
