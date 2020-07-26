package com.mstc.mstcapp.fragments.resources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mstc.mstcapp.R;

public class RoadmapFragment extends Fragment {

    String domain;
    public RoadmapFragment(){}
    public RoadmapFragment(String domain) {
        this.domain=domain;
    }

    ImageView imageViewRoadmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_roadmap,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewRoadmap=view.findViewById(R.id.roadmapImage);
        switch (domain){
            case"android":
                imageViewRoadmap.setImageResource(R.drawable.ic_roadmap_bg);
                break;
            case "backend":
                imageViewRoadmap.setImageResource(R.drawable.ic_roadmap_backend);
                break;
            case "data science":
                imageViewRoadmap.setImageResource(R.drawable.ic_roadmap_datascience);
                break;
            case "competitive coding":
                imageViewRoadmap.setImageResource(R.drawable.ic_roadmap_compcc);
                break;
            case "design":
                imageViewRoadmap.setImageResource(R.drawable.ic_roadmap_design);
                break;
            case "frontend":
                imageViewRoadmap.setImageResource(R.drawable.ic_roadmap_frontend);
                break;
            case "flutter":
                imageViewRoadmap.setImageResource(R.drawable.ic_roadmap_flutter);
                break;
            default:
                imageViewRoadmap.setVisibility(View.INVISIBLE);
        }
    }
}
