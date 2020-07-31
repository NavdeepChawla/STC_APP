package com.mstc.mstcapp.fragments.highlights;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.highlights.GithubAdapter;
import com.mstc.mstcapp.model.highlights.GithubObject;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubFragment extends Fragment {


    RecyclerView githubRecyclerView;
    ProgressBar githubProgressBar;
    String base_url = "https://stc-app-backend.herokuapp.com/api/";
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView internetCheck;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout githubLayout;


    public GithubFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences= requireContext().getSharedPreferences("github", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        githubProgressBar=view.findViewById(R.id.progressbarGithub);
        githubRecyclerView = view.findViewById(R.id.githubRecyclerView);
        githubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        internetCheck=view.findViewById(R.id.internetcheckGithub);

        if(NavActivity.githubList.size()==0){
            loadData(retrofit);
        }
        else{

            GithubAdapter githubAdapter = new GithubAdapter(getContext(), NavActivity.githubList);
            githubRecyclerView.setAdapter(githubAdapter);
            githubProgressBar.setVisibility(View.GONE);

        }

        githubLayout = view.findViewById(R.id.githubLayout);

        swipeRefreshLayout = view.findViewById(R.id.githubSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                NavActivity.githubList.clear();
                if(githubRecyclerView.getAdapter()!=null)
                {
                    Objects.requireNonNull(githubRecyclerView.getAdapter()).notifyDataSetChanged();
                }
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        loadData(retrofit);
                    }
                });
            }
        });
    }

    private void loadShared() {

        NavActivity.githubList.clear();
        SharedPreferences sharedPreferences= requireContext().getSharedPreferences("github", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        assert json != null;
        Log.i("GETDATA ",json);
        Type type=new TypeToken<List<GithubObject>>(){}.getType();
        NavActivity.githubList =gson.fromJson(json,type);

        swipeRefreshLayout.setRefreshing(false);
        githubProgressBar.setVisibility(View.GONE);
        internetCheck.setVisibility(View.GONE);
        GithubAdapter adapter=new GithubAdapter(getContext(), NavActivity.githubList);
        githubRecyclerView.setAdapter(adapter);

    }

    private void loadData(Retrofit retrofit) {

        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        Call<List<GithubObject>> call = jsonPlaceholderApi.getGithub();
        call.enqueue(new Callback<List<GithubObject>>() {
            @Override
            public void onResponse(@NotNull Call<List<GithubObject>> call, @NotNull Response<List<GithubObject>> response) {
                if(!response.isSuccessful()){
                    if(response.code()==400)
                    {
                        loadData(retrofit);
                    }
                    else {
                        swipeRefreshLayout.setRefreshing(false);
                        Snackbar.make(githubRecyclerView,"ErrorCode " + response.code(),Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                        Log.i("CODE", String.valueOf(response.code()));
                    }
                    return;
                }
                List<GithubObject> githubObjects = response.body();
                assert githubObjects != null;
                for (GithubObject githubObject : githubObjects) {
                    String title = githubObject.getTitle();
                    String content = githubObject.getLink();
                    NavActivity.githubList.add(new GithubObject(title, content));
                }

                Gson gson = new Gson();
                String json = gson.toJson(NavActivity.githubList);
                Log.i("JSON", json);
                editor= sharedPreferences.edit();
                editor.putString("data", json);
                editor.apply();
                swipeRefreshLayout.setRefreshing(false);
                githubProgressBar.setVisibility(View.GONE);
                internetCheck.setVisibility(View.GONE);
                GithubAdapter githubAdapter = new GithubAdapter(getContext(), NavActivity.githubList);
                githubRecyclerView.setAdapter(githubAdapter);

            }

            @Override
            public void onFailure(@NotNull Call<List<GithubObject>> call, @NotNull Throwable t) {
                Log.i("FAILED : ", Objects.requireNonNull(t.getMessage()));
                if(sharedPreferences.contains("data")){
                    sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    githubProgressBar.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                }
            }
        });


    }

}