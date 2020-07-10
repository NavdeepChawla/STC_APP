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
import com.mstc.mstcapp.adapter.HighlightAdapter;
import com.mstc.mstcapp.fragments.highlights.EventFragment;
import com.mstc.mstcapp.fragments.highlights.GithubFragment;
import com.mstc.mstcapp.fragments.highlights.ProjectFragment;

public class HighlightFragment extends Fragment {

    TabLayout highlightTabLayout;
    ViewPager highlightViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_highlight, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavActivity.appBar.setElevation(0.0f);
        NavActivity.appBarTitle.setText("HIGHLIGHTS");

        highlightViewPager=view.findViewById(R.id.viewPager2);
        highlightTabLayout=view.findViewById(R.id.tabLayout2);
        highlightTabLayout.setupWithViewPager(highlightViewPager);
        highlightViewPager.setOffscreenPageLimit(3);

        HighlightAdapter adapter=new HighlightAdapter(getChildFragmentManager());
        adapter.addFragment(new ProjectFragment(),"Project");
        adapter.addFragment(new EventFragment(),"Event");
        adapter.addFragment(new GithubFragment(),"Github");
        highlightViewPager.setAdapter(adapter);

        highlightViewPager.setPageTransformer(true, new DepthPageTransformer());

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