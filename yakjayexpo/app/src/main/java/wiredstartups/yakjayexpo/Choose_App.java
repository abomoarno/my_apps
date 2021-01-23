package wiredstartups.yakjayexpo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.APP_ADMIN;
import static wiredstartups.yakjayexpo.utils.Utils.APP_BADGES;
import static wiredstartups.yakjayexpo.utils.Utils.APP_CHECKS;
import static wiredstartups.yakjayexpo.utils.Utils.APP_ENTER_EXIT;
import static wiredstartups.yakjayexpo.utils.Utils.APP_NAME_LABEL;
import static wiredstartups.yakjayexpo.utils.Utils.APP_RESTO;
import static wiredstartups.yakjayexpo.utils.Utils.APP_ROOMS;
import static wiredstartups.yakjayexpo.utils.Utils.APP_VENDORS;
import static wiredstartups.yakjayexpo.utils.Utils.MESSAGE_ADMIN;
import static wiredstartups.yakjayexpo.utils.Utils.MESSAGE_CHECKS;
import static wiredstartups.yakjayexpo.utils.Utils.MESSAGE_ENTRY_EXIT;
import static wiredstartups.yakjayexpo.utils.Utils.MESSAGE_ROOMS;
import static wiredstartups.yakjayexpo.utils.Utils.MESSAGE_SCAN_RESTO;
import static wiredstartups.yakjayexpo.utils.Utils.MESSAGE_VALIDATE_BADGES;
import static wiredstartups.yakjayexpo.utils.Utils.MESSAGE_VENDOR;

public class Choose_App extends Fragment implements View.OnClickListener {

    private MainActivity context;
    private String message;
    private String app_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_app, container, false);

        context = (MainActivity)getActivity();

        view.findViewById(R.id.entry_exit).setOnClickListener(this);
        view.findViewById(R.id.rooms_app).setOnClickListener(this);
        view.findViewById(R.id.vendor_app).setOnClickListener(this);
        view.findViewById(R.id.resto_app).setOnClickListener(this);
        view.findViewById(R.id.checks_app).setOnClickListener(this);
        view.findViewById(R.id.admin_app).setOnClickListener(this);
        view.findViewById(R.id.validate_badges).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        message = "";
        app_name = "";

        switch (view.getId()){
            case R.id.entry_exit:
                message = MESSAGE_ENTRY_EXIT;
                app_name = APP_ENTER_EXIT;
                break;
            case R.id.rooms_app:
                message = MESSAGE_ROOMS;
                app_name = APP_ROOMS;
                break;
            case R.id.vendor_app:
                message = MESSAGE_VENDOR;
                app_name = APP_VENDORS;
                break;
            case R.id.validate_badges:
                message = MESSAGE_VALIDATE_BADGES;
                app_name = APP_BADGES;
                break;
            case R.id.resto_app:
                message = MESSAGE_SCAN_RESTO;
                app_name = APP_RESTO;
                break;
            case R.id.checks_app:
                message = MESSAGE_CHECKS;
                app_name = APP_CHECKS;
                break;
            case R.id.admin_app:
                message = MESSAGE_ADMIN;
                app_name = APP_ADMIN;
                break;
        }

        new AlertDialog.Builder(context)
                .setTitle("Confirmation du Choix")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preferences.edit().putString(APP_NAME_LABEL,app_name)
                                .apply();
                        context.setPage(new Loading());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        message = "";
                        app_name = "";
                    }
                }).show();

    }
}
