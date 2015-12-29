package vn.flearn.app.card.dao.interfaces;


import java.util.List;

import vn.flearn.app.card.models.Word;


/**
 * IWordDAO.
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:18 PM
 */
public interface IWordDAO extends IBaseDAO {
    List<Word> loadAll();

    Word load(String name);

    List<Word> loadByCourse(String type);

    List<Word> loadByCourse(int cid, boolean isShow);

    List<Word> loadByCourse(int cid, int from, int to, boolean isShow);

    void saveOrUpdate(Word word);

    void resetWords();

    void updateWord(int wordId, Object type);
}
