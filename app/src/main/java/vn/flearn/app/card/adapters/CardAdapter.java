package vn.flearn.app.card.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;
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

    public CardAdapter(FragmentManager fm, Context context, List<Word> words , boolean isReview) {
        super(fm);
        this.context = context;
        this.words = words;
        this.isReview = isReview;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentCard.getInstance(words.get(position) , isReview);
    }

    @Override
    public int getCount() {
        return words.size();
    }
}
