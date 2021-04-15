package com.mstc.mstcapp.ui.highlights.github;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.highlight.GithubAdapter;
import com.mstc.mstcapp.model.highlights.GithubObject;

import java.util.ArrayList;
import java.util.List;

public class GithubFragment extends Fragment {
    RecyclerView recyclerView;
    GithubViewModel mViewModel;
    GithubAdapter adapter;
    List<GithubObject> list;

    public GithubFragment() {
    }

    public static GithubFragment newInstance(int columnCount) {
        return new GithubFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GithubViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();
        adapter = new GithubAdapter(list);
        recyclerView.setAdapter(adapter);
        mViewModel.getList().observe(getViewLifecycleOwner(), eventObjects -> {
            list = eventObjects;
            adapter.setList(list);
        });
    }
}