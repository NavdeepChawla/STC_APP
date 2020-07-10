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

    private List<ResourceModel> domainList;
    private RecyclerView domainRecyclerView;

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

        domainList = new ArrayList<>();
        domainList.add(new ResourceModel("Android",R.mipmap.ic_green_android_foreground));
        domainList.add(new ResourceModel("Flutter",R.mipmap.ic_flutter_foreground));
        domainList.add(new ResourceModel("Frontend",R.mipmap.ic_frontend_foreground));
        domainList.add(new ResourceModel("Backend",R.mipmap.ic_backend_foreground));
        domainList.add(new ResourceModel("Data Science",R.mipmap.ic_data_science_foreground));
        domainList.add(new ResourceModel("Competitive Coding",R.mipmap.ic_cc_foreground));


        domainRecyclerView=view.findViewById(R.id.domainsRecyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        domainRecyclerView.setLayoutManager(gridLayoutManager);   //setting layout as grid in the recycler view
        domainRecyclerView.setAdapter(new ResourceAdapter(getContext(),domainList));//adapter for the recycler view

    }
}