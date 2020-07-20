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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mstc.mstcapp.JsonPlaceholderApi;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.highlights.EventAdapter;
import com.mstc.mstcapp.adapter.resources.ArticlelinksAdapter;
import com.mstc.mstcapp.model.highlights.EventObject;
import com.mstc.mstcapp.model.resources.ArticleLinksObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventFragment extends Fragment {

    RecyclerView eventRecyclerView;
    List<EventObject> eventList=new ArrayList<>();
    ProgressBar eventProgressbar;
    String base_url = "https://stc-app-backend.herokuapp.com/api/";
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public EventFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences=getContext().getSharedPreferences("event", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventProgressbar=view.findViewById(R.id.progressbarEvent);
        eventRecyclerView = view.findViewById(R.id.eventsRecyclerView);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(sharedPreferences.contains("data")){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
            Log.i("SHARED","Yes Data");
            eventList=new ArrayList<>();
            loadShared();
        }
        else{
            Log.i("SHARED","No Data");
            loadData(retrofit);
        }

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.eventSwipeRefresh);
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

    private void loadData(Retrofit retrofit ){
        eventList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderapi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<EventObject>> call= jsonPlaceholderapi.getEvents();
        call.enqueue(new Callback<List<EventObject>>() {
            @Override
            public void onResponse(Call<List<EventObject>> call, Response<List<EventObject>> response) {
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

                List<EventObject> events=response.body();
                for(EventObject events1:events){

                    String title = events1.getEventTitle();
                    String desc = events1.getEventDesc();
                    String link= events1.getEventLink();
                    String picture=events1.getEventPicture();
                    eventList.add(new EventObject(title,desc,link,picture));

                }

                Gson gson=new Gson();
                String json=gson.toJson(eventList);
                Log.i("JSON",json);
                editor.putString("data",json);
                editor.commit();
                eventProgressbar.setVisibility(View.INVISIBLE);
                EventAdapter eventAdapter=new EventAdapter(getContext(),eventList);
                eventRecyclerView.setAdapter(eventAdapter);
            }

            @Override
            public void onFailure(Call<List<EventObject>> call, Throwable t) {
                Log.i("FAILED : ",t.getMessage());
                loadData(retrofit);
            }
        });
    }

    private void loadShared(){
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("event", Context.MODE_PRIVATE);
        Gson gson=new Gson();
        String json=sharedPreferences.getString("data","");
        Log.i("GETDATA ",json);

        Type type=new TypeToken<List<EventObject>>(){}.getType();
        eventList=gson.fromJson(json,type);

        eventProgressbar.setVisibility(View.INVISIBLE);
        EventAdapter adapter=new EventAdapter(getContext(),eventList);
        eventRecyclerView.setAdapter(adapter);

        //if updates are there
        loadData(retrofit);

    }
}