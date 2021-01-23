package wiredstartups.yakjayexpo.rooms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import wiredstartups.yakjayexpo.R;

public class Success extends Fragment {

    public static final String CODE_VERIFICATION_RESULT = "code_verification_result";
    private TextView nom;
    private TextView err_msg;

    private Rooms context;

    private ImageView signal;

    public static final String VERIFICATION_RESULT_STANDARD = "standard_ticket";
    public static final String VERIFICATION_RESULT_CLUB = "club_ticket";
    public static final String VERIFICATION_RESULT_PREMIUM = "premium_ticket";
    public static final String VERIFICATION_RESULT_VIP = "vip_ticket";
    public static final String VERIFICATION_RESULT_ERROR = "code_error";
    static final String VERIFICATION_RESULT_BAD_QR = "bad_qr";
    public static final String VERIFICATION_RESULT_INVALID_CODE = "invalid_code";
    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.room_sucess, container, false);
        nom = view.findViewById(R.id.nom);

        err_msg = view.findViewById(R.id.err_message);
        signal = view.findViewById(R.id.signal);
        context = (Rooms) getActivity();

        Bundle args = getArguments();

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
            case VERIFICATION_RESULT_STANDARD:
                setupResultStandard();
                break;
            case VERIFICATION_RESULT_CLUB:
                setupResultClub();
                break;
            case VERIFICATION_RESULT_PREMIUM:
                setupResultPremium();
                break;
            case VERIFICATION_RESULT_VIP:
                setupResultVip();
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
            default:
                setupResult(result);
                break;

        }
    }

    private void setupResultPremium(){
        nom.setText("Participant PREMIUM");
        context.setTitre("PREMIUM");

        signal.setImageResource(R.drawable.ic_checked);
        view.setBackgroundColor(getResources().getColor(R.color.confirm));
    }
    private void setupResultVip(){
        nom.setText("Participant VIP");
        context.setTitre("VIP");

        signal.setImageResource(R.drawable.ic_checked);
        view.setBackgroundColor(getResources().getColor(R.color.confirm));
    }
    private void setupResultClub(){
        nom.setText("Participant CLUB");
        context.setTitre("CLUB");

        signal.setImageResource(R.drawable.ic_checked);
        view.setBackgroundColor(getResources().getColor(R.color.confirm));
    }
    private void setupResultStandard(){
        nom.setText("Participant STANDARD");
        context.setTitre("STANDARD");

        signal.setImageResource(R.drawable.ic_checked);
        view.setBackgroundColor(getResources().getColor(R.color.confirm));
    }
    private void setupResult(String result){
        nom.setText(result);
        context.setTitre(result);

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
