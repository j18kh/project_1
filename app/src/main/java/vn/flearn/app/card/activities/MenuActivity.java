package vn.flearn.app.card.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import vn.flearn.app.card.R;
import vn.flearn.app.card.utils.Constants;

public class MenuActivity extends BaseNavigationDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setItemSelected(Constants.MENU_PACKAGES);
    }
}
