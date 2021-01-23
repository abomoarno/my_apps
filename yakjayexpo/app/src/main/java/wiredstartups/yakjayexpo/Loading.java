package wiredstartups.yakjayexpo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wiredstartups.yakjayexpo.admin.Admin;
import wiredstartups.yakjayexpo.badges.Badges;
import wiredstartups.yakjayexpo.checks.Checks;
import wiredstartups.yakjayexpo.entry_exit.EntryExit;
import wiredstartups.yakjayexpo.resto.Resto;
import wiredstartups.yakjayexpo.rooms.Rooms;
import wiredstartups.yakjayexpo.vendor.Vendor;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.APP_ADMIN;
import static wiredstartups.yakjayexpo.utils.Utils.APP_BADGES;
import static wiredstartups.yakjayexpo.utils.Utils.APP_CHECKS;
import static wiredstartups.yakjayexpo.utils.Utils.APP_ENTER_EXIT;
import static wiredstartups.yakjayexpo.utils.Utils.APP_NAME_LABEL;
import static wiredstartups.yakjayexpo.utils.Utils.APP_RESTO;
import static wiredstartups.yakjayexpo.utils.Utils.APP_ROOMS;
import static wiredstartups.yakjayexpo.utils.Utils.APP_VENDORS;

public class Loading extends Fragment {

    private MainActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading, container, false);

        context = (MainActivity)getActivity();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (preferences.getString(APP_NAME_LABEL,null) == null) {
                    context.setPage(new Choose_App());
                }
                else {

                    String app_name = preferences.getString(APP_NAME_LABEL,null);
                    if (app_name == null)
                        return;

                    switch (app_name){
                        case APP_ENTER_EXIT:
                            startActivity(new Intent(context, EntryExit.class));
                            break;
                        case APP_ROOMS:
                            startActivity(new Intent(context, Rooms.class));
                            break;
                        case APP_VENDORS:
                            startActivity(new Intent(context, Vendor.class));
                            break;
                        case APP_CHECKS:
                            startActivity(new Intent(context, Checks.class));
                            break;
                        case APP_BADGES:
                            startActivity(new Intent(context, Badges.class));
                            break;
                        case APP_RESTO:
                            startActivity(new Intent(context, Resto.class));
                            break;
                        case APP_ADMIN:
                            startActivity(new Intent(context, Admin.class));
                            break;
                    }

                }
            }
        }, 1000);
    }
}
