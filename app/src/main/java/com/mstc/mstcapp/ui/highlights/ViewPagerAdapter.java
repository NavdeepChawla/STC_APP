package com.mstc.mstcapp.ui.highlights;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mstc.mstcapp.ui.highlights.event.EventsFragment;
import com.mstc.mstcapp.ui.highlights.github.GithubFragment;
import com.mstc.mstcapp.ui.highlights.project.ProjectFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new ProjectFragment();
        else if (position == 1)
            return new EventsFragment();
        else
            return new GithubFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Project";
        else if (position == 1)
            return "Events";
        else
            return "Github";
    }

}
