package big.win.classes;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.cinetpay.sdkjs.CinetPayWebAppInterface;

public class MyCinetPayWebAppInterface extends CinetPayWebAppInterface {

    public MyCinetPayWebAppInterface(Context c, String api_key, int site_id, String notify_url, String trans_id, int amount, String currency, String designation, String custom, boolean should_check_payment) {
        super(c, api_key, site_id, notify_url, trans_id, amount, currency, designation, custom, should_check_payment);

        Log.e("Appel Interface","OK");
    }

    @Override
    @JavascriptInterface
    public void onPaymentCompleted(String payment_info) {
    }

    @Override
    @JavascriptInterface
    public void onError(String code, String message) {
    }

    @Override
    @JavascriptInterface
    public void terminatePending(String apikey, int cpm_site_id, String cpm_trans_id) {
    }

    @Override
    @JavascriptInterface
    public void terminateSuccess(String payment_info) {
    }

    @Override
    @JavascriptInterface
    public void terminateFailed(String apikey, int cpm_site_id, String cpm_trans_id) {
    }

    @Override
    @JavascriptInterface
    public void checkPayment(String apikey, int cpm_site_id, String cpm_trans_id) {
    }

}
