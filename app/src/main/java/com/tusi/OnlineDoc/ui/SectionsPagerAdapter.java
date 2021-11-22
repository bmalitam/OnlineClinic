package com.tusi.OnlineDoc.ui;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.MutableLiveData;
import com.tusi.OnlineDoc.viewholder.usertype;

import com.tusi.OnlineDoc.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {
    public static final String ANONYMOUS = "anonymous";
    private static int NUM_ITEMS;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.main_screen,R.string.choice_screen,R.string.view_screen};
    private static final int[] TAB_TITLES_Patient = new int[]{R.string.patient_profile,R.string.follow_clinic,R.string.my_conditions,R.string.clinic_followed};
    private static final int[] TAB_TITLES_clinic = new int[]{R.string.clinic_profile,R.string.patient_following_me};
    private final Context mContext;
    static usertype usrtpe;
    MutableLiveData<String> usrtyp = new MutableLiveData<>();

    public SectionsPagerAdapter(FragmentManager fm, Context context,usertype userType) {
        super(fm);
        usrtpe = userType;


        if(usrtpe.getUser().contains("patient"))
        {
            NUM_ITEMS = 4;
        }
        else
        {
            NUM_ITEMS = 2;
        }
        mContext = context;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        if(usrtpe.getUser().contains("patient"))
        {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return mainscreenFragment.newInstance(0, "Page # 1",usrtpe);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return choicescreenFragment.newInstance(1, "Page # 2", usrtpe);
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return viewscreenFragment.newInstance(2, "Page # 3", usrtpe);
                case 3: // Fragment # 0 - This will show FirstFragment different title
                    return clinicfollowedscreenFragment.newInstance(3, "Page # 4", usrtpe);
                default:
                    return null;
            }
        }
        else
        {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return mainscreenFragment.newInstance(0, "Page # 1",usrtpe);
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return viewscreenFragment.newInstance(1, "Page # 2", usrtpe);
                default:
                    return null;
            }
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(usrtpe.getUser().contains("patient"))
        {
            return mContext.getResources().getString(TAB_TITLES_Patient[position]);
        }
        else
        {
            return mContext.getResources().getString(TAB_TITLES_clinic[position]);
        }

    }


    @Override
    public int getCount() {
        // Show 2 total pages.
//        return 2;
        return NUM_ITEMS;

    }


}
