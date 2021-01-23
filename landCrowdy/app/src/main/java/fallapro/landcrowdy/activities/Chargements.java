package fallapro.landcrowdy.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fallapro.landcrowdy.R;
import fallapro.landcrowdy.classes.Annonce;
import fallapro.landcrowdy.classes.Annonce_Adaptor;
import fallapro.landcrowdy.classes.Annonce_Small_Adaptor;
import fallapro.landcrowdy.classes.Pays;
import fallapro.landcrowdy.dbManagment.PaysManager;
import fallapro.landcrowdy.utils.Utils;

public class Chargements extends AppCompatActivity {

    public static String PAYS_ISO = "PAYS_ISO";
    public static String PAYS_NAME = "PAYS_NAME";
    public static String PAYS_LOGO = "PAYS_LOGO";
    public static String PAYS_SET = "PAYS_SET";

    public static String USER_NAME = "USER_NAME";
    public static String USER_MAIL = "USER_MAIL";
    public static String USER_PHONE = "USER_PHONE";
    public static String USER_WHATSAPP = "USER_WHATSAPP";
    public static String USER_IS_CONNECTED = "USER_IS_CONNECTED";
    public static String USER_CIVILITE = "USER_CIVILITE";


    public static SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chargements);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        getPaysInfos();
        getUserInfos();

        if (! Utils.PAYS_SET) {
            // TODO initialiser les alarms
            initPays();
            startActivity(new Intent(getApplicationContext(), ChoixPays.class));
        }
        else if(! Utils.USER_IS_CONNECTED)
            startActivity(new Intent(getApplicationContext(),Presentation.class));
        else
            startActivity(new Intent(getApplicationContext(),Accueil.class));

        finish();
    }

    public static void getPaysInfos(){
        Utils.PAYS_ISO = preferences.getString(PAYS_ISO,"");
        Utils.PAYS_NAME = preferences.getString(PAYS_NAME,"");
        Utils.PAYS_LOGO = preferences.getString(PAYS_LOGO,"");
        Utils.PAYS_SET = preferences.getBoolean(PAYS_SET,false);
    }

    public static void getUserInfos(){
        Utils.USER_CIVILITE = preferences.getString(USER_CIVILITE,"");
        Utils.USER_MAIL = preferences.getString(USER_MAIL,"");
        Utils.USER_NAME = preferences.getString(USER_NAME,"");
        Utils.USER_PHONE = preferences.getString(USER_PHONE,"");
        Utils.USER_WHATSAPP = preferences.getString(USER_WHATSAPP,"");
        Utils.USER_IS_CONNECTED = preferences.getBoolean(USER_IS_CONNECTED,false);
    }

    private void initPays(){
        PaysManager manager = new PaysManager(getApplicationContext());
        Utils.initPays();
        for (Pays p:Utils.LIST_PAYS) {
            manager.insertPays(p);
        }
    }

}
