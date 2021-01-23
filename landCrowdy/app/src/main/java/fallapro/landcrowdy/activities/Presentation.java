package fallapro.landcrowdy.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.utils.Utils;

public class Presentation extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation);

        TextView title = findViewById(R.id.presentation_header);
        String presenation_header = getString(R.string.presentation_title);
        switch (Utils.PAYS_ISO)
        {
            case "cm":
            case "ga":
            case "sn":
                presenation_header += " " + getString(R.string.presentation_title_au);
                break;
            case "ci":
            case "rdc":
                presenation_header += " " + getString(R.string.presentation_title_en);
                break;
        }
        presenation_header += " " + Utils.PAYS_NAME;
        title.setText(presenation_header);
        Button close = findViewById(R.id.close);
        Button connexion = findViewById(R.id.connexion);
        Button create_account = findViewById(R.id.creer_compte);

        create_account.setOnClickListener(this);
        close.setOnClickListener(this);
        connexion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(),Accueil.class);
        switch (view.getId()){
            case R.id.close:
                intent.putExtra("fragment",0);
                break;
            case R.id.connexion:
                intent.putExtra("fragment",4);
                break;
            case R.id.creer_compte:
                intent.putExtra("fragment",5);
                break;
        }
        startActivity(intent);
        finish();
    }
}
