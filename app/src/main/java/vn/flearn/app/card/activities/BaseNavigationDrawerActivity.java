package vn.flearn.app.card.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;

public class BaseNavigationDrawerActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupLayout();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.base_navigationDrawer);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    /* TODO: Menu item selected */
                    case R.id.menu_navigation_help:
                        navigateHelp();
                        break;
                    case R.id.menu_navigation_packages:
                        navigatePackages();
                        break;
                    case R.id.menu_navigation_learned:
                        navigateWordSlide(true);
                        break;
                    case R.id.menu_navigation_hard:
                        navigateWordSlide(false);
                        break;
                    case R.id.menu_navigation_share:
                        navigateSharingIntent();
                        break;
                    case R.id.menu_navigation_vote:
                        navigateVote();
                        break;
                    case R.id.menu_navigation_community:
                        navigateCommunity();
                        break;
                    case R.id.menu_navigation_feedback:
                        navigateFeedback();
                        break;
                    case R.id.menu_navigation_setting:
                        navigateSetting();
                        break;
                    case R.id.menu_navigation_reset:
                        Toast.makeText(getApplication(), "Reset invoked", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        drawerToggle.syncState();
    }

    private void navigateWordSlide(boolean learned) {
        Intent intent = new Intent(this , WordSlideActivity.class);
        intent.putExtra(Constant.REVIEW, true);
        String value;
        if (learned)
            value = Constant.WORD_COLOR_DONE;
        else
            value = Constant.WORD_COLOR_DIFFICULT;
        List<Word> words = AppUtils.getWords(this, value);
        intent.putParcelableArrayListExtra(Constant.SUBCOURSE_PACKAGE, (ArrayList<Word>) words);
        intent.putExtra(Constant.DESCRIPTION , value);
        startActivity(intent);
    }

    private void setupLayout() {
        Log.d("debug", "---Layout = " + getType());
        switch (getType()) {
            case SUBCOURSE:
                setContentView(R.layout.activity_subcourse);
                Log.d("debug", "---Layout = subcourse");
                break;
            case PACKAGES:
                setContentView(R.layout.activity_menu);
                Log.d("debug", "---Layout = course");
                break;
        }

    }

    private void navigateSetting() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void navigateFeedback() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.feeback_email)});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[FCard - Feedback]");
        startActivity(Intent.createChooser(emailIntent, " Gởi phản hồi"));
        try {
            startActivity(Intent.createChooser(emailIntent, getString(R.string.choose_email_client)));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.email_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateCommunity() {
        String link = getString(R.string.community_link);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(link));
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.browser_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateVote() {
        String link = getResources().getString(R.string.google_play_url) + getPackageName();
        Log.d("debug", "---Link = " + link);
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void navigateHelp() {
        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        startActivity(intent);
    }

    private void navigatePackages() {
        if (getType() != Constant.ActivityType.PACKAGES) {
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void navigateSharingIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.sharing_subject));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharing_text));
        try {
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.send_via)));
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.sharing_app_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    public void setItemSelected(int option) {
        navigationView.getMenu().getItem(option).setChecked(true);
    }

    public Constant.ActivityType getType() {
        return Constant.ActivityType.ERROR;
    }

}
