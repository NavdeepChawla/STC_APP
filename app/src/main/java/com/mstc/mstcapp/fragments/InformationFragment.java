package com.mstc.mstcapp.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;

import java.util.Objects;


public class InformationFragment extends Fragment {


    private Button infoLinkLinkedIn;
    private Button infoLinkFacebook;
    private Button infoLinkMedium;
    private Button infoLinkInstagram;
   private TabHost infoTabHost;

    public InformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavActivity.appBar.setElevation(4.0f);
        NavActivity.appBarTitle.setText("INFORMATION");

        findViewById(view);
        onClickListener();

        setUpTabHost();
        onTabChangedListener();

    }

    private void findViewById(View view)
    {
        infoLinkFacebook = view.findViewById(R.id.button_facebook);
        infoLinkInstagram = view.findViewById(R.id.button_instagram);
        infoLinkMedium = view.findViewById(R.id.button_medium);
        infoLinkLinkedIn = view.findViewById(R.id.button_linkedin);
        infoTabHost = view.findViewById(R.id.infoTabHost);
    }

    private void onClickListener()
    {
        infoLinkInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.instagram.com/mstcvit/")));
                startActivity(intent);
            }
        });

        infoLinkLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.linkedin.com/company/micvitvellore/")));
                startActivity(intent);
            }
        });

        infoLinkFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.facebook.com/mstcvit/")));
                startActivity(intent);
            }
        });

        infoLinkMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://medium.com/student-technical-community-vit-vellore")));
                startActivity(intent);
            }
        });
    }

    private void setUpTabHost()
    {
        infoTabHost.setup();
        TabHost.TabSpec spec = infoTabHost.newTabSpec("TAB ONE");
        spec.setContent(R.id.infoTab1);
        spec.setIndicator("About STC");
        infoTabHost.addTab(spec);

        spec = infoTabHost.newTabSpec("TAB TWO");
        spec.setContent(R.id.infoTab2);
        spec.setIndicator("STC Board");
        infoTabHost.addTab(spec);

        int tab = infoTabHost.getCurrentTab();
        for (int i = 0; i < infoTabHost.getTabWidget().getChildCount(); i++) {
            // When tab is not selected
            infoTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
            TextView tv = infoTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.black));
            tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
        }
        // When tab is selected
        if(Objects.equals(infoTabHost.getCurrentTabTag(), "TAB ONE"))
        {
            infoTabHost.getTabWidget().getChildAt(infoTabHost.getCurrentTab()).setBackground(getResources().getDrawable(R.drawable.tab_left_select));
        }
        else
        {
            infoTabHost.getTabWidget().getChildAt(infoTabHost.getCurrentTab()).setBackground(getResources().getDrawable(R.drawable.tab_right_select));
        }
        infoTabHost.getTabWidget().getChildAt(infoTabHost.getCurrentTab()).setElevation(4.0f);
        TextView tv = infoTabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
        tv.setTextColor(getResources().getColor(R.color.permWhite));
        tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
    }

    private void onTabChangedListener()
    {
        infoTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int tab = infoTabHost.getCurrentTab();
                for (int i = 0; i < infoTabHost.getTabWidget().getChildCount(); i++) {
                    // When tab is not selected
                    infoTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
                    TextView tv = infoTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
                }
                // When tab is selected
                if(Objects.equals(infoTabHost.getCurrentTabTag(), "TAB ONE"))
                {
                    infoTabHost.getTabWidget().getChildAt(infoTabHost.getCurrentTab()).setBackground(getResources().getDrawable(R.drawable.tab_left_select));
                }
                else
                {
                    infoTabHost.getTabWidget().getChildAt(infoTabHost.getCurrentTab()).setBackground(getResources().getDrawable(R.drawable.tab_right_select));
                }
                infoTabHost.getTabWidget().getChildAt(infoTabHost.getCurrentTab()).setElevation(4.0f);
                TextView tv = infoTabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
                tv.setTextColor(getResources().getColor(R.color.permWhite));
                tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
            }
        });
    }
}