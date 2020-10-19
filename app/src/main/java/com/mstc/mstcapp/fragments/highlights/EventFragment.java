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
import com.mstc.mstcapp.adapter.highlights.EventAdapter;
import com.mstc.mstcapp.model.highlights.EventObject;
import com.mstc.mstcapp.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventFragment extends Fragment {

    RecyclerView eventRecyclerView;
    ProgressBar eventProgressbar;
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView internetCheck;
    SwipeRefreshLayout swipeRefreshLayout;

    public EventFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit=new Retrofit.Builder()
                .baseUrl(Utils.HIGHLIGHT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sharedPreferences= requireContext().getSharedPreferences("event", Context.MODE_PRIVATE);

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
        internetCheck=view.findViewById(R.id.internetcheckEvent);

        if(NavActivity.eventList.size()==0){
            loadData(retrofit);
        }
        else{
            EventAdapter eventAdapter=new EventAdapter(getContext(),NavActivity.eventList);
            eventRecyclerView.setAdapter(eventAdapter);
            eventProgressbar.setVisibility(View.GONE);
        }

        swipeRefreshLayout = view.findViewById(R.id.eventSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(eventProgressbar.getVisibility()==View.GONE)
                {
                    NavActivity.eventList.clear();
                    if(eventRecyclerView.getAdapter()!=null)
                    {
                        Objects.requireNonNull(eventRecyclerView.getAdapter()).notifyDataSetChanged();
                    }
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            loadData(retrofit);
                        }
                    });
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void loadData(Retrofit retrofit){
        JsonPlaceholderApi jsonPlaceholderapi=retrofit.create(JsonPlaceholderApi.class);
        Call<List<EventObject>> call= jsonPlaceholderapi.getEvents();
        call.enqueue(new Callback<List<EventObject>>() {
            @Override
            public void onResponse(@NotNull Call<List<EventObject>> call, @NotNull Response<List<EventObject>> response) {
                if(!response.isSuccessful()){
                    swipeRefreshLayout.setRefreshing(false);
                    eventProgressbar.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                    Snackbar.make(eventRecyclerView,"ErrorCode " + response.code(),Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
                    Log.i("CODE", String.valueOf(response.code()));
                }
                else
                {
                    List<EventObject> events=response.body();
                    if(events!=null)
                    {
                        NavActivity.eventList.clear();
                        for(EventObject events1:events){

                            String title = events1.getEventTitle();
                            String desc = events1.getEventDesc();
                            String link= events1.getEventLink();
                            String picture=events1.getEventPicture();
                            NavActivity.eventList.add(new EventObject(title,desc,link,picture));

                        }

                        Gson gson=new Gson();
                        String json=gson.toJson(NavActivity.eventList);
                        Log.i("JSON",json);
                        editor= sharedPreferences.edit();
                        editor.putString("data",json);
                        editor.apply();
                        swipeRefreshLayout.setRefreshing(false);
                        eventProgressbar.setVisibility(View.GONE);
                        internetCheck.setVisibility(View.GONE);
                        EventAdapter eventAdapter=new EventAdapter(getContext(),NavActivity.eventList);
                        eventRecyclerView.setAdapter(eventAdapter);
                    }
                    else {
                        if(sharedPreferences.contains("data")){
                            //sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                            Log.i("SHARED","Yes Data");
                            loadShared();
                        }
                        else {
                            swipeRefreshLayout.setRefreshing(false);
                            eventProgressbar.setVisibility(View.GONE);
                            internetCheck.setVisibility(View.VISIBLE);
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<EventObject>> call, @NotNull Throwable t) {
                Log.i("FAILED : ", Objects.requireNonNull(t.getMessage()));
                if(sharedPreferences.contains("data")){
                    //sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
                    Log.i("SHARED","Yes Data");
                    loadShared();
                }
                else {
                    swipeRefreshLayout.setRefreshing(false);
                    eventProgressbar.setVisibility(View.GONE);
                    internetCheck.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void loadShared(){
        NavActivity.eventList.clear();
        //SharedPreferences sharedPreferences= requireContext().getSharedPreferences("event", Context.MODE_PRIVATE);
        if(getContext()!=null)
        {
            Gson gson=new Gson();
            String json=sharedPreferences.getString("data",null);
           if(json!=null) {
               Log.i("GETDATA ", json);
               Type type = new TypeToken<List<EventObject>>() {}.getType();
               NavActivity.eventList = gson.fromJson(json, type);
               EventAdapter adapter = new EventAdapter(getContext(), NavActivity.eventList);
               eventRecyclerView.setAdapter(adapter);
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
        eventProgressbar.setVisibility(View.GONE);

    }
}