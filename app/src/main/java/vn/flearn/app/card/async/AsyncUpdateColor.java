package vn.flearn.app.card.async;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.DbContext;

/**
 * Created by hkhoi on 12/30/15.
 */
public class AsyncUpdateColor extends AsyncTask<Void , Void , Void> {

    private Context context;
    private int id;
    private String color;

    public AsyncUpdateColor(Context context, int id, String color) {
        this.context = context;
        this.id = id;
        this.color = color;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase database = AppUtils.getDatabase(context);
        ContentValues values = new ContentValues();
        values.put("Color" , color);
        database.update("Word" , values , "_id=" + id , null);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
