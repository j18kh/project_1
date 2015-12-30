package vn.flearn.app.card.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import vn.flearn.app.card.fragments.FragmentCard;
import vn.flearn.app.card.models.Word;

/**
 * Created by hkhoi on 12/29/15.
 */
public class CardAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Word> words;
    private boolean isReview;
    private ViewPager viewPager;

    public CardAdapter(FragmentManager fm, Context context, List<Word> words , boolean isReview
                        , ViewPager viewPager) {
        super(fm);
        this.context = context;
        this.words = words;
        this.isReview = isReview;
        this.viewPager = viewPager;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCard.getInstance(words.get(position), isReview, viewPager, position);
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public int getItemPosition(Object object) {
        int position = 0;
        for (ListView.FixedViewInfo iView : _views) {

        }
    }

    public int removePage(int position) {
        words.remove(position);
        notifyDataSetChanged    ();
        if (position == words.size())
            position = position - 1;
        Log.d("debug" , "" + position);
        return  position;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
