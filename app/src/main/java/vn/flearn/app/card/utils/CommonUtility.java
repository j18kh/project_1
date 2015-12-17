package vn.flearn.app.card.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hkhoi on 07/12/2015.
 * REUSABLE utility black-box for this project
 */
public class CommonUtility {
    /**
     * All methods are called statically, no need to create a new instance
     */
    private CommonUtility() {}

    /**
     * @param context
     * @param key
     * @return true if it is the first-time run of the app
     */
    public static boolean isFirstTimeRun(Context context, String key) {
        SharedPreferences sharedPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, true);
    }

    /**
     * @param context
     * @param key
     * @param flag
     * Decides if it is the first-time run or not
     */
    public static void setFirstTimeRun(Context context, String key, boolean flag) {
        SharedPreferences sharedPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, flag);
        editor.commit();    // NEVER FORGET THIS LINE OF CODE
    }
}
