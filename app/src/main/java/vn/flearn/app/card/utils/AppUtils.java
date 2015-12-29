package vn.flearn.app.card.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

import vn.flearn.app.card.R;

/**
 * Created by hkhoi on 07/12/2015.
 * REUSABLE utility black-box for this project
 */
public class AppUtils {
    /**
     * All methods are called statically, no need to create a new instance
     */
    private static TextToSpeech textToSpeech;
    private AppUtils() {}

    /**
     * @param context
     * @param key
     * @return true if it is the first-time run of the app
     */
    public static boolean getBooleanPreference(Context context, String key, boolean init) {
        SharedPreferences sharedPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, init);
    }

    /**
     * @param context
     * @param key
     * @param flag
     * Decides if it is the first-time run or not
     */
    public static void setBooleanPreference(Context context, String key, boolean flag) {
        SharedPreferences sharedPreferences
                = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, flag);
        editor.commit();    // NEVER FORGET THIS LINE OF CODE
    }
    public static TextToSpeech getTextToSpeech(final Context context)
    {
        if (textToSpeech == null) {
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    Toast.makeText(context , R.string.please_wait , Toast.LENGTH_SHORT).show();
                }
            }
            );
            textToSpeech.setLanguage(Locale.ENGLISH);
        }
        return textToSpeech;
    }
}
