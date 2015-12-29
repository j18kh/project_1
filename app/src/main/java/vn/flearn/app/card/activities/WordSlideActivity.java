package vn.flearn.app.card.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewParent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.adapters.CardAdapter;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.Constant;

public class WordSlideActivity extends AppCompatActivity {

    private String title;
    private ActionBar actionBar;
    private ViewPager viewPager;
    private List<Word> words;
    private CardAdapter cardAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_slide);

        initViews();
        setupContents();
        setupActionBar();
        setupViewPager();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.activity_word_slide_viewpager);

    }

    private void setupActionBar() {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setupContents() {
        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.DESCRIPTION);

        words = intent.getParcelableArrayListExtra(Constant.SUBCOURSE_PACKAGE);
        Collections.shuffle(words);
    }

    private void setupViewPager() {
        cardAdapter = new CardAdapter(getSupportFragmentManager(), this, words , true);
        viewPager.setAdapter(cardAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
