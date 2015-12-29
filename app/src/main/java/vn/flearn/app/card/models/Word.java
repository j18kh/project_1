package vn.flearn.app.card.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Word.
 * User: LocNTV
 * Date: 01/04/14
 * Time: 5:21 PM
 */
public class Word implements BaseModel , Parcelable {

    public static final String _ID = "_id";
    public static final String COURSE_ID = "CourseId";
    public static final String NAME = "Name";
    public static final String TYPE = "Type"; //http://www.schoolwork.org/blog/english-grammar-word-types
    public static final String PRONOUN = "Pronoun"; // Spelling
    public static final String MEANING = "Meaning";
    public static final String EXAMPLE = "Example";
    public static final String EXAMPLE_TRANS = "ExampleTrans";
    public static final String COLOR = "Color"; // Word emotion 0:normal - 1:difficult - 2:done

    public static final String TABLE = "Word";
    public static final String[] COLUMNS = {_ID, COURSE_ID, NAME
            , TYPE, PRONOUN, MEANING, EXAMPLE, EXAMPLE_TRANS, COLOR};

    private int ID;
    private int CourseId;
    private String Name;
    private String Type;
    private String Pronoun;
    private String Meaning;
    private String Example;
    private String ExampleTrans;
    private String Color;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPronoun() {
        return Pronoun;
    }

    public void setPronoun(String pronoun) {
        Pronoun = pronoun;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String getExample() {
        return Example;
    }

    public void setExample(String example) {
        Example = example;
    }

    public String getExampleTrans() {
        return ExampleTrans;
    }

    public void setExampleTrans(String exampleTrans) {
        ExampleTrans = exampleTrans;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public Word() {

    }

    protected Word(Parcel in) {
        ID = in.readInt();
        CourseId = in.readInt();
        Name = in.readString();
        Type = in.readString();
        Pronoun = in.readString();
        Meaning = in.readString();
        Example = in.readString();
        ExampleTrans = in.readString();
        Color = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ID);
        dest.writeInt(CourseId);
        dest.writeString(Name);
        dest.writeString(Type);
        dest.writeString(Pronoun);
        dest.writeString(Meaning);
        dest.writeString(Example);
        dest.writeString(ExampleTrans);
        dest.writeString(Color);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Word> CREATOR = new Parcelable.Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
}