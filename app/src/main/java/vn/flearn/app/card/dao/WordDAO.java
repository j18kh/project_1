package vn.flearn.app.card.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import vn.flearn.app.card.dao.interfaces.IWordDAO;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.DbContext;

/**
 * WordDAO.
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:18 PM
 */
public class WordDAO extends BaseDAO implements IWordDAO, Parcelable {

    @Override
    public List<Word> loadAll() {
        List<Word> result = new ArrayList<Word>();
        Cursor cursor = loadAll(Word.TABLE, Word.COLUMNS);
        if (cursor.moveToFirst()) {
            do {
                Word client = new Word();
                client.setID(cursor.getInt(cursor.getColumnIndex(Word._ID)));
                client.setCourseId(cursor.getInt(cursor.getColumnIndex(Word.COURSE_ID)));
                client.setName(cursor.getString(cursor.getColumnIndex(Word.NAME)));
                client.setType(cursor.getString(cursor.getColumnIndex(Word.TYPE)));
                client.setPronoun(cursor.getString(cursor.getColumnIndex(Word.PRONOUN)));
                client.setMeaning(cursor.getString(cursor.getColumnIndex(Word.MEANING)));
                client.setExample(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE)));
                client.setExampleTrans(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE_TRANS)));
                client.setColor(cursor.getString(cursor.getColumnIndex(Word.COLOR)));

                result.add(client);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        DbContext db = getContext();
        if (db != null) {
            db.close();
        }
        return result;
    }

    @Override
    public List<Word> loadByCourse(String type) {
        List<Word> result = new ArrayList<Word>();
        String selection = Word.COLOR + " = ?";
        String[] args = {type};

        Cursor cursor = loadAll(Word.TABLE, Word.COLUMNS, selection, args);
        if (cursor.moveToFirst()) {
            do {
                Word client = new Word();
                client.setID(cursor.getInt(cursor.getColumnIndex(Word._ID)));
                client.setCourseId(cursor.getInt(cursor.getColumnIndex(Word.COURSE_ID)));
                client.setName(cursor.getString(cursor.getColumnIndex(Word.NAME)));
                client.setType(cursor.getString(cursor.getColumnIndex(Word.TYPE)));
                client.setPronoun(cursor.getString(cursor.getColumnIndex(Word.PRONOUN)));
                client.setMeaning(cursor.getString(cursor.getColumnIndex(Word.MEANING)));
                client.setExample(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE)));
                client.setExampleTrans(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE_TRANS)));
                client.setColor(cursor.getString(cursor.getColumnIndex(Word.COLOR)));

                result.add(client);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        DbContext db = getContext();
        if (db != null) {
            db.close();
        }
        return result;
    }

    @Override
    public List<Word> loadByCourse(int cid, boolean isShow) {
        List<Word> result = new ArrayList<Word>();
        String selection = Word.COURSE_ID + " = " + cid;
        /*String[] args = {Constant.WORD_COLOR_DONE};

        if (isShow) {
            selection += " AND " + Word.COLOR + " <> ?";
        } else {
            selection += " AND " + Word.COLOR + " = ?";
        }*/
//        Cursor cursor = loadAll(Word.TABLE, Word.COLUMNS, selection, args);
        Cursor cursor = loadAll(Word.TABLE, Word.COLUMNS, selection, null);
        if (cursor.moveToFirst()) {
            do {
                Word client = new Word();
                client.setID(cursor.getInt(cursor.getColumnIndex(Word._ID)));
                client.setCourseId(cursor.getInt(cursor.getColumnIndex(Word.COURSE_ID)));
                client.setName(cursor.getString(cursor.getColumnIndex(Word.NAME)));
                client.setType(cursor.getString(cursor.getColumnIndex(Word.TYPE)));
                client.setPronoun(cursor.getString(cursor.getColumnIndex(Word.PRONOUN)));
                client.setMeaning(cursor.getString(cursor.getColumnIndex(Word.MEANING)));
                client.setExample(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE)));
                client.setExampleTrans(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE_TRANS)));
                client.setColor(cursor.getString(cursor.getColumnIndex(Word.COLOR)));

                result.add(client);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        DbContext db = getContext();
        if (db != null) {
            db.close();
        }
        return result;
    }

    @Override
    public List<Word> loadByCourse(int cid, int numFrom, int numTo, boolean isShow) {
        List<Word> result = new ArrayList<Word>();
        int numCount = numTo - numFrom;

        String selection = Word.COURSE_ID + " = " + cid;
        /*String[] args = {Constant.WORD_COLOR_DONE};
        if (isShow) {
            selection += " AND " + Word.COLOR + " <> ?";
        } else {
            selection += " AND " + Word.COLOR + " = ?";
        }*/
        selection += " LIMIT " + numFrom + "," + numCount;

//        Cursor cursor = loadAll(Word.TABLE, Word.COLUMNS, selection, args);
        Cursor cursor = loadAll(Word.TABLE, Word.COLUMNS, selection, null);
        if (cursor.moveToFirst()) {
            do {
                Word client = new Word();
                client.setID(cursor.getInt(cursor.getColumnIndex(Word._ID)));
                client.setCourseId(cursor.getInt(cursor.getColumnIndex(Word.COURSE_ID)));
                client.setName(cursor.getString(cursor.getColumnIndex(Word.NAME)));
                client.setType(cursor.getString(cursor.getColumnIndex(Word.TYPE)));
                client.setPronoun(cursor.getString(cursor.getColumnIndex(Word.PRONOUN)));
                client.setMeaning(cursor.getString(cursor.getColumnIndex(Word.MEANING)));
                client.setExample(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE)));
                client.setExampleTrans(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE_TRANS)));
                client.setColor(cursor.getString(cursor.getColumnIndex(Word.COLOR)));

                result.add(client);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        DbContext db = getContext();
        if (db != null) {
            db.close();
        }
        return result;
    }

    @Override
    public Word load(String wordId) {
        String selection = "NAME = ?";
        String[] args = {wordId};
        Cursor cursor = loadAll(Word.TABLE, Word.COLUMNS, selection, args);

        Word client = null;
        if (cursor.moveToFirst()) {

            client = new Word();
            client.setID(cursor.getInt(cursor.getColumnIndex(Word._ID)));
            client.setCourseId(cursor.getInt(cursor.getColumnIndex(Word.COURSE_ID)));
            client.setName(cursor.getString(cursor.getColumnIndex(Word.NAME)));
            client.setType(cursor.getString(cursor.getColumnIndex(Word.TYPE)));
            client.setPronoun(cursor.getString(cursor.getColumnIndex(Word.PRONOUN)));
            client.setMeaning(cursor.getString(cursor.getColumnIndex(Word.MEANING)));
            client.setExample(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE)));
            client.setExampleTrans(cursor.getString(cursor.getColumnIndex(Word.EXAMPLE_TRANS)));
            client.setColor(cursor.getString(cursor.getColumnIndex(Word.COLOR)));

        }
        return client;
    }

    @Override
    public void saveOrUpdate(Word word) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Word.COURSE_ID, word.getCourseId());
        contentValues.put(Word.NAME, word.getName());
        contentValues.put(Word.TYPE, word.getType());
        contentValues.put(Word.PRONOUN, word.getPronoun());
        contentValues.put(Word.MEANING, word.getMeaning());
        contentValues.put(Word.EXAMPLE, word.getExample());
        contentValues.put(Word.EXAMPLE_TRANS, word.getExampleTrans());
        contentValues.put(Word.COLOR, word.getColor());

        if (word.getID() <= 0) {
            insert(Word.TABLE, contentValues);
        } else {
            String selection = "_ID = ?";
            String[] args = {Integer.toString(word.getID())};
            update(Word.TABLE, contentValues, selection, args);
        }
    }

    @Override
    public void resetWords() {
        resetWord();
    }

    @Override
    public void updateWord(int wordId, Object type) {
        String color = null;
        if (type.equals("done")) {
            color = Constant.WORD_COLOR_DONE;
        } else if (type.equals("difficult")) {
            color = Constant.WORD_COLOR_DIFFICULT;
        } else if (type.equals("review")) {
            color = "";
        }

        String query = "UPDATE " + Word.TABLE
                + " SET " + Word.COLOR + " = '" + color + "'"
                + " WHERE " + Word._ID + " = " + wordId;
        execQuery(query);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
