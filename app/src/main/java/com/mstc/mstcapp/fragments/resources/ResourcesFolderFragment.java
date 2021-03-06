package com.mstc.mstcapp.fragments.resources;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.mstc.mstcapp.adapter.resources.ResourcesFolderAdapter;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;
import com.mstc.mstcapp.util.Utils;

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

public class ResourcesFolderFragment extends Fragment {
    String domain;

    public ResourcesFolderFragment(){}
    public ResourcesFolderFragment(String domain) {
        this.domain = domain;
    }

    ProgressBar resouurcesfolderProgressbar;
    RecyclerView resourcesfolderRecyclerview;
    List<ResourcesFolderObject> resourcesFolderObjectsList;
    Retrofit retrofit;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView internetCheck;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        domain=domain.replaceAll("\\s","");
        retrofit=new Retrofit.Builder()
                .baseUrl(Utils.RESOURCES_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sharedPreferences= requireContext().getSharedPreferences(domain+"resource",Context.MODE_PRIVATE);


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
        internetCheck=view.findViewById(R.id.internetcheckResourcesFolder);


        loadData(retrofit,domain);

        swipeRefreshLayout = view.findViewById(R.id.resourceFolderSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(resouurcesfolderProgressbar.getVisibility()==View.GONE)
                {
                    resourcesFolderObjectsList.clear();
                    if(resourcesfolderRecyclerview.getAdapter()!=null)
                    {
                        Objects.requireNonNull(resourcesfolderRecyclerview.getAdapter()).notifyDataSetChanged();
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



    private void loadData(Retrofit retrofit,String domain) {

        resourcesFolderObjectsList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderApi= retrofit.create(JsonPlaceholderApi.class);
        Call<List<ResourcesFolderObject>> call=jsonPlaceholderApi.getResourcesFolderObject(Utils.RESOURCES_URL+domain);
        call.enqueue(new Callback<List<ResourcesFolderObject>>() {

            @Override
            public void onResponse(@NotNull Call<List<ResourcesFolderObject>> call, @NotNull Response<List<ResourcesFolderObject>> response) {
                if(!response.isSuccessful()){
                    swipeRefreshLayout.setRefreshing(false);
                    resouurcesfolderProgressbar.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                    Snackbar.make(resourcesfolderRecyclerview,"ErrorCode " + response.code(),Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                    Log.i("CODE", String.valueOf(response.code()));
                }
                else
                {
                    List<ResourcesFolderObject> resourcesFolderObjects=response.body();
                    if(resourcesFolderObjects!=null)
                    {
                        resourcesFolderObjectsList.clear();
                        for(ResourcesFolderObject resourcesFolderObject:resourcesFolderObjects){
                            String title=resourcesFolderObject.getResourcesfolderTitle();
                            String desc=resourcesFolderObject.getResourcefolderDesc();
                            String link= resourcesFolderObject.getResourcefolderLink();
                            resourcesFolderObjectsList.add(new ResourcesFolderObject(title,link,desc));


                        }
                        Gson gson=new Gson();
                        String json=gson.toJson(resourcesFolderObjectsList);
                        Log.i("JSON",json);
                        editor= sharedPreferences.edit();
                        editor.putString("data",json);
                        editor.apply();
                        internetCheck.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        resouurcesfolderProgressbar.setVisibility(View.GONE);
                        ResourcesFolderAdapter adapter=new ResourcesFolderAdapter(resourcesFolderObjectsList,getContext());
                        resourcesfolderRecyclerview.setAdapter(adapter);
                    }
                    else
                    {
                        if(sharedPreferences.contains("data")){
                            //sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                            Log.i("SHARED","Yes Data");
                            loadShared();
                        }
                        else {
                            swipeRefreshLayout.setRefreshing(false);
                            resouurcesfolderProgressbar.setVisibility(View.GONE);
                            internetCheck.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }



            @Override
            public void onFailure(@NotNull Call<List<ResourcesFolderObject>> call, @NotNull Throwable t) {
                Log.i("FAILED : ", Objects.requireNonNull(t.getMessage()));
                if(sharedPreferences.contains("data")){
                    //sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    resouurcesfolderProgressbar.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void loadShared(){
        //SharedPreferences sharedPreferences= requireContext().getSharedPreferences(domain+"resource",Context.MODE_PRIVATE);
        resourcesFolderObjectsList.clear();
        if(getContext()!=null)
        {
            Gson gson=new Gson();
            String json=sharedPreferences.getString("data",null);
            if(json!=null)
            {
                Log.i("GETDATA ",json);
                Type type=new TypeToken<List<ResourcesFolderObject>>(){}.getType();
                resourcesFolderObjectsList=gson.fromJson(json,type);
                ResourcesFolderAdapter adapter=new ResourcesFolderAdapter(resourcesFolderObjectsList,getContext());
                resourcesfolderRecyclerview.setAdapter(adapter);
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
        resouurcesfolderProgressbar.setVisibility(View.GONE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}