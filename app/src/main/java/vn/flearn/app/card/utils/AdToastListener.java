package vn.flearn.app.card.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

/**
 * Created by hkhoi on 03/12/2015.
 */
public class AdToastListener extends AdListener {
    private Context mContext;

    public AdToastListener(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onAdClosed() {
        super.onAdClosed();
    }

    @Override
    public void onAdFailedToLoad(int errorCode) {
        super.onAdFailedToLoad(errorCode);
        String error = "Unknown";
        switch (errorCode) {
            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                error = "Internal error";
                break;
            case AdRequest.ERROR_CODE_INVALID_REQUEST:
                error = "Invalid request";
                break;
            case AdRequest.ERROR_CODE_NETWORK_ERROR:
                error = "Network error";
                break;
            case AdRequest.ERROR_CODE_NO_FILL:
                error = "No fill";
                break;
        }
        Log.d("debug", "---AdERROR: " + error);
    }

    @Override
    public void onAdLeftApplication() {
        super.onAdLeftApplication();
    }

    @Override
    public void onAdOpened() {
        super.onAdOpened();
    }

    @Override
    public void onAdLoaded() {
        super.onAdLoaded();
    }
}
