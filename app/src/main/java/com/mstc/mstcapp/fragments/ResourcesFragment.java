package com.mstc.mstcapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.ResourceAdapter;
import com.mstc.mstcapp.model.ResourceModel;

import java.util.ArrayList;
import java.util.List;

public class ResourcesFragment extends Fragment {

    public ResourcesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resources, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavActivity.appBar.setElevation(4.0f);
        NavActivity.appBarTitle.setText("RESOURCES");

        List<ResourceModel> domainList = new ArrayList<>();
        domainList.add(new ResourceModel("Android",R.drawable.ic_android_bg));
        domainList.add(new ResourceModel("Flutter",R.drawable.ic_flutter_bg ));
        domainList.add(new ResourceModel("Frontend",R.drawable.ic_web_logos));
        domainList.add(new ResourceModel("Backend",R.drawable.ic_backend_foreground));
        domainList.add(new ResourceModel("Data Science",R.drawable.ic_datascience_bg));
        domainList.add(new ResourceModel("Competitive Coding",R.drawable.ic_compcc_bg));
        domainList.add(new ResourceModel("Design",R.drawable.ic_design_bg));


        RecyclerView domainRecyclerView = view.findViewById(R.id.domainsRecyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        domainRecyclerView.setLayoutManager(gridLayoutManager);   //setting layout as grid in the recycler view
        domainRecyclerView.setAdapter(new ResourceAdapter(getContext(), domainList));//adapter for the recycler view

    }
}