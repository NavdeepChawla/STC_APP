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
import com.mstc.mstcapp.adapter.resources.ResourcesFolderAdapter;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;

import java.util.ArrayList;
import java.util.List;

public class ResourcesFolderFragment extends Fragment {
    RecyclerView resourcesfolderRecyclerview;
    List<ResourcesFolderObject> resourcesFolderObjectsList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_resourcesfolder,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resourcesfolderRecyclerview=view.findViewById(R.id.resourcesfolder_recyclerview);
        resourcesFolderObjectsList=new ArrayList<>();
        resourcesFolderObjectsList.add(new ResourcesFolderObject("Activity & Lifecycle","https://medium.com/student-technical-community-vit-vellore"));
        resourcesFolderObjectsList.add(new ResourcesFolderObject("Shared Preferences","https://medium.com/student-technical-community-vit-vellore"));
        resourcesFolderObjectsList.add(new ResourcesFolderObject("Firebase","https://medium.com/student-technical-community-vit-vellore"));
        resourcesfolderRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        ResourcesFolderAdapter adapter=new ResourcesFolderAdapter(resourcesFolderObjectsList);
        resourcesfolderRecyclerview.setAdapter(adapter);

    }
}
