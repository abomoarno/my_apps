package fallapro.landcrowdy.utils;

import java.util.ArrayList;
import java.util.List;

import fallapro.landcrowdy.classes.Pays;

public class Utils {

    public static final String URL_ALERTS_GET = "";
    public static final String URL_ALERTS_SEND = "";
    public static final String URL_ALERTS_UPDATE = "";
    public static final String URL_ALERTS_CHECK = "";

    public static final String URL_JOBS_GET = "";
    public static final String URL_JOBS_SEND = "";
    public static final String URL_JOBS_GET_UPDATES = "";

    public static final String URL_TERAAINS_GET = "";
    public static final String URL_TERRAINS_SEND = "";
    public static final String URL_TERRAINS_GET_UPDATES = "";

    public static final String URL_MAISONS_GET = "";
    public static final String URL_MAISON_SEND = "";
    public static final String URL_MAISONS_GET_UPDATES = "";

    public static final String URL_USERS_GET = "";
    public static final String URL_USRS_SEND = "";

    public static final String URL_LIKES_SEND = "";
    public static final String URL_AFFICHAGES_SEND = "";
    public static final String URL_CLICKS_SEND = "";

    // USER
    public static String USER_NAME;
    public static String USER_MAIL;
    public static String USER_PHONE;
    public static String USER_WHATSAPP;
    public static String USER_CIVILITE;
    public static boolean USER_IS_CONNECTED;

    // PAYS
    public static String PAYS_ISO;
    public static String PAYS_NAME;
    public static String PAYS_LOGO;
    public static boolean PAYS_SET;

    public static List<Pays> LIST_PAYS;

    public static void initPays(){

        LIST_PAYS = new ArrayList<>();

        Pays cm = new Pays("cm","cameroun");
        cm.setLogo("cm.png");
        cm.setDevise("FCFA");

        Pays rdc = new Pays("rdc","rd Congo");
        rdc.setLogo("rdc.png");
        rdc.setDevise("US$");

        Pays ci = new Pays("ci","côte d'ivoire");
        ci.setLogo("ci.png");
        ci.setDevise("FCFA");

        Pays ga = new Pays("ga","gabon");
        ga.setLogo("ga.png");
        ga.setDevise("FCFA");

        Pays sn = new Pays("sn","sénégal");
        sn.setLogo("sn.png");
        sn.setDevise("FCFA");

        LIST_PAYS.add(cm);
        LIST_PAYS.add(rdc);
        LIST_PAYS.add(ci);
        LIST_PAYS.add(ga);
        LIST_PAYS.add(sn);

    }
}
