package wiredstartups.yakjayexpo.vendor;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import wiredstartups.yakjayexpo.R;
import wiredstartups.yakjayexpo.utils.Utils;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.PARTNER_ID_LABEL;
import static wiredstartups.yakjayexpo.vendor.Success.VERIFICATION_RESULT_INVALID_CODE;

public class Enter_Pin extends Fragment implements View.OnClickListener {

    private Vendor context;
    private EditText pin;
    private Button send;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.enter_pin, container, false);

        context = (Vendor)getActivity();

        pin = view.findViewById(R.id.pin_code);
        send = view.findViewById(R.id.send_pin);

        view.findViewById(R.id.send_pin).setOnClickListener(this);

        pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>3)
                    send.setEnabled(true);
                else
                    send.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_pin){
            verifyPin();
        }
    }

    private void verifyPin() {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Vérification en cours; Veuillez patienter un instant ...");
        dialog.setTitle("Vérification");
        dialog.setCancelable(false);

        dialog.show();

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context ct, Intent intent) {

                String data = intent.getStringExtra("result");
                dialog.dismiss();

                try {
                    JSONArray jsonObject = new JSONArray(data);
                    JSONObject result = jsonObject.getJSONObject(0);

                    if (result.getString("vendor_id").equals("invalid")){
                       new AlertDialog.Builder(context)
                               .setCancelable(true)
                               .setPositiveButton("Ok", null)
                               .setTitle("ERREUR")
                               .setMessage("Votre code n'est pas valide, Merci de le vérifier puis recommencer.")
                               .show();
                    }

                    else {
                        int vendor_id = result.getInt("vendor_id");
                        String vendor_name = result.getString("vendor_name");

                        preferences.edit().putInt(PARTNER_ID_LABEL,vendor_id)
                                .apply();
                        context.onBackPressed();
                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }

            }
        }, new IntentFilter("pin_verification"));

        new Utils.SendToServer(context)
                .execute("https://dirlabasexpo.com/admin/yakjay.php?action=verify_pin&pin=" + pin.getText().toString(),"pin_verification");

    }

    @Override
    public void onStart() {
        super.onStart();

        context.toggleFabs(false);
    }
}
