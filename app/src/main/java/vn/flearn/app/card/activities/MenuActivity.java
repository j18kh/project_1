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
        getDatabaseFromServer();
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
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                /* TODO: Download database  */
                String uuid = PublicFunction.getUUID(activity);
                Map<String, String> params = new HashMap<String, String>();
                JSONObject jo;

                if (PublicFunction.isNetworkConnected(activity)) {
                    AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
                        private List<Course> coursesList,
                                courseFromApi;

                        @Override
                        public void callback(String url, JSONObject json, AjaxStatus status) {
                            coursesList = courses;
                            if (json != null) {
                                if (!json.has(Constant.JSON_TAG_ERROR)) {
                                    Gson gson = new Gson();
                                    try {
                                        courseFromApi = Arrays.asList(gson.fromJson(
                                                json.getString(Constant.JSON_TAG_UCOURSE), Course[].class));
                                        if (courseFromApi.size() > 0) {
                                            for (int i = 0; i < coursesList.size(); i++) {
                                                for (int j = 0; j < courseFromApi.size(); j++) {
                                                    if (coursesList.get(i).getName().equals(courseFromApi.get(j).getName())) {
                                                        coursesList.get(i).setIsActivated(courseFromApi.get(j).getIsActivated());
                                                        courseDAO.saveOrUpdate(coursesList.get(i));
                                                    } else {
                                                        coursesList.get(i).setIsActivated("0");
                                                    }
                                                }
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                        }
                    };

                    params.put("UUID", uuid);
                    jo = new JSONObject(params);

                    aQuery.post(
                            Constant.API_MSERVICE_GET_UCOURSE, jo, JSONObject.class, cb.timeout(3000));

                }

                Log.d("debug", "---UUID = " + uuid);
            }
        }, Constant.WAITING_DURATION);
    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed();
            info.cancel();
            finish();
        } else {
//            Toast.makeText(getApplication(), R.string.press_once_more, Toast.LENGTH_SHORT).show();
            info.show();
            backPressedOnce = true;
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backPressedOnce = false;
                }
            }, 2000);
        }
    }
}
