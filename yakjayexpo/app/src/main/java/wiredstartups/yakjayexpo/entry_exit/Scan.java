package wiredstartups.yakjayexpo.entry_exit;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import wiredstartups.yakjayexpo.R;
import wiredstartups.yakjayexpo.utils.Participant;
import wiredstartups.yakjayexpo.utils.Utils;

import static wiredstartups.yakjayexpo.MainActivity.preferences;
import static wiredstartups.yakjayexpo.entry_exit.Success.CODE_VERIFICATION_RESULT;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_BAD_QR;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_ERROR;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_INVALID_CODE;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_MULTIPLE_ENTRIES;
import static wiredstartups.yakjayexpo.entry_exit.Success.VERIFICATION_RESULT_OK;
import static wiredstartups.yakjayexpo.utils.Utils.ENTER_OPERATION;
import static wiredstartups.yakjayexpo.utils.Utils.ENTRY_ID_LABEL;
import static wiredstartups.yakjayexpo.utils.Utils.EXIT_OPERATION;
import static wiredstartups.yakjayexpo.utils.Utils.OPERATION_LABEL;

public class Scan extends Fragment implements ZXingScannerView.ResultHandler{

  private static final int MY_PERMISSIONS_REQUEST = 12;
  private static final String YAKJAY_VERIFY_CODE = "yakjay_verify_code";
  private static final String URL_VERIFY_CODE = "https://dirlabasexpo.com/admin/yakjay.php?action=entry&data=";
  private static final String URL_EXIT_CODE = "https://dirlabasexpo.com/admin/yakjay.php?action=exit&data=";
  private ZXingScannerView mScannerView;
  private EntryExit context;
  private ProgressDialog dialog;
  private String operation;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    mScannerView = new ZXingScannerView(getActivity());
    mScannerView.setResultHandler(this);
    context = (EntryExit) (getActivity());
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

    Bundle bundle = getArguments();

    if (bundle != null){
      operation = bundle.getString(OPERATION_LABEL);
    }
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
            /*new AlertDialog.Builder(context)
                    .setTitle("Test Code")
                    .setMessage(result.getText())
                    .setPositiveButton("Ok", null)
                    .setCancelable(true)
                    .show();*/

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

          JSONObject result = new JSONObject(data);

          if (result.getString("message").equals("fail")){
            sendError(VERIFICATION_RESULT_INVALID_CODE);
          }
          else {
            Bundle args = new Bundle();
            Participant participant = new Participant("");

            participant.setName(result.getString("message"));


            if (operation.equals(EXIT_OPERATION)) {
              participant.setName("Participant(e) Sorti(e)");
              args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_OK);

            }

            else {
              int warn = result.getInt("entered");

              if (warn == 1)
                args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_MULTIPLE_ENTRIES);
              else
                args.putString(CODE_VERIFICATION_RESULT, VERIFICATION_RESULT_OK);
            }

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

    JSONObject object = new JSONObject();

    try {
      object.put("entry_id",preferences.getInt(ENTRY_ID_LABEL,-1));
      object.put("badge_code",code);
      object.put("device_key","");

      Log.e("json", object.toString());

      if (operation.equals(ENTER_OPERATION))
        new Utils.SendToServer(context)
                .execute(URL_VERIFY_CODE + object.toString(),YAKJAY_VERIFY_CODE);
      else
        new Utils.SendToServer(context)
                .execute(URL_EXIT_CODE + object.toString() ,YAKJAY_VERIFY_CODE);
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

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onPrepareOptionsMenu(@NonNull Menu menu) {
    menu.findItem(R.id.action_settings).setVisible(false);
  }

}
