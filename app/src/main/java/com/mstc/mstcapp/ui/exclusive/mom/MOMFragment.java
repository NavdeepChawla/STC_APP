package com.mstc.mstcapp.ui.exclusive.mom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapters.MOMAdapter;
import com.mstc.mstcapp.model.exclusive.MOM;

import java.util.ArrayList;
import java.util.List;

public class MOMFragment extends Fragment {
    RecyclerView recyclerView;
    MomViewModel mViewModel;
    MOMAdapter adapter;
    List<MOM> list;

    public MOMFragment() {
    }

    public static MOMFragment newInstance(int columnCount) {
        return new MOMFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MomViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();
        adapter = new MOMAdapter(list);
        recyclerView.setAdapter(adapter);
        mViewModel.getList().observe(getViewLifecycleOwner(), eventObjects -> {
            list = eventObjects;
            adapter.setList(list);
        });
    }
}