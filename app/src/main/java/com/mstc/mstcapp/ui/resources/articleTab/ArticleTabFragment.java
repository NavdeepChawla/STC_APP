package com.mstc.mstcapp.ui.resources.articleTab;

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
import com.mstc.mstcapp.adapter.resource.ArticleTabAdapter;
import com.mstc.mstcapp.model.resources.Article;

import java.util.ArrayList;
import java.util.List;

public class ArticleTabFragment extends Fragment {
    RecyclerView recyclerView;
    ArticleTabViewModel mViewModel;
    ArticleTabAdapter adapter;
    List<Article> list;
    String domain;

    public ArticleTabFragment() {
    }

    public ArticleTabFragment(String domain) {
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
        mViewModel = new ViewModelProvider(this).get(ArticleTabViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();
        adapter = new ArticleTabAdapter(list);
        recyclerView.setAdapter(adapter);
        mViewModel.getList(domain).observe(getViewLifecycleOwner(), eventObjects -> {
            list = eventObjects;
            adapter.setList(list);
        });
    }
}