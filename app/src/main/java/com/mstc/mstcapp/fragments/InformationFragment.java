package com.mstc.mstcapp.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.util.Utils;

import java.util.Objects;

import static android.content.Context.WINDOW_SERVICE;


public class InformationFragment extends Fragment {

    //Social Media Buttons
    private Button infoLinkLinkedIn;
    private Button infoLinkFacebook;
    private Button infoLinkMedium;
    private Button infoLinkInstagram;
    private CardView infoPrivacyPolicy;

    private TabHost infoTabHost;

    //Board LinkedInButton
    private Button infoLinkPresident, infoLinkTechHead, infoLinkMgmtHead, infoLinkProjectLeadOne,infoLinkTechMentorOne,infoLinkOperationLead;
    private Button infoLinkPRLead, infoLinkDesignLead, infoLinkWebLead, infoLinkProjectLeadTwo,infoLinkTechMentorTwo,infoLinkVideoLead;

    private ImageView infoImagePresident, infoImageTechHead, infoImageMgmtHead, infoImageProjectLeadOne,infoImageTechMentorOne,infoImageOperationLead;
    private ImageView infoImagePRLead, infoImageDesignLead, infoImageWebLead, infoImageProjectLeadTwo,infoImageTechMentorTwo,infoImageVideoLead;

    public InformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        adjustFontScale(getResources().getConfiguration());
        return inflater.inflate(R.layout.fragment_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViewById(view);
        onClickListener();

        setUpTabHost();
        onTabChangedListener();

    }

    private void findViewById(View view) {
        infoLinkFacebook = view.findViewById(R.id.button_facebook);
        infoLinkInstagram = view.findViewById(R.id.button_instagram);
        infoLinkMedium = view.findViewById(R.id.button_medium);
        infoLinkLinkedIn = view.findViewById(R.id.button_linkedin);

        infoTabHost = view.findViewById(R.id.infoTabHost);
        infoPrivacyPolicy = view.findViewById(R.id.infoPrivacyPolicy);

        infoLinkPresident = view.findViewById(R.id.linkedinPresident);
        infoLinkTechHead = view.findViewById(R.id.linkedinTechHead);
        infoLinkMgmtHead = view.findViewById(R.id.linkedinMgmtHead);
        infoLinkProjectLeadOne = view.findViewById(R.id.linkedinProjectLeadOne);
        infoLinkProjectLeadTwo = view.findViewById(R.id.linkedinProjectLeadTwo);
        infoLinkDesignLead = view.findViewById(R.id.linkedinDesignLead);
        infoLinkWebLead = view.findViewById(R.id.linkedinWebLead);
        infoLinkPRLead = view.findViewById(R.id.linkedinPRLead);
        infoLinkOperationLead = view.findViewById(R.id.linkedinOperationLead);
        infoLinkVideoLead = view.findViewById(R.id.linkedinVideoLead);
        infoLinkTechMentorOne=view.findViewById(R.id.linkedinTechMentorOne);
        infoLinkTechMentorTwo=view.findViewById(R.id.linkedinTechMentorTwo);

        infoImagePresident = view.findViewById(R.id.infoImagePresident);
        infoImageTechHead = view.findViewById(R.id.infoImageTechHead);
        infoImageMgmtHead = view.findViewById(R.id.infoImageMgmtHead);
        infoImageProjectLeadOne = view.findViewById(R.id.infoImageProjectLeadOne);
        infoImageProjectLeadTwo = view.findViewById(R.id.infoImageProjectLeadTwo);
        infoImageDesignLead = view.findViewById(R.id.infoImageDesignLead);
        infoImageWebLead = view.findViewById(R.id.infoImageWebLead);
        infoImagePRLead = view.findViewById(R.id.infoImagePRLead);
        infoImageOperationLead = view.findViewById(R.id.infoImageOperationLead);
        infoImageVideoLead = view.findViewById(R.id.infoImageVideoLead);
        infoImageTechMentorOne=view.findViewById(R.id.infoImageTechMentorOne);
        infoImageTechMentorTwo=view.findViewById(R.id.infoImageTechMentorTwo);

    }

    private void onClickListener() {

        //SOCIAL MEDIA ON CLICK LISTENER

        infoLinkInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.INSTAGRAM_URL);
            }
        });

        infoLinkLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_URL);
            }
        });

        infoLinkFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.FACEBOOK_URL);
            }
        });

        infoLinkMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.MEDIUM_URL);
            }
        });

        infoPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.PRIVACY_URL);
            }
        });

        //BOARD LINKEDIN ON CLICK LISTENER

        infoLinkPresident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_PRESIDENT_URL);
            }
        });

        infoLinkTechHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_VP_TECH_URL);
            }
        });

        infoLinkMgmtHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_VP_MGMT_URL);
            }
        });

        infoLinkProjectLeadOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_PROJECT_LEAD_ONE_URL);
            }
        });

        infoLinkProjectLeadTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_PROJECT_LEAD_TWO_URL);
            }
        });

        infoLinkDesignLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_DESIGN_LEAD_URL);
            }
        });

        infoLinkWebLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_WEB_LEAD_URL);
            }
        });

        infoLinkPRLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_PR_LEAD_URL);
            }
        });

        infoLinkOperationLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_OPERATION_LEAD_URL);
            }
        });

        infoLinkVideoLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_VIDEO_LEAD);
            }
        });

        infoLinkTechMentorOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_TECH_MENTOR_ONE_URL);
            }
        });

        infoLinkTechMentorTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlIntent(Utils.LINKEDIN_TECH_MENTOR_TWO_URL);
            }
        });

    }

    private void urlIntent(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData((Uri.parse(url)));
        startActivity(intent);
    }

    /*
    private void setImage(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageTechHead.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_VP_TECH);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageTechHead.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageMgmtHead.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_VP_MGMT);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageTechHead.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageProjectLeadOne.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_PROJECT_LEAD_ONE);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageProjectLeadOne.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageProjectLeadTwo.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_PROJECT_LEAD_TWO);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageProjectLeadTwo.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageDesignLead.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_DESIGN_LEAD);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageDesignLead.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageWebLead.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_WEB_LEAD);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageWebLead.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImagePRLead.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_PR_LEAD);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImagePRLead.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageOperationLead.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_OPERATION_LEAD);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageOperationLead.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageVideoLead.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_VIDEO_LEAD);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageVideoLead.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageTechMentorOne.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_TECH_MENTOR_ONE);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageTechMentorOne.setImageBitmap(picture);
                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                infoImageTechMentorTwo.post(new Runnable() {
                    @Override
                    public void run() {
                        String pic= getString(R.string.IMAGE_TECH_MENTOR_TWO);
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        infoImageTechMentorTwo.setImageBitmap(picture);
                    }
                });
            }
        }).start();

    }
     */

    private void setUpTabHost() {

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
            //tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
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
        //tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
    }

    private void onTabChangedListener() {

        infoTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int tab = infoTabHost.getCurrentTab();
                for (int i = 0; i < infoTabHost.getTabWidget().getChildCount(); i++) {
                    // When tab is not selected
                    infoTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
                    TextView tv = infoTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(getResources().getColor(R.color.black));
                    //tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
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
                //tv.setTextSize(getResources().getDimension(R.dimen.tabHost));
            }
        });

    }

    public void adjustFontScale(Configuration configuration) {
        configuration.fontScale = (float) 1;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) requireContext().getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getResources().updateConfiguration(configuration, metrics);
    }

}