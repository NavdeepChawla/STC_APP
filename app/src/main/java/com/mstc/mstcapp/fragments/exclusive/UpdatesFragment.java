package com.mstc.mstcapp.fragments.exclusive;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.mstc.mstcapp.adapter.exclusive.UpdatesAdapter;
import com.mstc.mstcapp.model.exclusive.UpdatesObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UpdatesFragment extends Fragment {
    List<UpdatesObject> updatesObjectsList=new ArrayList<>();
    RecyclerView recyclerView_updates;
    DatabaseReference databaseReference_updates;

    public UpdatesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View updates_view=inflater.inflate(R.layout.fragment_update,container,false);

        recyclerView_updates=updates_view.findViewById(R.id.recyclerview_updates);
        recyclerView_updates.setLayoutManager(new LinearLayoutManager(getContext()));

        initializeData();
        return updates_view;
    }

    private void initializeData() {
        updatesObjectsList=new ArrayList<>();
        databaseReference_updates= FirebaseDatabase.getInstance().getReference().child("Updates");
        databaseReference_updates.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                    String title = Objects.requireNonNull(dataSnapshot1.child("Title").getValue()).toString();
                    String content = Objects.requireNonNull(dataSnapshot1.child("Content").getValue()).toString();
                    updatesObjectsList.add(new UpdatesObject(content, title));
                }
                final UpdatesAdapter adapter=new UpdatesAdapter(updatesObjectsList);
                recyclerView_updates.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(recyclerView_updates,"Error "+error.getMessage(),Snackbar.LENGTH_SHORT).setAnchorView(R.id.nav_view).setBackgroundTint(requireContext().getColor(R.color.colorPrimary)).setTextColor(requireContext().getColor(R.color.permWhite)).show();
            }
        });
    }
}
