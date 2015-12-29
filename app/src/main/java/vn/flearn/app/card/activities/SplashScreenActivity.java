package vn.flearn.app.card.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.adapters.FragmentPagerAdapterImp;
import vn.flearn.app.card.fragments.Help1Fragment;
import vn.flearn.app.card.fragments.Help2Fragment;
import vn.flearn.app.card.fragments.Help3Fragment;
import vn.flearn.app.card.fragments.Help4Fragment;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;

public class SplashScreenActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<Fragment> fragments;
    private FragmentPagerAdapterImp fragmentPagerAdapterImp;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        initViews();
    }

    /**
     * Initialize all views
     */
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.activity_splashScreen_viewPager_screen);
        initFragments();
        initAdapter();
        viewPager.setAdapter(fragmentPagerAdapterImp);
//        setViewPagerTransformer();
    }

    /**
     * Initialize adapter for the viewpager
     */
    private void initAdapter() {
        fragmentPagerAdapterImp =
                new FragmentPagerAdapterImp(getSupportFragmentManager(), fragments);
    }

    /**
     * Put all fragments in the list of fragments
     * TODO: Add more splash-screens here
     */
    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(new Help1Fragment());
        fragments.add(new Help2Fragment());
        fragments.add(new Help3Fragment());
        fragments.add(new Help4Fragment());
    }

    public void SplashScreenOnclick(View view) {

        if (AppUtils.getBooleanPreference(this, Constant.FIRST_RUN, true)) {
            intent = new Intent(this, MenuActivity.class);
            Log.d("SplashScreenActivity", "Intent assigned");

            Log.d("SplashScreenActivity", "Start activity");
            startActivity(intent);
        }

        Log.d("SplashScreenActivity", "Finish");
        AppUtils.setBooleanPreference(this, Constant.FIRST_RUN, false);
        finish();
    }
}

