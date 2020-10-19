package com.mstc.mstcapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.FeedAdapter;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedFragment extends Fragment {

    //Views
    private RecyclerView recyclerViewFeed;
    private TextView internetCheck;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBarFeed;

    //Adapter
    private FeedAdapter feedAdapter;
    private LinearLayoutManager linearLayoutManager;

    //SharedPreference
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //Network
    private Retrofit retrofit;

    //Check Variables
    private int skip;

    public FeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Network Initialization
        retrofit=new Retrofit.Builder()
                .baseUrl(Utils.FEED_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Check variable Initialization
        skip=0;
        NavActivity.loadData=true;

        //SharedPreference Initialization
        sharedPreferences= requireContext().getSharedPreferences("feed", Context.MODE_PRIVATE);

        //Adapter Initialization
        linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        feedAdapter = new FeedAdapter(NavActivity.feedList,getContext());

        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Connect Views to Layout
        findViewById(view);

        //Connect RecyclerView to Adapter
        recyclerViewFeed.setAdapter(feedAdapter);
        recyclerViewFeed.setLayoutManager(linearLayoutManager);

        //First Load
        if(NavActivity.feedList.size()==0) {
            NavActivity.loadData=true;
            skip=0;
            loadData(retrofit);
        }
        //Not First Load
        else {
            progressBarFeed.setVisibility(View.GONE);
            if(!NavActivity.sharedFeed) {
                skip = NavActivity.feedList.size();
            }
        }

        setSwipeRefreshLayout();
        loadOnScroll();
    }

    private void findViewById(View view){
        progressBarFeed=view.findViewById(R.id.progressbarFeed);
        recyclerViewFeed = (RecyclerView) view.findViewById(R.id.recyclerview_feed);
        internetCheck=view.findViewById(R.id.internetcheckFeed);
        swipeRefreshLayout = view.findViewById(R.id.feedSwipeRefresh);
    }

    private void setSwipeRefreshLayout(){
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(progressBarFeed.getVisibility()==View.GONE)
                {
                    NavActivity.feedList.clear();
                    if(recyclerViewFeed.getAdapter()!=null)
                    {
                        Objects.requireNonNull(recyclerViewFeed.getAdapter()).notifyDataSetChanged();
                    }
                    skip = 0;
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            loadData(retrofit);
                        }
                    });
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void loadOnScroll(){
        recyclerViewFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==RecyclerView.SCROLL_STATE_IDLE
                        &&!NavActivity.sharedFeed
                        &&!swipeRefreshLayout.isRefreshing()
                        &&NavActivity.loadData){
                            if(!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)){
                                swipeRefreshLayout.setRefreshing(true);
                                loadData(retrofit);
                            }
                }
            }
        });
    }

    private void loadData(Retrofit retrofit) {

        JsonPlaceholderApi jsonPlaceholderapi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<FeedObject>> call= jsonPlaceholderapi.getFeed(Utils.FEED_URL +skip);

        call.enqueue(new Callback<List<FeedObject>>() {
            @Override
            public void onResponse(@NotNull Call<List<FeedObject>> call, @NotNull Response<List<FeedObject>> response) {

                //Unsuccessful Response
                if(!response.isSuccessful()){
                    swipeRefreshLayout.setRefreshing(false);
                    progressBarFeed.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                    Snackbar.make(recyclerViewFeed,"ErrorCode " + response.code(),Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                    Log.i("CODE", String.valueOf(response.code()));
                }
                else
                {
                    List<FeedObject> feeds=response.body();
                    if(feeds==null||feeds.size()==0){
                        swipeRefreshLayout.setRefreshing(false);
                        NavActivity.loadData=false;
                        Snackbar.make(recyclerViewFeed,"No More Posts To Show.",Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                    }
                    else
                    {
                        NavActivity.sharedFeed=false;
                        if(skip==0)
                        {
                            NavActivity.feedList.clear();
                        }
                        for(FeedObject feed:feeds){
                            String title = feed.getFeedTitle();
                            String desc = feed.getFeedDesc();
                            String link= feed.getFeedLink();
                            String picture=feed.getFeedPicture();
                            NavActivity.feedList.add(new FeedObject(title,desc,link,picture));
                        }
                        if(skip==0)
                        {
                            feedAdapter=new FeedAdapter(NavActivity.feedList,getContext());
                            Gson gson=new Gson();
                            String json=gson.toJson(NavActivity.feedList);
                            Log.i("JSON",json);

                            editor= sharedPreferences.edit();
                            editor.putString("data",json);
                            editor.apply();

                            recyclerViewFeed.setAdapter(feedAdapter);
                        }
                        skip=NavActivity.feedList.size();
                        progressBarFeed.setVisibility(View.GONE);
                        internetCheck.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        feedAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<FeedObject>> call, @NotNull Throwable t) {
                Log.i("FAILED : ", Objects.requireNonNull(t.getMessage()));
                if(sharedPreferences.contains("data")){
                    if(!NavActivity.sharedFeed){
                        Log.i("SHARED","Yes Data");
                        loadShared();
                    }
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    progressBarFeed.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadShared(){
        NavActivity.feedList.clear();
        if(getContext()!=null)
        {
            Gson gson=new Gson();
            String json=sharedPreferences.getString("data",null);
            if(json!=null)
            {
                Log.i("GETDATA",json);
                NavActivity.feedList.clear();
                Type type=new TypeToken<List<FeedObject>>(){}.getType();
                NavActivity.feedList=gson.fromJson(json,type);
                internetCheck.setVisibility(View.GONE);
                feedAdapter=new FeedAdapter(NavActivity.feedList,getContext());
                recyclerViewFeed.setAdapter(feedAdapter);
                internetCheck.setVisibility(View.GONE);
            }
            else
            {
                internetCheck.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            internetCheck.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
        progressBarFeed.setVisibility(View.GONE);
        NavActivity.sharedFeed = true;
    }

}