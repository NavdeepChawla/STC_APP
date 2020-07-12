package com.mstc.mstcapp.fragments.resources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.resources.ArticlelinksAdapter;
import com.mstc.mstcapp.model.resources.ArticleLinksObject;

import java.util.ArrayList;
import java.util.List;

public class ArticleLinksFragment extends Fragment {
    List<ArticleLinksObject> articleLinksObjectList=new ArrayList<>();
    RecyclerView articlelinksRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_articlelinks,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        articlelinksRecyclerView=view.findViewById(R.id.resourcesarticle_recyclerview);
        articleLinksObjectList=new ArrayList<>();
        articleLinksObjectList.add(new ArticleLinksObject("Article sample","https://medium.com/student-technical-community-vit-vellore"));
        articleLinksObjectList.add(new ArticleLinksObject("Article sample","https://medium.com/student-technical-community-vit-vellore"));
        articleLinksObjectList.add(new ArticleLinksObject("Article sample","https://medium.com/student-technical-community-vit-vellore"));

        ArticlelinksAdapter adapter=new ArticlelinksAdapter(articleLinksObjectList,getContext());
        articlelinksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        articlelinksRecyclerView.setAdapter(adapter);
    }
}
