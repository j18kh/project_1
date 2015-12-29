package vn.flearn.app.card.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import vn.flearn.app.card.dao.interfaces.IBaseDAO;
import vn.flearn.app.card.models.Course;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.DbContext;

/**
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:32 PM
 * BaseDAO.
 */
public class BaseDAO implements IBaseDAO {
    protected DbContext db;
//    private String dbPass = "";

    public Cursor loadAll(String table,String[] columns) {
        Log.d("BaseDAO", "table:" + table + " - " + columns.toString());
        return db.getReadableDatabase().query(table,columns,null,null,null,null,null);
    }

    public void setContext(DbContext dbContext) {
//        db = new DbContext(context);
        db = dbContext;
    }

    public DbContext getContext() {
        return db;
    }

    @Override
    public void resetData() {
        clearTable(Word.TABLE);
        clearTable(Course.TABLE);
    }
    public void resetLocalUserData() {
        clearTable(Word.TABLE);
    }

    public Cursor loadAll(String table, String[] columns, String selection, String[] args) {
        return db.getReadableDatabase().query(table,columns,selection,args,null,null,null);
    }

    public void insert(String table,ContentValues contentValues)
    {
       db.getWritableDatabase().insert(table,null,contentValues);
    }

    public long insertNew(String table, ContentValues contentValues) {

        return db.getWritableDatabase().insert(table, null, contentValues);
    }

    public void update(String table, ContentValues contentValues,String selection, String[] args) {
        db.getWritableDatabase().update(table,contentValues,selection,args);
    }

    public void execQuery(String query) {
        db.getWritableDatabase().execSQL(query);
    }

    /**
     * @Function name: clearTable
     * @Description clear table
     * @param table
     *            Table need to clear
     */
    private void clearTable(String table) {
        // Clear all record
        // Truncate
        db.getWritableDatabase().execSQL("DELETE FROM " + table);
    }

    /**
     * @Function name: resetWord
     * @Description reset table
     */
    public void resetWord() {
        // Update color = '' for all word data

        db.getWritableDatabase().execSQL("UPDATE " + Word.TABLE + " SET " + Word.COLOR + " = ''");
    }
}
