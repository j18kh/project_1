package vn.flearn.app.card.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.fragments.WordFragment;
import vn.flearn.app.card.models.Word;

/**
 * Created by huytr on 03-01-2016.
 */
public class WordSlideAdapter extends FragmentStatePagerAdapter {

    private List<Word> words;
    private boolean isReview;
    private ViewPager viewPager;
    private TextToSpeech textToSpeech;
    private boolean isDone;
    private Toolbar toolbar;
    private String subTitle;
    private Context context;

    public WordSlideAdapter(FragmentManager fm ,
                            List<Word> words ,
                            boolean isReview ,
                            ViewPager viewPager,
                            TextToSpeech textToSpeech,
                            boolean isDone,
                            Toolbar toolbar,
                            String subTitle ,
                            Context context) {
        super(fm);
        this.words = words;
        this.isReview = isReview;
        this.viewPager = viewPager;
        this.textToSpeech = textToSpeech;
        this.isDone = isDone;
        this.toolbar = toolbar;
        this.subTitle = subTitle;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        return WordFragment.getInstance(this, words.get(position), isReview);
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void removePage() {
        int index = viewPager.getCurrentItem();
        words.remove(index);
        notifyDataSetChanged();
        toolbar.setSubtitle(subTitle + " " + context.getString(R.string.left) + " " + words.size() + context.getString(R.string.word));
    }

    public TextToSpeech getTextToSpeech() {
        return textToSpeech;
    }

    public boolean getIsDone() {
        return isDone;
    }

}
