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
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.FeedAdapter;
import com.mstc.mstcapp.model.FeedObject;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedFragment extends Fragment {

    RecyclerView recyclerView_feed;
    String base_url = "https://stc-app-backend.herokuapp.com/api/feeds/";
    Retrofit retrofit;
    ProgressBar progressBarFeed;
    FeedAdapter feedAdapter;
    TextView feedLoadMore;
    private int skip;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LinearLayoutManager linearLayoutManager;
    TextView internetCheck;
    SwipeRefreshLayout swipeRefreshLayout;

    public FeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        skip=0;
        sharedPreferences= requireContext().getSharedPreferences("feed", Context.MODE_PRIVATE);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavActivity.appBar.setElevation(4.0f);
        NavActivity.appBarTitle.setText("HOME");

        progressBarFeed=view.findViewById(R.id.progressbarFeed);
        recyclerView_feed = (RecyclerView) view.findViewById(R.id.recyclerview_feed);
        feedLoadMore = view.findViewById(R.id.feedLoadMore);
        internetCheck=view.findViewById(R.id.internetcheckFeed);
        feedLoadMore.setVisibility(View.GONE);

        recyclerView_feed.setLayoutManager(linearLayoutManager);

        if(NavActivity.feedList.size()==0)
        {
            loadData(retrofit);
        }
        else {
            feedAdapter = new FeedAdapter(NavActivity.feedList,getContext());
            recyclerView_feed.setAdapter(feedAdapter);
            progressBarFeed.setVisibility(View.GONE);
            if(!NavActivity.sharedFeed)
            {
                feedLoadMore.setVisibility(View.VISIBLE);
                skip = NavActivity.feedList.size();
            }
        }

        //SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.feedSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NavActivity.feedList.clear();
                Objects.requireNonNull(recyclerView_feed.getAdapter()).notifyDataSetChanged();
                skip = 0;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadData(retrofit);
                    }

                });
            }
        });

        feedLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeRefreshLayout.setRefreshing(true);
                loadData(retrofit);
            }
        });
    }

    private void loadData(Retrofit retrofit)
    {
        feedLoadMore.setVisibility(View.GONE);
        JsonPlaceholderApi jsonPlaceholderapi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<FeedObject>> call= jsonPlaceholderapi.getFeed(base_url+skip);
        call.enqueue(new Callback<List<FeedObject>>() {
            @Override
            public void onResponse(@NotNull Call<List<FeedObject>> call, @NotNull Response<List<FeedObject>> response) {
                if(!response.isSuccessful()){
                    if(response.code()==400)
                    {
                        loadData(retrofit);
                    }
                    else {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "ErrorCode " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.i("CODE", String.valueOf(response.code()));
                    }
                    return;
                }

                List<FeedObject> feeds=response.body();
                if(feeds==null||feeds.size()==0){
                    feedLoadMore.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(feedLoadMore,"No More Posts To Show.",Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                }
                else
                {
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

                        recyclerView_feed.setAdapter(feedAdapter);
                    }
                    if(feeds.size()==5)
                    {
                        feedLoadMore.setVisibility(View.VISIBLE);
                    }
                    skip=NavActivity.feedList.size();
                    progressBarFeed.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    feedAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<FeedObject>> call, @NotNull Throwable t) {
                Log.i("FAILED : ", Objects.requireNonNull(t.getMessage()));
                if(sharedPreferences.contains("data")){
                    sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    progressBarFeed.setVisibility(View.INVISIBLE);
                    internetCheck.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadShared(){
        NavActivity.feedList.clear();
        SharedPreferences sharedPreferences= requireContext().getSharedPreferences("feed", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        assert json != null;
        Log.i("GETDATA",json);

        Type type=new TypeToken<List<FeedObject>>(){}.getType();
        NavActivity.feedList=gson.fromJson(json,type);

        swipeRefreshLayout.setRefreshing(false);
        progressBarFeed.setVisibility(View.INVISIBLE);
        feedAdapter=new FeedAdapter(NavActivity.feedList,getContext());
        recyclerView_feed.setAdapter(feedAdapter);

       NavActivity.sharedFeed = true;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}