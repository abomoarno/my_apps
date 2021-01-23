package wiredstartups.yakjayexpo.vendor;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import wiredstartups.yakjayexpo.utils.Participant;
import wiredstartups.yakjayexpo.utils.Utils;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.utils.Utils.PARTNER_ID_LABEL;
import static wiredstartups.yakjayexpo.vendor.Success.CODE_VERIFICATION_RESULT;
import static wiredstartups.yakjayexpo.vendor.Success.VERIFICATION_RESULT_BAD_QR;
import static wiredstartups.yakjayexpo.vendor.Success.VERIFICATION_RESULT_ERROR;
import static wiredstartups.yakjayexpo.vendor.Success.VERIFICATION_RESULT_INVALID_CODE;
import static wiredstartups.yakjayexpo.vendor.Success.VERIFICATION_RESULT_MULTIPLE_ENTRIES;
import static wiredstartups.yakjayexpo.vendor.Success.VERIFICATION_RESULT_OK;

public class Scan extends Fragment implements ZXingScannerView.ResultHandler{

    private static final int MY_PERMISSIONS_REQUEST = 12;
    private static final String YAKJAY_VERIFY_CODE = "yakjay_verify_code";
    private static final String URL_VERIFY_CODE = "https://dirlabas.com/admin/yakjay.php?action=scan&code=";
    private ZXingScannerView mScannerView;
    private Vendor context;
    private ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mScannerView = new ZXingScannerView(getActivity());
        mScannerView.setResultHandler(this);
        context = (Vendor) (getActivity());
        dialog = new ProgressDialog(context);
        dialog.setTitle("Vérification");
        dialog.setMessage("Patientez un peu");
        dialog.setCancelable(false);
        return mScannerView;
    }

    @Override
    public void onStart() {
        super.onStart();
        context.setTitre("Scanner un Code");

        context.toggleFabs(false);
        context.toggleHomeButton(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED){
            mScannerView.startCamera();
        }
        else{
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        if (result.getText() != null){

            try {
                String[] values = result.getText().split(";");

                String codeObj = values[6];

                String code = codeObj.split("=")[1];

                dialog.show();

                verifyCode(code);

            }

            catch (Exception e){
                e.printStackTrace();
                sendError(VERIFICATION_RESULT_BAD_QR);
            }
        }
        else {
            sendError(VERIFICATION_RESULT_BAD_QR);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mScannerView.stopCamera();
            }
            else {
                Toast.makeText(getActivity(), "Permission Refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void verifyCode(String code){

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context cxt, Intent intent) {

                String data = intent.getStringExtra("result");
                dialog.dismiss();

                try{
                    JSONArray jsonObject = new JSONArray(data);
                    JSONObject result = jsonObject.getJSONObject(0);

                    if (result.getString("conf_code").equals("invalid")){
                        sendError(VERIFICATION_RESULT_INVALID_CODE);
                    }
                    else {
                        Bundle args = new Bundle();
                        Participant participant = new Participant(result.getString("conf_code"));

                        participant.setName(result.getString("name"));

                        int warn = result.getInt("visited");

                        if (warn == 1)
                            args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_MULTIPLE_ENTRIES);
                        else
                            args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_OK);

                        args.putParcelable("participant",participant);

                        Fragment fragment = new Success();
                        fragment.setArguments(args);
                        context.setPage(fragment);
                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                    sendError(VERIFICATION_RESULT_ERROR);
                }

                finally {
                    context.unregisterReceiver(this);
                }

            }
        }, new IntentFilter(YAKJAY_VERIFY_CODE));

        new Utils.SendToServer(context)
                .execute(URL_VERIFY_CODE + code + "vendor_id=" + preferences.getInt(PARTNER_ID_LABEL,-1),YAKJAY_VERIFY_CODE);
    }

    private void sendError(String result){
        Bundle args = new Bundle();
        args.putString(CODE_VERIFICATION_RESULT,result);

        Fragment fragment = new Success();
        fragment.setArguments(args);

        context.setPage(fragment);
    }

}
