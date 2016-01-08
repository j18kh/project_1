package vn.flearn.app.card.models;

import java.util.List;

/**
 * Course.
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:21 PM
 */
public class Course implements BaseModel {

    public static final String NAME = "Name";
    public static final String LEVEL = "Level";
    public static final String DESCRIPTION = "Description";

    public static final String TABLE = "Course";
    public static final String IS_ACTIVATED = "IsActivated";
    public static final String[] COLUMNS = {_ID, NAME, LEVEL, DESCRIPTION};

    private int ID;
    private String Name;
    private String Level;
    private String Description;
    private List<Word> Words;
    private String IsActivated;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<Word> getWords() {
        return Words;
    }

    public void setWords(List<Word> words) {
        Words = words;
    }

    public String getIsActivated() {
        return IsActivated;
    }

    public void setIsActivated(String isActivated) {
        IsActivated = isActivated;
    }
}