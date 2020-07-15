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
import com.mstc.mstcapp.adapter.highlights.ProjectAdapter;
import com.mstc.mstcapp.model.highlights.ProjectObject;

import java.util.ArrayList;
import java.util.List;

public class ProjectFragment extends Fragment {

    List<ProjectObject> projectList;
    RecyclerView projectRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectRecyclerView = view.findViewById(R.id.projectRecyclerView);

        projectList = new ArrayList<>();
        projectList.add(new ProjectObject("MSTC APP","asdfghjk lzxcvbnm, qwertyuioop","www.google.com","Navdeep,Prajesh,Utkarsh"));
        projectList.add(new ProjectObject("MSTC APP","asdfghjk lzxcvbnm, qwertyuioop","www.google.com","Navdeep,Prajesh,Utkarsh"));
        projectList.add(new ProjectObject("MSTC APP","asdfghjk lzxcvbnm, qwertyuioop","www.google.com","Navdeep,Prajesh,Utkarsh"));

        ProjectAdapter projectAdapter = new ProjectAdapter(getContext(),projectList);
        projectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        projectRecyclerView.setAdapter(projectAdapter);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.projectSwipeRefresh);
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