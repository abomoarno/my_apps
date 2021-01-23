package wiredstartups.yakjayexpo.badges;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.zxing.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import wiredstartups.yakjayexpo.utils.Participant;
import wiredstartups.yakjayexpo.utils.Utils;
import wiredstartups.yakjayexpo.vendor.Vendor;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.badges.Success.CODE_VERIFICATION_RESULT;
import static wiredstartups.yakjayexpo.badges.Success.VERIFICATION_RESULT_BAD_QR;
import static wiredstartups.yakjayexpo.badges.Success.VERIFICATION_RESULT_ERROR;
import static wiredstartups.yakjayexpo.badges.Success.VERIFICATION_RESULT_INVALID_CODE;
import static wiredstartups.yakjayexpo.badges.Success.VERIFICATION_RESULT_MULTIPLE_ENTRIES;
import static wiredstartups.yakjayexpo.badges.Success.VERIFICATION_RESULT_OK;
import static wiredstartups.yakjayexpo.utils.Utils.PARTNER_ID_LABEL;


public class Scan extends Fragment implements ZXingScannerView.ResultHandler{

    private static final int MY_PERMISSIONS_REQUEST = 12;
    private static final String YAKJAY_VERIFY_CODE = "yakjay_verify_code";
    private static final String URL_VERIFY_CODE = "https://dirlabasexpo.com/admin/yakjay.php?action=enable&data=";
    private ZXingScannerView mScannerView;
    private Badges context;
    private ProgressDialog dialog;

    private String code_participant = null;
    private String code_badge = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mScannerView = new ZXingScannerView(getActivity());
        mScannerView.setResultHandler(this);
        context = (Badges) (getActivity());
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
            if (code_participant == null){
                new AlertDialog.Builder(context)
                        .setMessage("Scannez le code de l'utilisateur")
                        .setTitle("Step 1")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mScannerView.startCamera();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.onBackPressed();
                            }
                        })
                        .show();
            }
            else {
                new AlertDialog.Builder(context)
                        .setMessage("Scannez maintenant le code du Badge associé")
                        .setTitle("Step 2")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mScannerView.startCamera();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.onBackPressed();
                            }
                        })
                        .show();
            }
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

                if (code_participant == null) {
                    String codeObj = values[4];

                    code_participant = codeObj.split("=")[1];

                    new AlertDialog.Builder(context)
                            .setMessage("Scannez maintenant le code du Badge associé")
                            .setTitle("Step 2")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mScannerView.resumeCameraPreview(Scan.this);
                                }
                            })
                            .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    context.onBackPressed();
                                }
                            })
                            .show();
                }
                else {

                    String codeObj = values[6];

                    code_badge = codeObj.split("=")[1];

                    //Log.e("User_code",code_participant);
                    //Log.e("Badge_code",code_badge);


                    verifyCode();
                }

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

    private void verifyCode(){

        dialog.show();

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context cxt, Intent intent) {

                String data = intent.getStringExtra("result");

                Log.e("Data",data);
                dialog.dismiss();

                try{
                    JSONObject result = new JSONObject(data);

                    if (result.getInt("result") <= 0){
                        sendError(VERIFICATION_RESULT_INVALID_CODE);
                    }
                    else {
                        Bundle args = new Bundle();

                        /*int warn = result.getInt("registered");

                        if (warn == 1)
                            args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_MULTIPLE_ENTRIES);
                        else*/
                        args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_OK);
                        args.putInt("badge_id",result.getInt("result"));

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

        JSONObject object = new JSONObject();

        try {
            object.put("user_code",code_participant);
            object.put("badge_code",code_badge);
            object.put("device_key","");

            Log.e("json", object.toString());

            new Utils.SendToServer(context)
                    .execute(URL_VERIFY_CODE + object.toString(),YAKJAY_VERIFY_CODE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendError(String result){
        Bundle args = new Bundle();
        args.putString(CODE_VERIFICATION_RESULT,result);

        Fragment fragment = new Success();
        fragment.setArguments(args);

        context.setPage(fragment);
    }

}
