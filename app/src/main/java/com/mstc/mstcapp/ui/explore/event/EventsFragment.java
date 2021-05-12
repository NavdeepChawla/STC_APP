package com.mstc.mstcapp.ui.explore.event;

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
import com.mstc.mstcapp.adapter.explore.EventAdapter;
import com.mstc.mstcapp.model.explore.EventObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class EventsFragment extends Fragment {
    RecyclerView recyclerView;
    EventViewModel mViewModel;
    EventAdapter eventAdapter;
    List<EventObject> list;

    public EventsFragment() {
    }

    public static EventsFragment newInstance(int columnCount) {
        return new EventsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();
        eventAdapter = new EventAdapter(context, list);
        recyclerView.setAdapter(eventAdapter);
        mViewModel.getList().observe(getViewLifecycleOwner(), eventObjects -> {
            list = eventObjects;
            eventAdapter.setList(list);
        });
    }
}