package com.mstc.mstcapp.fragments;

import android.app.TaskStackBuilder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.FeedAdapter;
import com.mstc.mstcapp.model.FeedObject;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    List<FeedObject> feedList;
    RecyclerView recyclerView_feed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavActivity.appBar.setElevation(4.0f);
        NavActivity.appBarTitle.setText("HOME");

        recyclerView_feed = (RecyclerView) view.findViewById(R.id.recyclerview_feed);
        feedList = new ArrayList<>();
        feedList.add(new FeedObject( "Did You Know", "This the sample description for post  This is clickable and the insta post opens in new browser", "https://www.instagram.com/mstcvit/",R.drawable.xbox));
        feedList.add(new FeedObject( "Did You Know", "This the sample description for post  This is clickable and the insta post opens in new browser", "https://www.instagram.com/mstcvit/",R.drawable.xbox));
        feedList.add(new FeedObject( "Did You Know", "This the sample description for post  This is clickable and the insta post opens in new browser", "https://www.instagram.com/mstcvit/",R.drawable.xbox));
        feedList.add(new FeedObject( "Did You Know", "This the sample description for post  This is clickable and the insta post opens in new browser", "https://www.instagram.com/mstcvit/",R.drawable.xbox));

        FeedAdapter feedAdapter = new FeedAdapter(feedList, getContext());
        recyclerView_feed.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_feed.setAdapter(feedAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.feedSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }
}