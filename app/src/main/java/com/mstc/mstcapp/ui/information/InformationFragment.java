package com.mstc.mstcapp.ui.information;

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
import com.mstc.mstcapp.adapters.BoardMemberAdapter;
import com.mstc.mstcapp.model.BoardMember;

import java.util.ArrayList;
import java.util.List;

public class InformationFragment extends Fragment {
    InformationViewModel mViewModal;
    BoardMemberAdapter boardMemberAdapter;
    List<BoardMember> list;
    RecyclerView recyclerView;

    public InformationFragment() {
        // Required empty public constructor
    }

    public static InformationFragment newInstance(String param1, String param2) {
        return new InformationFragment();
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
        mViewModal = new ViewModelProvider(this).get(InformationViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        list = new ArrayList<>();
        boardMemberAdapter = new BoardMemberAdapter(list);
        recyclerView.setAdapter(boardMemberAdapter);

        mViewModal.getList().observe(getViewLifecycleOwner(), members -> {
            list = members;
            boardMemberAdapter.setList(list);
        });
    }
}