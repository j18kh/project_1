package vn.flearn.app.card.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.flearn.app.card.R;
import vn.flearn.app.card.activities.BaseNavigationDrawerActivity;

public class ReviewActivity extends BaseNavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
    }
}
