package com.mstc.mstcapp.ui.resources.detailsTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mstc.mstcapp.R;


public class DetailsFragment extends Fragment {
    DetailsViewModel mViewModel;
    private String domain;
    private TextView details, salary;

    public DetailsFragment() {
    }

    public DetailsFragment(String domain) {
        this.domain = domain;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        details = view.findViewById(R.id.details);
        salary = view.findViewById(R.id.salary);
        mViewModel.getDetails(domain).observe(getViewLifecycleOwner(), detailModel -> {
            if (detailModel != null) {
                details.setText(detailModel.getDescription());
                salary.setText(detailModel.getExpectation());
            }
        });
    }
}