package afrimoov.tg.alerts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import afrimoov.tg.utilis.Utils;

public class Pub_Interval extends BroadcastReceiver {
    private static InterstitialAd pubInterst;
    private static InterstitialAd pubInterstVideo;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && !intent.getAction().equals("crtv.crtvnews.PUB_INTERVAL"))
            return;
        showAds();
    }
    public static void initAds(final Context context,boolean remove_pubs){
        if (remove_pubs)
            return;
        pubInterst = new InterstitialAd(context);
        pubInterstVideo = new InterstitialAd(context);
        pubInterst.setAdUnitId("ca-app-pub-2082216950067064/2202006212");
        pubInterstVideo.setAdUnitId("ca-app-pub-2082216950067064/6496474448");
        pubInterst.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadAds();
            }
        });
        pubInterstVideo.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                loadAds();
            }
        });
        loadAds();
    }
    private static void loadAds( ){
        if (pubInterst != null && !pubInterst.isLoaded())
            pubInterst.loadAd(new AdRequest.Builder().build());
        if (pubInterstVideo != null && !pubInterstVideo.isLoaded())
            pubInterstVideo.loadAd(new AdRequest.Builder().build());
    }
    private void showAds(){
        if(pubInterstVideo != null && pubInterstVideo.isLoaded())
            pubInterstVideo.show();
        else if(pubInterst != null && pubInterst.isLoaded())
            pubInterst.show();
    }
}