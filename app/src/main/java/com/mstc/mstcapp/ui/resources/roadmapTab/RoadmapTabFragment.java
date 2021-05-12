package com.mstc.mstcapp.ui.resources.roadmapTab;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.model.resources.RoadmapModel;

public class RoadmapTabFragment extends Fragment {

    String domain;
    ImageView imageView;
    RoadmapTabViewModel mViewModel;

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
        imageView = view.findViewById(R.id.roadmapImage);
        mViewModel = new ViewModelProvider(this).get(RoadmapTabViewModel.class);
        mViewModel.getRoadmap(domain).observe(getViewLifecycleOwner(), new Observer<RoadmapModel>() {
            @Override
            public void onChanged(RoadmapModel roadmapModel) {
                new Thread(() -> imageView.post(() -> {
                    if (roadmapModel != null) {
                        String pic = roadmapModel.getImage();
                        byte[] decodedString = Base64.decode(pic, Base64.DEFAULT);
                        Bitmap picture = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        imageView.setImageBitmap(picture);
                    } else {
                        Snackbar.make(view, "Unable to load roadmap", BaseTransientBottomBar.LENGTH_SHORT)
                                .show();
                    }
                })).start();
            }
        });
    }
}
