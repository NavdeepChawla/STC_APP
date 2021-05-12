package com.mstc.mstcapp.ui;

import android.content.Intent;
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
import com.mstc.mstcapp.adapter.ResourceAdapter;
import com.mstc.mstcapp.model.ResourceModel;
import com.mstc.mstcapp.util.ClickListener;
import com.mstc.mstcapp.util.RecyclerTouchListener;

import java.util.ArrayList;

public class ResourcesFragment extends Fragment {

    private ArrayList<ResourceModel> list;
    private RecyclerView recyclerView;

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
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        list = new ArrayList<>();

        /** ADD A NEW RESOURCE HERE **/
        list.add(new ResourceModel("Android", R.drawable.ic_app_dev));
        list.add(new ResourceModel("Frontend", R.drawable.ic_frontend));
        list.add(new ResourceModel("Backend", R.drawable.ic_backend));
        list.add(new ResourceModel("Design", R.drawable.ic_design));
        list.add(new ResourceModel("Machine Learning", R.drawable.ic_app_dev));
        list.add(new ResourceModel("Competitive Coding", R.drawable.ic_cc));
        ResourceAdapter resourceAdapter = new ResourceAdapter(getContext(), list);
        recyclerView.setAdapter(resourceAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                viewResource(list.get(position).getDomain().toLowerCase(), position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void viewResource(String type, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("domain", type);
        bundle.putInt("position", position);
        NavHostFragment.findNavController(ResourcesFragment.this).navigate(R.id.action_navigation_resources_to_navigation_view_resource_activity, bundle);
    }
}