package com.mstc.mstcapp.fragments.exclusive;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.activity.NavActivity;
import com.mstc.mstcapp.adapter.exclusive.MomAdapter;
import com.mstc.mstcapp.model.exclusive.MomObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MomFragment extends Fragment {
    List<MomObject> momObjectList=new ArrayList<>();
    RecyclerView recyclerView_mom;
    DatabaseReference databaseReference_mom;
    private ProgressBar progressBar_mom;

    public MomFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mom_view=inflater.inflate(R.layout.fragment_mom,container,false);
        recyclerView_mom=mom_view.findViewById(R.id.recyclerview_mom);
        progressBar_mom=mom_view.findViewById(R.id.progressbar_mom);
        recyclerView_mom.setLayoutManager(new LinearLayoutManager(getContext()));

        initializeData();
        return mom_view;
    }

    private void initializeData() {
        momObjectList=new ArrayList<>();
        databaseReference_mom= FirebaseDatabase.getInstance().getReference().child("Mom");
        databaseReference_mom.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                    String title= Objects.requireNonNull(dataSnapshot1.child("Title").getValue()).toString();
                    String content= Objects.requireNonNull(dataSnapshot1.child("Content").getValue()).toString();
                    momObjectList.add(new MomObject(content,title));
                }
                progressBar_mom.setVisibility(View.INVISIBLE);
                final MomAdapter momAdapter=new MomAdapter(momObjectList);
                recyclerView_mom.setAdapter(momAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(recyclerView_mom,"Error "+error.getMessage(),Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
            }
        });

    }

}
