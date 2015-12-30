package vn.flearn.app.card.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.flearn.app.card.R;
import vn.flearn.app.card.models.Word;

/**
 * Created by hkhoi on 07/12/2015.
 * REUSABLE utility black-box for this project
 */
public class AppUtils {
    /**
     * All methods are called statically, no need to create a new instance
     */
    private static TextToSpeech textToSpeech;
    private static SQLiteDatabase database;
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

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null) {
            String path = context.getDatabasePath(DbContext.DATABASE_NAME).getAbsolutePath();
            database = SQLiteDatabase.openDatabase(path , null , SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }
    public static List<Word> getWords(Context context, String typeColor) {
        List<Word> words = new ArrayList<>();
        String query = "select * from word where color = '" + typeColor + "'";
        database = getDatabase(context);
        try {
            Cursor cursor = database.rawQuery(query , null);
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String meaning = cursor.getString(2);
                String type = cursor.getString(3);
                String example = cursor.getString(4);
                String pronounce = cursor.getString(5);
                String color = cursor.getString(6);
                int course = cursor.getInt(7);
                String exTrans = cursor.getString(8);
                words.add(new Word(name , id , course , type, pronounce, meaning, example, exTrans, color));
                Log.d("debug", "--- Get word: " + name);

            }
        } catch (Exception e) {
            Log.d("debug", "---ERROR: AppUtils: getWords: " + e.getMessage());
        }
        return words;
    }
}
