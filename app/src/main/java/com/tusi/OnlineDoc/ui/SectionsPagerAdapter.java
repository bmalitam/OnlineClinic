package com.tusi.OnlineDoc.ui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tusi.OnlineDoc.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.main_screen,R.string.choice_screen,R.string.view_screen};
    private final Context mContext;

    public SectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return mainscreenFragment.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return choicescreenFragment.newInstance(1, "Page # 2");
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return viewscreenFragment.newInstance(2, "Page # 3");
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }


    @Override
    public int getCount() {
        // Show 2 total pages.
//        return 2;
        return NUM_ITEMS;

    }


}
