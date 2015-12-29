package vn.flearn.app.card.dao.interfaces;

import android.content.Context;

import vn.flearn.app.card.utils.DbContext;

/**
 * User: LocNTV
 * Date: 01/04/14
 * Time: 1:20 PM
 * IBaseDao.
 */
public interface IBaseDAO {
    void setContext(DbContext dbContext);
    DbContext getContext();
    void resetData();
}
