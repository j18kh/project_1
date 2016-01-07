package vn.flearn.app.card.async;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.DbContext;

/**
 * Created by hkhoi on 12/30/15.
 */
public class AsyncReset  extends AsyncTask<Void , Void , Void> {

    private Context context;

    public AsyncReset(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        SQLiteDatabase database = AppUtils.getDatabase(context);
        ContentValues values = new ContentValues();
        values.put("Color" , Constant.WORD_COLOR_NEUTRAL);
        database.update("Word" , values , "" , null);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
