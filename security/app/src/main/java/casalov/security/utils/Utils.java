package casalov.security.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import casalov.security.activities.FinalInscription;

public class Utils {

    public static boolean is_connected;
    public static boolean is_subscribed;
    public static boolean is_pay_ok;

    public static String user_name;
    public static String user_mail;
    public static String user_phone;
    public static String user_location;

    public static String IS_CCONNECTED = "IS_CCONNECTED";
    public static String IS_SUBSCRIBED = "IS_SUBSCRIBED";
    public static String IS_PAY_OK = "IS_PAY_OK";
    public static String USER_NAME = "USER_NAME";
    public static String USER_MAIL = "USER_MAIL";
    public static String USER_PHONE = "USER_PHONE";
    public static String USER_LOCATION = "USER_LOCATION";
    public static String USER_TYPE = "USER_TYPE";
    public static String CLENT_PARTICULIER = "particulier";
    public static String CLENT_ENTREPRISE = "entreprise";

    public static String INTERVENTION_PARTICULIER = "intervention_particulier";
    public static String INTERVENTION_ENTREPRISE = "intervention_entreprise";

    public static String ABONNEMENT_PARTICULIER_6_MOIS = "abonnement_particulier_6_mois";
    public static String ABONNEMENT_PARTICULIER_12_MOIS = "abonnement_particulier_12_mois";
    public static String ABONNEMENT_ENTREPRISE_6_MOIS = "abonnement_entreprise_6_mois";
    public static String ABONNEMENT_ENTREPRISE_12_MOIS = "abonnement_entreprise_12_mois";

    public static final int NOTIFICATION_ID = 2122412;
    public static final long NOTIFICATION_INTERVAL = 18000000;
    public static final long UPDATE_INTERVAL = 4000000;

    public static final long GET_ENTREPRISES_INTERVAL = 2000000;

    public static String USER_ID = "USER_ID";


    public static final String STATUT_OK = "INTERVENTION_OK";
    public static final String STATUT_NOT_OK = "INTERVENTION_NOT_OK";

}
