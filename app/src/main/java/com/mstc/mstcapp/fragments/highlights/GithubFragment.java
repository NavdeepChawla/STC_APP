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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.highlights.GithubAdapter;
import com.mstc.mstcapp.adapter.highlights.ProjectAdapter;
import com.mstc.mstcapp.model.highlights.GithubObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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


    public GithubFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences=getContext().getSharedPreferences("github", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

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


        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.githubSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        loadData(retrofit);
                    }
                }, 2000);
            }
        });
    }

    private void loadShared() {
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("github", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        Log.i("GETDATA ",json);
        Type type=new TypeToken<List<GithubObject>>(){}.getType();
         NavActivity.githubList =gson.fromJson(json,type);

        githubProgressBar.setVisibility(View.INVISIBLE);
        GithubAdapter adapter=new GithubAdapter(getContext(), NavActivity.githubList);
        githubRecyclerView.setAdapter(adapter);

        //if updates are there
        loadData(retrofit);

    }

    private void loadData(Retrofit retrofit) {

        NavActivity.githubList = new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
        Call<List<GithubObject>> call = jsonPlaceholderApi.getGithub();
        call.enqueue(new Callback<List<GithubObject>>() {
            @Override
            public void onResponse(Call<List<GithubObject>> call, Response<List<GithubObject>> response) {
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
                editor.putString("data", json);
                editor.commit();
                githubProgressBar.setVisibility(View.INVISIBLE);
                GithubAdapter githubAdapter = new GithubAdapter(getContext(), NavActivity.githubList);
                githubRecyclerView.setAdapter(githubAdapter);

            }

            @Override
            public void onFailure(Call<List<GithubObject>> call, Throwable t) {
                Log.i("FAILED : ", t.getMessage());
                if(sharedPreferences.contains("data")){
                    sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    githubProgressBar.setVisibility(View.INVISIBLE);
                    internetCheck.setVisibility(View.VISIBLE);
                }
            }
        });


    }

}