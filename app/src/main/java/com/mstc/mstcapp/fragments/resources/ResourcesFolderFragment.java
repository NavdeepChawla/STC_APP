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
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.resources.ResourcesFolderAdapter;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ResourcesFolderFragment extends Fragment {
    String domain;

    public ResourcesFolderFragment(String domain) {
        this.domain = domain;
    }

    ProgressBar resouurcesfolderProgressbar;
    RecyclerView resourcesfolderRecyclerview;
    List<ResourcesFolderObject> resourcesFolderObjectsList;
    Retrofit retrofit;
    Context context;
    String base_url = "https://stc-app-backend.herokuapp.com/api/resources/";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        context=getContext();

        domain=domain.replaceAll("\\s","");
        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences=getContext().getSharedPreferences(domain+"resource",Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resourcesfolder,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resouurcesfolderProgressbar=view.findViewById(R.id.progressbarResourcesFolder);
        resourcesfolderRecyclerview=view.findViewById(R.id.resourcesfolder_recyclerview);
        resourcesfolderRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        if(sharedPreferences.contains("data")){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
            Log.i("SHARED","Yes Data");
            resourcesFolderObjectsList=new ArrayList<>();
            loadShared();
        }
        else{
            Log.i("SHARED","No Data");
            loadData(retrofit,domain,editor);
        }

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.resourceFolderSwipeRefresh);
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



    private void loadData(Retrofit retrofit,String domain,SharedPreferences.Editor editor) {
        resourcesFolderObjectsList=new ArrayList<>();

        JsonPlaceholderApi jsonPlaceholderApi= retrofit.create(JsonPlaceholderApi.class);
        Call<List<ResourcesFolderObject>> call=jsonPlaceholderApi.getResourcesFolderObject(base_url+domain);
        call.enqueue(new Callback<List<ResourcesFolderObject>>() {

            @Override
            public void onResponse(Call<List<ResourcesFolderObject>> call, Response<List<ResourcesFolderObject>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(),"Code "+response.code(),Toast.LENGTH_SHORT).show();
                    Log.i("CODE", String.valueOf(response.code()));
                    return;
                }
                    List<ResourcesFolderObject> resourcesFolderObjects=response.body();
                    editor.clear();
                    for(ResourcesFolderObject resourcesFolderObject:resourcesFolderObjects){
                        String title=resourcesFolderObject.getResourcesfolderTitle();
                        String desc=resourcesFolderObject.getResourcefolderDesc();
                        String link= resourcesFolderObject.getResourcefolderLink();
                        resourcesFolderObjectsList.add(new ResourcesFolderObject(title,link,desc));

                    }
                    Gson gson=new Gson();
                    String json=gson.toJson(resourcesFolderObjectsList);
                    Log.i("JSON",json);
                    editor.putString("data",json);
                    editor.commit();
                    resouurcesfolderProgressbar.setVisibility(View.INVISIBLE);
                    ResourcesFolderAdapter adapter=new ResourcesFolderAdapter(resourcesFolderObjectsList);
                    resourcesfolderRecyclerview.setAdapter(adapter);

                }



            @Override
            public void onFailure(Call<List<ResourcesFolderObject>> call, Throwable t) {
                Log.i("FAILED : ",t.getMessage());
                loadData(retrofit,domain,editor
                );
            }
        });
    }
    private void loadShared(){
        SharedPreferences sharedPreferences=getContext().getSharedPreferences(domain+"resource",Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        Log.i("GETDATA ",json);
        Type type=new TypeToken<List<ResourcesFolderObject>>(){}.getType();
        resourcesFolderObjectsList=gson.fromJson(json,type);
        resouurcesfolderProgressbar.setVisibility(View.INVISIBLE);
        ResourcesFolderAdapter adapter=new ResourcesFolderAdapter(resourcesFolderObjectsList);
        resourcesfolderRecyclerview.setAdapter(adapter);

        //if updates are there
        loadData(retrofit,domain,editor);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
