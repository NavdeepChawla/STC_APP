package com.mstc.mstcapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mstc.mstcapp.MainActivity;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapters.FeedAdapter;
import com.mstc.mstcapp.model.FeedObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    RecyclerView recyclerView;
    HomeViewModel mViewModel;
    FeedAdapter adapter;
    List<FeedObject> feedList;
    int skip = 1;
    ProgressBar load;
    SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = view.getContext();
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        load = view.findViewById(R.id.load);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        feedList = new ArrayList<>();
        adapter = new FeedAdapter(feedList);
        recyclerView.setAdapter(adapter);
        mViewModel.getList().observe(getViewLifecycleOwner(), list -> {
            swipeRefreshLayout.setRefreshing(!MainActivity.isAppRunning);
            feedList = list;
            adapter.setList(feedList);
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        mViewModel.more(++skip, swipeRefreshLayout);
                    }
                }
            }
        });
    }
}