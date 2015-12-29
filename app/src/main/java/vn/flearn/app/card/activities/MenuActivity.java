package vn.flearn.app.card.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.adapters.CourseAdapter;
import vn.flearn.app.card.dao.CourseDAO;
import vn.flearn.app.card.dao.interfaces.ICourseDAO;
import vn.flearn.app.card.models.Course;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.DbContext;

public class MenuActivity extends BaseNavigationDrawerActivity {

    private List<Course> courses;
    private DbContext dbContext;
    private ICourseDAO courseDAO;
    private RecyclerView recyclerView;
    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItemSelected(Constant.MENU_PACKAGES);

        initComponents();

    }

    private void initComponents() {
        dbContext = new DbContext(this);

        courseDAO = new CourseDAO();
        courseDAO.setContext(dbContext);
        courses = courseDAO.loadAll();

        recyclerView = (RecyclerView) findViewById(R.id.activity_menu_recyclerView_courses);
        adapter = new CourseAdapter(this, courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public Constant.ActivityType getType() {
        return Constant.ActivityType.PACKAGES;
    }
}
