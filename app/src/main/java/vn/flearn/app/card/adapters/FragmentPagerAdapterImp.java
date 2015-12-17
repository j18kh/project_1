package vn.flearn.app.card.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hkhoi on 07/12/2015.
 * REUSABLE adpater for viewpager of fragments
 */
public class FragmentPagerAdapterImp extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public FragmentPagerAdapterImp(FragmentManager fm, List<Fragment> mFragments) {
        super(fm);
        this.mFragments = mFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
