package com.mstc.mstcapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapters.ResourceAdapter;
import com.mstc.mstcapp.model.ResourceModel;
import com.mstc.mstcapp.util.ClickListener;
import com.mstc.mstcapp.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class ResourcesFragment extends Fragment {

    public ResourcesFragment() {
        // Required empty public constructor
    }

    public static ResourcesFragment newInstance(String param1, String param2) {
        return new ResourcesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resources, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<ResourceModel> domainList = new ArrayList<>();
        domainList.add(new ResourceModel("android", "Android", R.drawable.ic_android_bg));
        domainList.add(new ResourceModel("flutter", "Flutter", R.drawable.ic_flutter_bg));
        domainList.add(new ResourceModel("frontend", "Frontend", R.drawable.ic_web_logos));
        domainList.add(new ResourceModel("backend", "Backend", R.drawable.ic_backend_foreground));
        domainList.add(new ResourceModel("data_science", "Data Science", R.drawable.ic_datascience_bg));
        domainList.add(new ResourceModel("competitive_coding", "Competitive Coding", R.drawable.ic_compcc_bg));
        domainList.add(new ResourceModel("design", "Design", R.drawable.ic_design_bg));


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new ResourceAdapter(getContext(), domainList));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("domain", domainList.get(position).getDomain());
                NavHostFragment.findNavController(ResourcesFragment.this).navigate(R.id.action_navigation_resources_to_fragment_view_resource, bundle);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

}