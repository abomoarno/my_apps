package arnoarobase.coiffuresafricaines.activities;

import android.app.Application;

import arnoarobase.coiffuresafricaines.calsses.AlarmInitialisation;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AlarmInitialisation.initPubInterval(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AlarmInitialisation.clearPubInterval(getApplicationContext());
    }
}
