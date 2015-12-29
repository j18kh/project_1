package vn.flearn.app.card.dao.interfaces;

import android.content.Context;

import java.util.List;

import vn.flearn.app.card.models.Course;


/**
 * ICourseDAO.
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:18 PM
 */
public interface ICourseDAO extends IBaseDAO {
    List<Course> loadAll();
    Course load(String name);
    void saveOrUpdate(Course course);
    Course saveItem(Course course);
}
