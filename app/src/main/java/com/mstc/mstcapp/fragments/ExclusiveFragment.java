package com.mstc.mstcapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.ExclusiveAdapter;
import com.mstc.mstcapp.fragments.exclusives.AttendanceFragment;
import com.mstc.mstcapp.fragments.exclusives.MomFragment;
import com.mstc.mstcapp.fragments.exclusives.UpdatesFragment;
import com.mstc.mstcapp.fragments.highlights.EventFragment;
import com.mstc.mstcapp.fragments.highlights.GithubFragment;
import com.mstc.mstcapp.fragments.highlights.ProjectFragment;

public class ExclusiveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exclusive, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavActivity.appBar.setElevation(0.0f);
        NavActivity.appBarTitle.setText("EXCLUSIVE");

        ViewPager exclusiveViewPager = view.findViewById(R.id.exclusiveViewPager);
        TabLayout exclusiveTabLayout = view.findViewById(R.id.exclusiveTabLayout);

        exclusiveTabLayout.setupWithViewPager(exclusiveViewPager);
        exclusiveViewPager.setOffscreenPageLimit(3);

        ExclusiveAdapter adapter=new ExclusiveAdapter(getChildFragmentManager());
        adapter.addFragment(new MomFragment(),"MOM");
        adapter.addFragment(new AttendanceFragment(),"Attendance");
        adapter.addFragment(new UpdatesFragment(),"Updates");
        exclusiveViewPager.setAdapter(adapter);

        exclusiveViewPager.setPageTransformer(true, new HighlightFragment.DepthPageTransformer());
    }

    public static class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
    
}