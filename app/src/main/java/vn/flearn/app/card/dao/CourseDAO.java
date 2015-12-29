package vn.flearn.app.card.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.flearn.app.card.dao.interfaces.ICourseDAO;
import vn.flearn.app.card.models.Course;
import vn.flearn.app.card.utils.DbContext;

/**
 * PickDeliverySessionDAO.
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:18 PM
 */
public class CourseDAO extends BaseDAO implements ICourseDAO {

    @Override
    public List<Course> loadAll() {
        List<Course> result = new ArrayList<Course>();
        Cursor cursor = loadAll(Course.TABLE, Course.COLUMNS);
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setID(cursor.getInt(cursor.getColumnIndex(Course._ID)));
                course.setName(cursor.getString(cursor.getColumnIndex(Course.NAME)));
                course.setLevel(cursor.getString(cursor.getColumnIndex(Course.LEVEL)));
                course.setDescription(cursor.getString(cursor.getColumnIndex(Course.DESCRIPTION)));

                result.add(course);
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
    public Course load(String name) {
        String selection = "NAME = ?";
        String[] args = {name};
        Cursor cursor = loadAll(Course.TABLE, Course.COLUMNS, selection, args);

        Course course = null;
        if (cursor.moveToFirst()) {

            course = new Course();
            course.setID(cursor.getInt(cursor.getColumnIndex(Course._ID)));

        }
        return course;
    }

    @Override
    public void saveOrUpdate(Course course) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Course.NAME, course.getName());
        contentValues.put(Course.LEVEL, course.getLevel());
        contentValues.put(Course.DESCRIPTION, course.getDescription());

        if (course.getID() <=0 ) {
            insert(Course.TABLE, contentValues);
        } else {
            String selection = "_ID = ?";
            String[] args = {Integer.toString(course.getID())};
            update(Course.TABLE, contentValues, selection, args);
        }
    }

    @Override
    public Course saveItem(Course course) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Course.NAME, course.getName());
        contentValues.put(Course.LEVEL, course.getLevel());
        contentValues.put(Course.DESCRIPTION, course.getDescription());

        insert(Course.TABLE, contentValues);
        long id = insertNew(Course.TABLE, contentValues);

        course.setID(Integer.parseInt(id + ""));
        return course;
    }
}
