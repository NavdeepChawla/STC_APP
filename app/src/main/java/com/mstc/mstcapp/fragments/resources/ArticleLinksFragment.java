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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.resources.ArticlelinksAdapter;
import com.mstc.mstcapp.adapter.resources.ResourcesFolderAdapter;
import com.mstc.mstcapp.model.resources.ArticleLinksObject;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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
    String base_url = "https://stc-app-backend.herokuapp.com/api/articles/";
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView internetCheck;
    public ArticleLinksFragment(String domain) {
        this.domain=domain;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        domain=domain.replaceAll("\\s","");
        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences=getContext().getSharedPreferences(domain+"article",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();


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

        if(sharedPreferences.contains("data")){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
            Log.i("SHARED","Yes Data");
            articleLinksObjectList=new ArrayList<>();
            loadShared();
        }
        else{
            Log.i("SHARED","No Data");
            loadData(retrofit,domain,editor);
        }
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.articleSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        loadData(retrofit,domain,editor);
                    }
                }, 2000);
            }
        });

    }

    private void loadData(Retrofit retrofit, String domain, SharedPreferences.Editor editor) {
        articleLinksObjectList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderApi= retrofit.create(JsonPlaceholderApi.class);
        Call<List<ArticleLinksObject>> call=jsonPlaceholderApi.getArticleLinksObject(base_url+domain);
        call.enqueue(new Callback<List<ArticleLinksObject>>() {
            @Override
            public void onResponse(Call<List<ArticleLinksObject>> call, Response<List<ArticleLinksObject>> response) {
                if(!response.isSuccessful()){
                    if(response.code()==400)
                    {
                        loadData(retrofit,domain,editor);
                        return;
                    }
                    else {
                        Toast.makeText(getContext(), "ErrorCode " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.i("CODE", String.valueOf(response.code()));
                        return;
                    }
                }

                List<ArticleLinksObject> articleLinksObjects= response.body();
                for(ArticleLinksObject articleLinksObject : articleLinksObjects){
                    String title=articleLinksObject.getArticlelinksTitle();
                    String link=articleLinksObject.getArticlelinksLink();
                    String desc=articleLinksObject.getArticlelinksDesc();
                    articleLinksObjectList.add(new ArticleLinksObject(title,link,desc));
                }

                Gson gson=new Gson();
                String json=gson.toJson(articleLinksObjects);
                Log.i("JSON",json);
                editor.putString("data",json);
                editor.commit();
                articlelinksProgressbar.setVisibility(View.INVISIBLE);
                ArticlelinksAdapter adapter=new ArticlelinksAdapter(articleLinksObjectList,getContext());
                articlelinksRecyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<ArticleLinksObject>> call, Throwable t) {
                Log.i("FAILED : ",t.getMessage());
                if(sharedPreferences.contains("data")){
                    sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    articlelinksProgressbar.setVisibility(View.INVISIBLE);
                    internetCheck.setVisibility(View.VISIBLE);
                }

            }
        });

    }
    private void loadShared(){
        SharedPreferences sharedPreferences=getContext().getSharedPreferences(domain+"article", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        Log.i("GETDATA ",json);
        Type type=new TypeToken<List<ArticleLinksObject>>(){}.getType();
        articleLinksObjectList=gson.fromJson(json,type);

        articlelinksProgressbar.setVisibility(View.INVISIBLE);
        ArticlelinksAdapter adapter=new ArticlelinksAdapter(articleLinksObjectList,getContext());
        articlelinksRecyclerView.setAdapter(adapter);

        //if updates are there
        loadData(retrofit,domain,editor);

    }
}