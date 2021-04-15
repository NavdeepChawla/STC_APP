package com.mstc.mstcapp.ui.resources.resourceTab;

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
import com.mstc.mstcapp.adapter.resource.ResourceTabAdapter;
import com.mstc.mstcapp.model.resources.Resource;

import java.util.ArrayList;
import java.util.List;

public class ResourceTabFragment extends Fragment {
    RecyclerView recyclerView;
    ResourceTabViewModel mViewModel;
    ResourceTabAdapter adapter;
    List<Resource> list;
    String domain;

    public ResourceTabFragment() {
    }

    public ResourceTabFragment(String domain) {
        this.domain = domain;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ResourceTabViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();
        adapter = new ResourceTabAdapter(list);
        recyclerView.setAdapter(adapter);
        mViewModel.getList(domain).observe(getViewLifecycleOwner(), eventObjects -> {
            list = eventObjects;
            adapter.setList(list);
        });
    }
}