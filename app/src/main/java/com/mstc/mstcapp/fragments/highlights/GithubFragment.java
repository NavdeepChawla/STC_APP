package com.mstc.mstcapp.fragments.highlights;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.highlights.GithubAdapter;
import com.mstc.mstcapp.model.highlights.GithubObject;

import java.util.ArrayList;
import java.util.List;

public class GithubFragment extends Fragment {

    List<GithubObject> githubList;
    RecyclerView githubRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_github, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        githubRecyclerView = view.findViewById(R.id.githubRecyclerView);

        githubList = new ArrayList<>();
        githubList.add(new GithubObject("MSTC APP","www.google.com"));
        githubList.add(new GithubObject("MSTC APP","www.google.com"));
        githubList.add(new GithubObject("MSTC APP","www.google.com"));

        GithubAdapter githubAdapter = new GithubAdapter(getContext(),githubList);
        githubRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        githubRecyclerView.setAdapter(githubAdapter);
    }
}