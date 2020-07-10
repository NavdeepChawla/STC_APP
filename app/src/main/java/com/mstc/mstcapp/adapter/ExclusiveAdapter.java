package com.mstc.mstcapp.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExclusiveAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> exclusiveFragmentList=new ArrayList<>();
    private final List<String> exclusiveFragmentListTitle=new ArrayList<>();

    public ExclusiveAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return exclusiveFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return exclusiveFragmentListTitle.get(position);
    }

    @Override
    public int getCount() {
        return exclusiveFragmentList.size();
    }

    public void addFragment(Fragment fragment,String Title){
        exclusiveFragmentList.add(fragment);
        exclusiveFragmentListTitle.add(Title);
    }

}
