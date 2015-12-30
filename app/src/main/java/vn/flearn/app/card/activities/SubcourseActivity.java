package vn.flearn.app.card.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.adapters.SubCourseAdapter;
import vn.flearn.app.card.dao.WordDAO;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.DbContext;

public class SubcourseActivity extends BaseNavigationDrawerActivity {

    private int id;
    private List<Word> words;
    private WordDAO wordDAO;
    private SubCourseAdapter adapter;
    private RecyclerView recyclerView;
    private DbContext dbContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initComponents();
    }

    public void initComponents() {
        dbContext = new DbContext(this);

        id = getIntent().getIntExtra(Constant.NAME, -1);

        String title = getIntent().getStringExtra(Constant.DESCRIPTION);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        getWords();
        Log.d("debug", "" + id);

        recyclerView = (RecyclerView) findViewById(R.id.activity_sub_course_recyclerView_sub_courses);
        adapter = new SubCourseAdapter(this, words , title);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Constant.ActivityType getType() {
        return Constant.ActivityType.SUBCOURSE;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWords();
        adapter.resetData(words);
        adapter.notifyDataSetChanged();
    }

    private void getWords() {
        wordDAO = new WordDAO();
        wordDAO.setContext(dbContext);
        words = wordDAO.loadByCourse(id, true);
    }
}
