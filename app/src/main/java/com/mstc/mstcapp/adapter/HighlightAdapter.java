package com.mstc.mstcapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class HighlightAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> highlightFragmentList=new ArrayList<>();
    private final List<String> highlightFragmentListTitle=new ArrayList<>();

    public HighlightAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return highlightFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return highlightFragmentListTitle.get(position);
    }

    @Override
    public int getCount() {
        return highlightFragmentList.size();
    }

    public void addFragment(Fragment fragment,String Title){
        highlightFragmentList.add(fragment);
        highlightFragmentListTitle.add(Title);
    }

}
