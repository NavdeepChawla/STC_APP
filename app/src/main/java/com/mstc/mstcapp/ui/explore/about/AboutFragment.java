package com.mstc.mstcapp.ui.explore.about;

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
import com.mstc.mstcapp.adapter.BoardMemberAdapter;
import com.mstc.mstcapp.model.explore.BoardMember;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    AboutViewModel mViewModal;
    BoardMemberAdapter boardMemberAdapter;
    List<BoardMember> list;
    RecyclerView recyclerView;
    Context context;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance(String param1, String param2) {
        return new AboutFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModal = new ViewModelProvider(this).get(AboutViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        list = new ArrayList<>();
        boardMemberAdapter = new BoardMemberAdapter(context, list);
        recyclerView.setAdapter(boardMemberAdapter);

        mViewModal.getList().observe(getViewLifecycleOwner(), members -> {
            list = members;
            boardMemberAdapter.setList(list);
        });
    }
}