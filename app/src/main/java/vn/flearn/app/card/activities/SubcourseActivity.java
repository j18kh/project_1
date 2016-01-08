package vn.flearn.app.card.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.flearn.app.card.R;
import vn.flearn.app.card.adapters.SubCourseAdapter;
import vn.flearn.app.card.dao.WordDAO;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.DbContext;
import vn.flearn.app.card.utils.PublicFunction;

public class SubcourseActivity extends BaseNavigationDrawerActivity {

    private int id;
    private List<Word> words;
    private WordDAO wordDAO;
    private SubCourseAdapter adapter;
    private RecyclerView recyclerView;
    private DbContext dbContext;
    private String title;
    private Activity activity;
    private AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initComponents();
    }

    public void initComponents() {
        activity = this;
        dbContext = new DbContext(this);
        id = getIntent().getIntExtra(Constant.NAME, -1);
        title = getIntent().getStringExtra(Constant.DESCRIPTION);
        aQuery = new AQuery(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);

        getDatabaseFromServer();

        getWords();
        recyclerView = (RecyclerView) findViewById(R.id.activity_sub_course_recyclerView_sub_courses);
        Log.d("debug", "" + id);

        adapter = new SubCourseAdapter(this, words, title);
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
        refresh();
    }

    private void refresh() {
        getWords();
        adapter.resetData(words);
        adapter.notifyDataSetChanged();
    }

    private void getWords() {
        wordDAO = new WordDAO();
        wordDAO.setContext(dbContext);
        words = wordDAO.loadByCourse(id, true);
    }

    private void getDatabaseFromServer() {
        if (id > 0) {

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    wordDAO.setContext(dbContext);
                    words = wordDAO.loadByCourse(id, true);

                    if (words.size() <= 0) {
                        AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
                            @Override
                            public void callback(String url, JSONObject json, AjaxStatus status) {
                                if (json != null) {
                                    if (!json.has(Constant.JSON_TAG_ERROR)) {
                                        Gson gson = new Gson();
                                        try {
                                            List<Word> words = Arrays.asList(gson.fromJson(
                                                    json.getString(Constant.JSON_TAG_UWORDS), Word[].class));
                                            if (words != null && words.size() > 0) {

                                                wordDAO.setContext(dbContext);
                                                Word word;
                                                for (int i = 0; i < words.size(); i++) {
                                                    word = words.get(i);
                                                    wordDAO.saveOrUpdate(word);
                                                }
                                            }
//                                            lvContent = ViewHelper.findView(mView, R.id.listView);
//                                            subCourse = new SubCourseAdapter(mActivity, words, bundle);
//                                            lvContent.setAdapter(subCourse);
//                                            PublicFunction.dismissProgresDialog(mProgressDialog);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        };
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("courseId", id + "");
                        JSONObject jo = new JSONObject(params);

                        aQuery.post(
                                Constant.API_MSERVICE_GET_WORD, jo, JSONObject.class, cb);
                    }
                    /* TODO: Refresh that goddamn page!  */
                    Toast.makeText(activity, "Done downloading!", Toast.LENGTH_SHORT).show();
                }
            }, Constant.WAITING_DURATION);
        }
    }
}
