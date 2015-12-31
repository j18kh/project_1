package vn.flearn.app.card.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import vn.flearn.app.card.R;
import vn.flearn.app.card.fragments.FragmentCard;
import vn.flearn.app.card.models.Word;

/**
 * Created by hkhoi on 12/29/15.
 */
public class CardAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Word> words;
    private boolean isReview;
    private boolean isDone;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private String subTitle;

    public CardAdapter(FragmentManager fm, Context context, List<Word> words , boolean isReview, boolean isDone
                        , ViewPager viewPager, Toolbar toolbar , String subTitle) {
        super(fm);
        this.context = context;
        this.words = words;
        this.isReview = isReview;
        this.viewPager = viewPager;
        this.toolbar = toolbar;
        this.subTitle = subTitle;
        this.isDone = isDone;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCard.getInstance(words.get(position), isReview, isDone, viewPager, position);
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public int removePage(int position) {
        words.remove(position);
        notifyDataSetChanged();
        if (position == words.size())
            position = position - 1;
        Log.d("debug" , "" + position);
        toolbar.setSubtitle(subTitle + " " + context.getString(R.string.left) + " " + words.size() + context.getString(R.string.word));
        return  position;
    }
}
