package com.mstc.mstcapp.ui.resources.resourceTab;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.resource.ResourceTabAdapter;
import com.mstc.mstcapp.model.resources.ResourceModel;
import com.mstc.mstcapp.util.ClickListener;
import com.mstc.mstcapp.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ResourceTabFragment extends Fragment {
    private RecyclerView recyclerView;
    private ResourceTabViewModel mViewModel;
    private ResourceTabAdapter adapter;
    private List<ResourceModel> list;
    private String domain;

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
        adapter = new ResourceTabAdapter(context, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        mViewModel.getList(domain).observe(getViewLifecycleOwner(), eventObjects -> {
            list = eventObjects;
            adapter.setList(list);
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(context, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                openURL(list.get(position).getLink());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void openURL(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}