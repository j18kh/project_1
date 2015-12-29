package vn.flearn.app.card.utils;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:34 PM
 * DbContext.
 */
//public class DbContext extends android.database.sqlite.SQLiteOpenHelper {
public class DbContext extends SQLiteAssetHelper {


    public static final String DATABASE_NAME = "eflashcard.db";
    public static final String DBPASS = "eflashcard";
    public static final int DATABASE_VERSION = 3;
    public static final boolean IS_ENCRYPT = false;

    public DbContext(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // call this method to force a database overwrite every time the version number increments:
        setForcedUpgrade();
//        SQLiteDatabase.loadLibs(context);
//        Log.d(context.getExternalFilesDir(null).getAbsolutePath());context.getExternalFilesDir(null).getAbsolutePath() + "/" +
    }



    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        //To change body of implemented methods use File | Settings | File Templates.
        switch (newVersion)
        {
            /*case 2:
                db.execSQL("DELETE FROM " + Course.TABLE);
                db.execSQL("DELETE FROM " + Word.TABLE);
                break;
            case 3:
                String sql = "INSERT INTO " + Course.TABLE +
                        " (" + Course.NAME + ", " + Course.DESCRIPTION + ", " + Course.LEVEL +
                        ", " + Course.PRICE + ", " + Course.IS_ACTIVATED + ") VALUES " +
                        "('FC07', 'Luyện thi TOEIC', '600', '0', 0)," +
                        "('FC08', 'Luyện thi IELTS', '600', '0', 0)," +
                        "('FC09', 'Luyện thi TN - ĐH', '1000', '0', 0)";
                db.execSQL(sql);
                break;*/
        }
    }



}
