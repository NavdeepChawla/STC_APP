package com.mstc.mstcapp.ui.resources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mstc.mstcapp.R;

public class RoadmapTabFragment extends Fragment {

    String domain;
    ImageView imageViewRoadmap;

    public RoadmapTabFragment() {
    }

    public RoadmapTabFragment(String domain) {
        this.domain = domain;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_roadmap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageViewRoadmap = view.findViewById(R.id.roadmapImage);
        switch (domain) {
            case "android":
                break;
            case "backend":
                break;
            case "data science":
                break;
            case "competitive coding":
                break;
            case "design":
                break;
            case "frontend":
                break;
            case "flutter":
                break;
            default:
        }
    }
}
