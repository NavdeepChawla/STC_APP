package com.mstc.mstcapp.fragments.highlights;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.highlights.ProjectAdapter;
import com.mstc.mstcapp.model.highlights.ProjectsObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectFragment extends Fragment {

    List<ProjectsObject> projectList=new ArrayList<>();
    RecyclerView projectRecyclerView;
    ProgressBar projectProgressBar;
    CardView projectCardView;
    String base_url = "https://stc-app-backend.herokuapp.com/api/";
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public ProjectFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sharedPreferences=getContext().getSharedPreferences("project", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

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
        projectList = new ArrayList<>();

        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        if(sharedPreferences.contains("data")){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());

            loadShared();
        }
        else{

            loadData(retrofit);
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

        projectList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderApi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<ProjectsObject>> call=jsonPlaceholderApi.getProjects();
        call.enqueue(new Callback<List<ProjectsObject>>() {
            @Override
            public void onResponse(Call<List<ProjectsObject>> call, Response<List<ProjectsObject>> response) {
                if(!response.isSuccessful()) {
                    if (response.code() == 400) {
                        loadData(retrofit);
                        return;
                    } else {
                        Toast.makeText(getContext(), "ErrorCode " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.i("CODE", String.valueOf(response.code()));
                        return;
                    }
                }

                List<ProjectsObject> projects=response.body();
                for(ProjectsObject projectsObject1 :projects){

                    String title = projectsObject1.getTitle();
                    String desc = projectsObject1.getDesc();
                    String link= projectsObject1.getLink();
                    List <String> contri = projectsObject1.getContributors();
                    projectList.add(new ProjectsObject(title,contri,link,desc));

                }
                Gson gson=new Gson();
                String json=gson.toJson(projectList);
                Log.i("JSON",json);
                editor.putString("data",json);
                editor.commit();
                projectProgressBar.setVisibility(View.INVISIBLE);
                ProjectAdapter projectAdapter=new ProjectAdapter(getContext(),projectList);
                projectRecyclerView.setAdapter(projectAdapter);
            }

            @Override
            public void onFailure(Call<List<ProjectsObject>> call, Throwable t) {
                Log.i("FAILED : ", t.getMessage());
                loadData(retrofit);
            }
        });

    }

   private void loadShared(){
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("project", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        Log.i("GETDATA ",json);
        Type type=new TypeToken<List<ProjectsObject>>(){}.getType();
        projectList=gson.fromJson(json,type);

        projectProgressBar.setVisibility(View.INVISIBLE);
        ProjectAdapter adapter=new ProjectAdapter(getContext(),projectList);
        projectRecyclerView.setAdapter(adapter);

       //if updates are there
       loadData(retrofit);

    }
}