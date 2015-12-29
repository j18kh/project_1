package vn.flearn.app.card;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.flearn.app.card.activities.MenuActivity;
import vn.flearn.app.card.activities.SplashScreenActivity;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;

public class MainActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* If this is the first-time run, then go to splash-screen */
        if (AppUtils.getBooleanPreference(this, Constant.FIRST_RUN, true)) {
            intent = new Intent(this, SplashScreenActivity.class);
        } else {
            /* Else, go to menu */
            intent = new Intent(this, MenuActivity.class);
        }

        startActivity(intent);
        finish();
    }
}