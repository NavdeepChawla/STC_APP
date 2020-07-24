package com.mstc.mstcapp.fragments;

import android.app.TaskStackBuilder;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.FeedAdapter;
import com.mstc.mstcapp.adapter.highlights.EventAdapter;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.highlights.EventObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedFragment extends Fragment {
    List<FeedObject> feedList;
    RecyclerView recyclerView_feed;
    String base_url = "https://stc-app-backend.herokuapp.com/api/feeds/";
    Retrofit retrofit;
    ProgressBar progressBarFeed;
    FeedAdapter feedAdapter;
    private int skip;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View rootview;
    LinearLayoutManager linearLayoutManager;

    private Boolean isLoading = true;
    private int pastVisibleItem,visisbleitemCount,totalitemCount,prevoiusTotal=0;
    private int view_threshold=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        skip=0;
        pastVisibleItem=0;
        visisbleitemCount=0;
        view_threshold=0;
        totalitemCount=0;
        prevoiusTotal=0;
        isLoading=false;
        sharedPreferences=getContext().getSharedPreferences("feed", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
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

        recyclerView_feed.setLayoutManager(linearLayoutManager);

        if(sharedPreferences.contains("data")){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
            Log.i("SHARED","Yes Data");
            feedList=new ArrayList<>();
            loadShared();
        }
        else{
            Log.i("SHARED","No Data");
            loadData(retrofit);
        }
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.feedSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skip=0;
                        pastVisibleItem=0;
                        visisbleitemCount=0;
                        view_threshold=0;
                        totalitemCount=0;
                        prevoiusTotal=0;
                        isLoading=false;
                        swipeRefreshLayout.setRefreshing(false);
                        Log.i("RETROFIT","Started");
                        loadData(retrofit);
                    }

                }, 3000);
            }
        });
        recyclerView_feed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visisbleitemCount=linearLayoutManager.getChildCount();
                totalitemCount=linearLayoutManager.getItemCount();
                pastVisibleItem=linearLayoutManager.findFirstVisibleItemPosition();
                if(dy>0){
                    if(isLoading){
                        if(totalitemCount>prevoiusTotal){
                            isLoading=false;
                            prevoiusTotal=totalitemCount;
                        }
                    }
                    if(!isLoading && (totalitemCount-visisbleitemCount)<=(pastVisibleItem+view_threshold)){

                        skip+=5;
                        pagination(skip);
                        isLoading=true;
                    }
                }
            }
        });


    }

    private void pagination(int skip) {
        JsonPlaceholderApi jsonPlaceholderapi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<FeedObject>> call= jsonPlaceholderapi.getFeed(base_url+skip);
        call.enqueue(new Callback<List<FeedObject>>() {
            @Override
            public void onResponse(Call<List<FeedObject>> call, Response<List<FeedObject>> response) {
                if(!response.isSuccessful()){
                    if(response.code()==400)
                    {
                        loadData(retrofit);
                        return;
                    }
                    else {
                        Toast.makeText(getContext(), "ErrorCode " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.i("CODE", String.valueOf(response.code()));
                        return;
                    }
                }

                List<FeedObject> feeds=response.body();
                for(FeedObject events1:feeds){

                    String title = events1.getFeedTitle();
                    String desc = events1.getFeedDesc();
                    String link= events1.getFeedLink();
                    String picture=events1.getFeedPicture();
                    feedList.add(new FeedObject(title,desc,link,picture));

                }
                feedAdapter.notifyItemInserted(feedList.size()-1);
                feedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<FeedObject>> call, Throwable t) {
                Log.i("FAILED : ",t.getMessage());
                loadData(retrofit);
            }
        });

    }


    private void loadData(Retrofit retrofit) {

        feedList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderapi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<FeedObject>> call= jsonPlaceholderapi.getFeed(base_url+skip);
        call.enqueue(new Callback<List<FeedObject>>() {
            @Override
            public void onResponse(Call<List<FeedObject>> call, Response<List<FeedObject>> response) {
                if(!response.isSuccessful()){
                    if(response.code()==400)
                    {
                        loadData(retrofit);
                        return;
                    }
                    else {
                        Toast.makeText(getContext(), "ErrorCode " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.i("CODE", String.valueOf(response.code()));
                        return;
                    }
                }

                List<FeedObject> feeds=response.body();
                for(FeedObject events1:feeds){

                    String title = events1.getFeedTitle();
                    String desc = events1.getFeedDesc();
                    String link= events1.getFeedLink();
                    String picture=events1.getFeedPicture();
                    feedList.add(new FeedObject(title,desc,link,picture));

                }
                Gson gson=new Gson();
                String json=gson.toJson(feedList);
                Log.i("JSON",json);
                editor.putString("data",json);
                editor.commit();

                progressBarFeed.setVisibility(View.INVISIBLE);
                feedAdapter=new FeedAdapter(feedList,getContext());
                recyclerView_feed.setAdapter(feedAdapter);
            }

            @Override
            public void onFailure(Call<List<FeedObject>> call, Throwable t) {
                Log.i("FAILED : ",t.getMessage());
                loadData(retrofit);
            }
        });
    }
    private void loadShared(){
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("feed", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        Log.i("GETDATA ",json);

        Type type=new TypeToken<List<FeedObject>>(){}.getType();
        feedList=gson.fromJson(json,type);

        progressBarFeed.setVisibility(View.INVISIBLE);
        FeedAdapter adapter=new FeedAdapter(feedList,getContext());
        recyclerView_feed.setAdapter(adapter);

        //if updates are there
        loadData(retrofit);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}