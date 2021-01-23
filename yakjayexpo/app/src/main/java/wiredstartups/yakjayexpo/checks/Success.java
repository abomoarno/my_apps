package wiredstartups.yakjayexpo.checks;

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

public class Success extends Fragment {

    public static final String CODE_VERIFICATION_RESULT = "code_verification_result";
    private TextView nom;
    private TextView err_msg;

    private Participant participant;
    private Checks context;
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

        err_msg = view.findViewById(R.id.err_message);
        signal = view.findViewById(R.id.signal);
        context = (Checks) getActivity();

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
                participant = args.getParcelable("participant");
                setupResultOk();
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
        nom.setText(participant.getName());
        context.setTitre(participant.getName());

        signal.setImageResource(R.drawable.ic_checked);
        view.setBackgroundColor(getResources().getColor(R.color.confirm));
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
