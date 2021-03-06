package com.mstc.mstcapp.fragments.resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.resources.ArticlelinksAdapter;
import com.mstc.mstcapp.adapter.resources.ResourcesFolderAdapter;
import com.mstc.mstcapp.model.resources.ArticleLinksObject;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;
import com.mstc.mstcapp.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleLinksFragment extends Fragment {

    List<ArticleLinksObject> articleLinksObjectList=new ArrayList<>();
    RecyclerView articlelinksRecyclerView;
    ProgressBar articlelinksProgressbar;
    String domain;
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    TextView internetCheck;
    SwipeRefreshLayout swipeRefreshLayout;

    public ArticleLinksFragment(String domain) {
        this.domain=domain;
    }

    public ArticleLinksFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        domain=domain.replaceAll("\\s","");
        retrofit=new Retrofit.Builder()
                .baseUrl(Utils.ARTICLE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences= requireContext().getSharedPreferences(domain+"article",Context.MODE_PRIVATE);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articlelinks,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        articlelinksProgressbar=view.findViewById(R.id.progressbarArticlelinks);
        articlelinksRecyclerView=view.findViewById(R.id.resourcesarticle_recyclerview);
        articlelinksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        internetCheck=view.findViewById(R.id.internetcheckArticles);
        articleLinksObjectList=new ArrayList<>();

        loadData(retrofit,domain);

        swipeRefreshLayout = view.findViewById(R.id.articleSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(articlelinksProgressbar.getVisibility()==View.GONE)
                {
                    articleLinksObjectList.clear();
                    if(articlelinksRecyclerView.getAdapter()!=null)
                    {
                        Objects.requireNonNull(articlelinksRecyclerView.getAdapter()).notifyDataSetChanged();
                    }
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            loadData(retrofit,domain);
                        }
                    });
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

        });

    }

    private void loadData(Retrofit retrofit, String domain) {

        JsonPlaceholderApi jsonPlaceholderApi= retrofit.create(JsonPlaceholderApi.class);
        Call<List<ArticleLinksObject>> call=jsonPlaceholderApi.getArticleLinksObject(Utils.ARTICLE_URL+domain);
        call.enqueue(new Callback<List<ArticleLinksObject>>() {
            @Override
            public void onResponse(@NotNull Call<List<ArticleLinksObject>> call, @NotNull Response<List<ArticleLinksObject>> response) {
                if(!response.isSuccessful()){
                    swipeRefreshLayout.setRefreshing(false);
                    articlelinksProgressbar.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                    Snackbar.make(articlelinksRecyclerView,"ErrorCode " + response.code(),Snackbar.LENGTH_SHORT).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                    Log.i("CODE", String.valueOf(response.code()));
                }
                else
                {
                    List<ArticleLinksObject> articleLinksObjects= response.body();
                    if(articleLinksObjects!=null)
                    {
                        articleLinksObjectList.clear();
                        for(ArticleLinksObject articleLinksObject : articleLinksObjects){
                            String title=articleLinksObject.getArticlelinksTitle();
                            String link=articleLinksObject.getArticlelinksLink();
                            String desc=articleLinksObject.getArticlelinksDesc();
                            articleLinksObjectList.add(new ArticleLinksObject(title,link,desc));
                        }

                        Gson gson=new Gson();
                        String json=gson.toJson(articleLinksObjects);
                        Log.i("JSON",json);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("data",json);
                        editor.apply();
                        internetCheck.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        articlelinksProgressbar.setVisibility(View.GONE);
                        ArticlelinksAdapter adapter=new ArticlelinksAdapter(articleLinksObjectList,getContext());
                        articlelinksRecyclerView.setAdapter(adapter);
                    }
                    else {
                        if(sharedPreferences.contains("data")){
                            Log.i("SHARED","Yes Data");
                            loadShared();
                        }
                        else {
                            swipeRefreshLayout.setRefreshing(false);
                            articlelinksProgressbar.setVisibility(View.GONE);
                            internetCheck.setVisibility(View.VISIBLE);
                        }
                    }
                }


            }
            @Override
            public void onFailure(@NotNull Call<List<ArticleLinksObject>> call, @NotNull Throwable t) {
                Log.i("FAILED : ", Objects.requireNonNull(t.getMessage()));
                if(sharedPreferences.contains("data")){
                    //sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    articlelinksProgressbar.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                }

            }
        });

    }
    private void loadShared(){
        //SharedPreferences sharedPreferences= requireContext().getSharedPreferences(domain+"article", Context.MODE_PRIVATE);
        articleLinksObjectList.clear();
        if(getContext()!=null)
        {
            Gson gson=new Gson();
            String json=sharedPreferences.getString("data",null);
            if(json!=null)
            {
                Log.i("GETDATA ",json);
                Type type=new TypeToken<List<ArticleLinksObject>>(){}.getType();
                articleLinksObjectList=gson.fromJson(json,type);
                ArticlelinksAdapter adapter=new ArticlelinksAdapter(articleLinksObjectList,getContext());
                articlelinksRecyclerView.setAdapter(adapter);
                internetCheck.setVisibility(View.GONE);
            }
            else {
            internetCheck.setVisibility(View.VISIBLE);
            }
        }
        else {
            internetCheck.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setRefreshing(false);
        articlelinksProgressbar.setVisibility(View.GONE);
    }
}