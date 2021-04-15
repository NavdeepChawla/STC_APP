package com.mstc.mstcapp.ui.exclusive;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mstc.mstcapp.ui.exclusive.attendance.AttendanceFragment;
import com.mstc.mstcapp.ui.exclusive.mom.MOMFragment;
import com.mstc.mstcapp.ui.exclusive.updates.UpdatesFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0)
            fragment = new MOMFragment();
        else if (position == 1)
            fragment = new AttendanceFragment();
        else
            fragment = new UpdatesFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
            title = "MOM";
        else if (position == 1)
            title = "Attendance";
        else if (position == 2)
            title = "Updates";
        return title;
    }

}
