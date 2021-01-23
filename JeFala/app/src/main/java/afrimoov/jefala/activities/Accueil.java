package afrimoov.jefala.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import afrimoov.jefala.R;
import afrimoov.jefala.classes.Annonce;
import afrimoov.jefala.classes.Annonce_Adaptor;
import afrimoov.jefala.classes.Maison;

public class Accueil extends AppCompatActivity {

    private ListView listView;
    private Annonce_Adaptor adaptor;
    List<Annonce> annonces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultats);

        listView = findViewById(R.id.list_view);
        annonces = new ArrayList<>();

        annonces.add(new Maison(1,null));
        annonces.add(new Maison(2,null));
        annonces.add(new Maison(3,null));
        annonces.add(new Maison(4,null));
        annonces.add(new Maison(5,null));
        annonces.add(new Maison(6,null));

        adaptor = new Annonce_Adaptor(this,annonces);
        listView.setAdapter(adaptor);

    }
}
