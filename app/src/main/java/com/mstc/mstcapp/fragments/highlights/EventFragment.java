package com.mstc.mstcapp.fragments.highlights;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.highlights.EventAdapter;
import com.mstc.mstcapp.model.highlights.EventObject;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {

    RecyclerView eventRecyclerView;
    List<EventObject> eventList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventRecyclerView = view.findViewById(R.id.eventsRecyclerView);

        eventList=new ArrayList<>();
        eventList.add(new EventObject("Brew","Our Flagship Event","https://www.instagram.com/mstcvit/",R.drawable.xbox));
        eventList.add(new EventObject("Docker","Another Event","https://www.instagram.com/mstcvit/",R.drawable.xbox));

        EventAdapter eventAdapter = new EventAdapter(getContext(),eventList);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventRecyclerView.setAdapter(eventAdapter);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.eventSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

    }
}