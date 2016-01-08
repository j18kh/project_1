package vn.flearn.app.card.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
import vn.flearn.app.card.adapters.CourseAdapter;
import vn.flearn.app.card.dao.CourseDAO;
import vn.flearn.app.card.dao.interfaces.ICourseDAO;
import vn.flearn.app.card.models.Course;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.DbContext;
import vn.flearn.app.card.utils.PublicFunction;

public class MenuActivity extends BaseNavigationDrawerActivity {

    private List<Course> courses;
    private DbContext dbContext;
    private ICourseDAO courseDAO;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;
    protected ProgressDialog progressDialog;
    private AQuery aQuery;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItemSelected(Constant.MENU_PACKAGES);

        initComponents();

    }

    private void initComponents() {
        dbContext = new DbContext(this);
        activity = this;
        aQuery = new AQuery(this);

        courseDAO = new CourseDAO();
        courseDAO.setContext(dbContext);
        courses = courseDAO.loadAll();

        recyclerView = (RecyclerView) findViewById(R.id.activity_menu_recyclerView_courses);
        adapter = new CourseAdapter(this, courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (!AppUtils.getBooleanPreference(this, Constant.DATABASE_DOWNLOADED, false)) {
            getDatabaseFromServer();
        }
    }

    @Override
    public Constant.ActivityType getType() {
        return Constant.ActivityType.PACKAGES;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void getDatabaseFromServer() {
        try {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        PublicFunction.dismissProgresDialog(progressDialog);
                /* TODO: Download database  */
                        String uuid = PublicFunction.getUUID(activity);
                        Log.d("debug", "---UUID = " + uuid);
                        Map<String, String> params = new HashMap<String, String>();
                        JSONObject jo;

                        Log.d("debug", "---PublicFunction.isNetworkConnected");
                        if (PublicFunction.isNetworkConnected(activity)) {
                            Log.d("debug", "---AjaxCallback");
                            AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
                                private List<Course> courseList,
                                        courseFromApi;

                                @Override
                                public void callback(String url, JSONObject json, AjaxStatus status) {
                                    courseList = courses;
                                    if (json != null) {
                                        if (!json.has(Constant.JSON_TAG_ERROR)) {
                                            Log.d("debug", "---GSON");
                                            Gson gson = new Gson();
                                            try {
                                                courseFromApi = Arrays.asList(gson.fromJson(
                                                        json.getString(Constant.JSON_TAG_UCOURSE), Course[].class));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }

                                }
                            };

                            params.put("UUID", uuid);
                            jo = new JSONObject(params);

                            Log.d("debug", "---aQuery");
                            aQuery.post(
                                    Constant.API_MSERVICE_GET_UCOURSE, jo, JSONObject.class, cb.timeout(3000));
                            Log.d("debug", "--FINISHED");

                        }

                    } catch (Exception e) {
                        AppUtils.setBooleanPreference(activity, Constant.DATABASE_DOWNLOADED, false);
                        Log.d("debug", "---DOWNLOAD = failed" + e.getMessage());
                    }
                }
            }, Constant.WAITING_DURATION);
        } catch (Exception e) {
            AppUtils.setBooleanPreference(activity, Constant.DATABASE_DOWNLOADED, false);
            Log.d("debug", "---DOWNLOAD = failed" + e.getMessage());
        }
        AppUtils.setBooleanPreference(activity, Constant.DATABASE_DOWNLOADED, true);
        Log.d("debug", "---DOWNLOAD = success!");

    }
}
