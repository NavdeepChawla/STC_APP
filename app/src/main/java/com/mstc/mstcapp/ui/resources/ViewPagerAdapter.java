package com.mstc.mstcapp.ui.resources;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mstc.mstcapp.ui.resources.articleTab.ArticleTabFragment;
import com.mstc.mstcapp.ui.resources.resourceTab.ResourceTabFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final String domain;

    public ViewPagerAdapter(@NonNull FragmentManager fm, String domain) {
        super(fm);
        this.domain = domain;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new RoadmapTabFragment(domain);
        else if (position == 1)
            return new ResourceTabFragment(domain);
        else
            return new ArticleTabFragment(domain);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Roadmap";
        else if (position == 1)
            return "Resources";
        else
            return "Articles";
    }

}
