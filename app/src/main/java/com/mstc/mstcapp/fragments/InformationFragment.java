package com.mstc.mstcapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;

import java.util.Objects;


public class InformationFragment extends Fragment {


    private Button infoLinkLinkedIn;
    private Button infoLinkFacebook;
    private Button infoLinkMedium;
    private Button infoLinkInstagram;
    private TabHost infoTabHost;
    private Button infoLinkPresident, infoLinkTechHead, infoLinkProjectLead,infoLinkTechMentor,infoLinkMgmtLead;
    private CardView infoPrivacyPolicy;

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
        infoPrivacyPolicy = view.findViewById(R.id.infoPrivacyPolicy);

        infoLinkPresident =view.findViewById(R.id.linkedinPresident);
        infoLinkTechHead =view.findViewById(R.id.linkedinTechHead);
        infoLinkProjectLead =view.findViewById(R.id.linkedinProjectLead);
        infoLinkTechMentor=view.findViewById(R.id.linkedinTechMentor);
        infoLinkMgmtLead = view.findViewById(R.id.linkedinMgmtLead);

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

        infoPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://navdeepchawla.github.io/STC_APP_Privacy_Policy/")));
                startActivity(intent);
            }
        });

        infoLinkPresident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.linkedin.com/in/jatin-mahajan-0869")));
                startActivity(intent);
            }
        });

        infoLinkTechHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.linkedin.com/in/shrey-sindher-0b3008167/")));
                startActivity(intent);
            }
        });


        infoLinkProjectLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.linkedin.com/in/ujjwal-sinha-b8032514b/")));
                startActivity(intent);
            }
        });


        infoLinkTechMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.linkedin.com/in/mukundh-bhushan-akns-101a24156/")));
                startActivity(intent);
            }
        });

        infoLinkMgmtLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData((Uri.parse("https://www.linkedin.com/in/mayank-yadav-528381187/")));
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