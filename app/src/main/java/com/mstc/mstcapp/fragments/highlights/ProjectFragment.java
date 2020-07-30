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

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.highlights.ProjectAdapter;
import com.mstc.mstcapp.model.highlights.ProjectsObject;

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

public class ProjectFragment extends Fragment {

    RecyclerView projectRecyclerView;
    ProgressBar projectProgressBar;
    String base_url = "https://stc-app-backend.herokuapp.com/api/";
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ProjectAdapter projectAdapter;
    TextView internetCheck;

    public ProjectFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sharedPreferences= requireContext().getSharedPreferences("project", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        projectRecyclerView = view.findViewById(R.id.projectRecyclerView);
        projectProgressBar=view.findViewById(R.id.progressbarProject);
        internetCheck=view.findViewById(R.id.internetcheckProject);

        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(NavActivity.projectList.size()==0){
            loadData(retrofit);
        }
        else{
            projectAdapter=new ProjectAdapter(getContext(),NavActivity.projectList);
            projectRecyclerView.setAdapter(projectAdapter);
            projectProgressBar.setVisibility(View.GONE);
        }


        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.projectSwipeRefresh);
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
    private void loadData(Retrofit retrofit) {

        NavActivity.projectList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderApi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<ProjectsObject>> call=jsonPlaceholderApi.getProjects();
        call.enqueue(new Callback<List<ProjectsObject>>() {
            @Override
            public void onResponse(@NotNull Call<List<ProjectsObject>> call, @NotNull Response<List<ProjectsObject>> response) {
                if(!response.isSuccessful()) {
                    if (response.code() == 400) {
                        loadData(retrofit);
                    } else {
                        Snackbar.make(projectRecyclerView,"ErrorCode " + response.code(),Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                        Log.i("CODE", String.valueOf(response.code()));
                    }
                    return;
                }

                List<ProjectsObject> projects=response.body();
                assert projects != null;
                for(ProjectsObject projectsObject1 :projects){

                    String title = projectsObject1.getTitle();
                    String desc = projectsObject1.getDesc();
                    String link= projectsObject1.getLink();
                    List <String> contri = projectsObject1.getContributors();
                    NavActivity.projectList.add(new ProjectsObject(title,contri,link,desc));

                }
                Gson gson=new Gson();
                String json=gson.toJson(NavActivity.projectList);
                editor= sharedPreferences.edit();
                Log.i("JSON",json);
                editor.putString("data",json);
                editor.apply();

                projectProgressBar.setVisibility(View.INVISIBLE);
                projectAdapter=new ProjectAdapter(getContext(),NavActivity.projectList);
                projectRecyclerView.setAdapter(projectAdapter);

            }

            @Override
            public void onFailure(@NotNull Call<List<ProjectsObject>> call, @NotNull Throwable t) {
                Log.i("FAILED : ", Objects.requireNonNull(t.getMessage()));
                if(sharedPreferences.contains("data")){
                    sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    projectProgressBar.setVisibility(View.INVISIBLE);
                    internetCheck.setVisibility(View.VISIBLE);
                }
            }
        });

    }

   private void loadShared(){
        SharedPreferences sharedPreferences= requireContext().getSharedPreferences("project", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
       assert json != null;
       Log.i("GETDATA ",json);
        Type type=new TypeToken<List<ProjectsObject>>(){}.getType();
        NavActivity.projectList=gson.fromJson(json,type);

        projectProgressBar.setVisibility(View.INVISIBLE);
        projectAdapter=new ProjectAdapter(getContext(),NavActivity.projectList);
        projectRecyclerView.setAdapter(projectAdapter);




    }
}