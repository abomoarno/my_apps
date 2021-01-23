package fallapro.landcrowdy.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fallapro.landcrowdy.R;

public class EmailSendNotification extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_send_notification);

        Button fermer = findViewById(R.id.close);
        fermer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), Accueil.class);
        intent.putExtra("fragment",3);
        startActivity(new Intent(intent));
        finish();
    }
}
