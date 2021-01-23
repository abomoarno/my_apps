package wiredstartups.yakjayexpo.badges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import wiredstartups.yakjayexpo.R;
import wiredstartups.yakjayexpo.utils.Participant;
import wiredstartups.yakjayexpo.vendor.Vendor;

public class Success extends Fragment {

    public static final String CODE_VERIFICATION_RESULT = "code_verification_result";
    private TextView nom;
    private TextView warning;
    private TextView err_msg;


    private Badges context;
    private Bundle args;

    private ImageView signal;

    public static final String VERIFICATION_RESULT_OK = "code_ok";
    public static final String VERIFICATION_RESULT_ERROR = "code_error";
    public static final String VERIFICATION_RESULT_MULTIPLE_ENTRIES = "multiple_entries";
    static final String VERIFICATION_RESULT_BAD_QR = "bad_qr";
    public static final String VERIFICATION_RESULT_INVALID_CODE = "invalid_code";
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sucess, container, false);
        nom = view.findViewById(R.id.nom);
        warning = view.findViewById(R.id.warning);

        warning.setText("Vous avez déjà enregistré ce Badge !!");

        err_msg = view.findViewById(R.id.err_message);
        signal = view.findViewById(R.id.signal);
        context = (Badges) getActivity();

        args = getArguments();

        if (args != null){
            String result = args.getString(CODE_VERIFICATION_RESULT);
            if (result != null) {
                handleResult(result);
            }
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        context.toggleFabs(false);
        context.toggleHomeButton(true);
    }

    private void handleResult(String result){
        switch (result){
            case VERIFICATION_RESULT_OK:
                setupResultOk();
                break;
            case VERIFICATION_RESULT_MULTIPLE_ENTRIES:
                setupResultMultipleEntries();
                break;
            case VERIFICATION_RESULT_INVALID_CODE:
                setupResultInvalidCode();
                break;
            case VERIFICATION_RESULT_BAD_QR:
                setupResultBadQr();
                break;
            case VERIFICATION_RESULT_ERROR:
                setupResultUnknow();
                break;

        }
    }

    private void setupResultOk(){
        nom.setText("Badge Enabled");
        context.setTitre("Opération effectuée avec Succès");

        signal.setImageResource(R.drawable.ic_checked);
        view.setBackgroundColor(getResources().getColor(R.color.confirm));


    }
    private void setupResultMultipleEntries(){
        nom.setText("Warning");
        context.setTitre("Ce Badge a déjà été engregistré");

        signal.setImageResource(R.drawable.ic_caution);
        view.setBackgroundColor(getResources().getColor(R.color.caution));

        view.findViewById(R.id.bloc_warning).setVisibility(View.VISIBLE);

    }
    private void setupResultInvalidCode(){
        nom.setText("Erreur");
        err_msg.setText(R.string.invalid_code);
        context.setTitre(getString(R.string.invalid_code));

        signal.setImageResource(R.drawable.prohibition);
        view.setBackgroundColor(getResources().getColor(R.color.error));

        view.findViewById(R.id.bloc_error).setVisibility(View.VISIBLE);
    }
    private void setupResultBadQr(){
        nom.setText("Erreur");
        err_msg.setText(R.string.bad_qr);

        context.setTitre(getString(R.string.bad_qr));

        signal.setImageResource(R.drawable.prohibition);
        view.setBackgroundColor(getResources().getColor(R.color.error));

        view.findViewById(R.id.bloc_error).setVisibility(View.VISIBLE);
    }
    private void setupResultUnknow(){
        nom.setText("Erreur");
        err_msg.setText(R.string.unknown_error);
        context.setTitre(getString(R.string.unknown_error));

        signal.setImageResource(R.drawable.prohibition);
        view.setBackgroundColor(getResources().getColor(R.color.error));

        view.findViewById(R.id.bloc_error).setVisibility(View.VISIBLE);
    }

}
