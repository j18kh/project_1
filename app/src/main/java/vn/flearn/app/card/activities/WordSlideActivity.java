package vn.flearn.app.card.activities;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import vn.flearn.app.card.R;
import vn.flearn.app.card.adapters.CardAdapter;
import vn.flearn.app.card.adapters.WordSlideAdapter;
import vn.flearn.app.card.models.Word;
import vn.flearn.app.card.utils.AdToastListener;
import vn.flearn.app.card.utils.AppUtils;
import vn.flearn.app.card.utils.Constant;
import vn.flearn.app.card.utils.ShakeDetector;
import vn.flearn.app.card.utils.SoundManager;

public class WordSlideActivity extends AppCompatActivity {

    private String title, subTitle;
    private ActionBar actionBar;
    private ViewPager viewPager;
    private List<Word> words;
    private WordSlideAdapter adapter;
    private TextToSpeech textToSpeech;
    private Toolbar toolbar;
    private boolean isReview;
    private boolean isDone;
    private ShakeDetector shakeDetector;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_slide);

        initComponents();
        setupContents();
        setupActionBar();
        setupViewPager();
        setupAds();
    }

    private void setupAds() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();

                adView.setAdListener(new AdToastListener(getApplicationContext()));
                adView.loadAd(adRequest);
            }
        } , 5000);
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.activity_word_slide_viewpager);
        adView = (AdView) findViewById(R.id.adView);

    }

    private void setupActionBar() {
        toolbar.setTitle(title);
        /*TODO: Add subtitle*/
        toolbar.setSubtitle(subTitle + " " + getString(R.string.left) + " " + words.size() + getString(R.string.word));
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setupContents() {
        Intent intent = getIntent();
        title = intent.getStringExtra(Constant.DESCRIPTION);
        subTitle = intent.getStringExtra(Constant.SUBTITLE);
        isReview = intent.getBooleanExtra(Constant.REVIEW, false);
        isDone = intent.getBooleanExtra(Constant.DONE, true);
        textToSpeech = new TextToSpeech(getApplication(), new TextToSpeech.OnInitListener() {
            /* TODO: Set up text to speech engine */
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                } else if (status == TextToSpeech.ERROR_NETWORK) {
                    Toast.makeText(getApplication(), "Không có Internet!", Toast.LENGTH_SHORT).show();
                } else if (status == TextToSpeech.ERROR) {
                    Toast.makeText(getApplication(), R.string.error_tts, Toast.LENGTH_SHORT).show();
                }
            }
        });

        words = intent.getParcelableArrayListExtra(Constant.SUBCOURSE_PACKAGE);
        if (words.isEmpty()) {
            Toast.makeText(this, R.string.finish, Toast.LENGTH_LONG).show();
        }
        Collections.shuffle(words);

    }

    private void scrambleWords() {
        Toast.makeText(getApplication(), "Các từ đã được sáo trộn", Toast.LENGTH_SHORT).show();
        Collections.shuffle(words);
        try {
            viewPager.getAdapter().notifyDataSetChanged();
        } catch (IllegalStateException e) {
            Log.d("debug", "---Exception!!" + e.getMessage());
        }
    }

    private void setupViewPager() {
        adapter = new WordSlideAdapter(getSupportFragmentManager(), words, isReview, viewPager, textToSpeech, isDone, toolbar, subTitle, this);
        viewPager.setAdapter(adapter);
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        initShakeDetector();
    }

    private void initShakeDetector() {
        if (shakeDetector == null) {
            shakeDetector = new ShakeDetector(this);
            shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
                @Override
                public void onShake() {
                    if (AppUtils.getBooleanPreference(getApplicationContext(), Constant.SOUND, true)) {
                        SoundManager sound = SoundManager.getInstance(getApplication());
                        sound.play(R.raw.shuffling_cards);
                    }
                    scrambleWords();
                }
            });
        } else {
            shakeDetector.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        shakeDetector.pause();
    }
}
