package com.mstc.mstcapp.fragments.exclusives;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.exclusives.UpdatesAdapter;
import com.mstc.mstcapp.model.exclusives.UpdatesObject;

import java.util.ArrayList;
import java.util.List;

public class UpdatesFragment extends Fragment {
    List<UpdatesObject> updatesObjectsList=new ArrayList<>();
    RecyclerView recyclerView_updates;
    DatabaseReference databaseReference_updates;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View updates_view=inflater.inflate(R.layout.fragment_update,container,false);

        recyclerView_updates=updates_view.findViewById(R.id.recyclerview_updates);
        recyclerView_updates.setLayoutManager(new LinearLayoutManager(getContext()));

        initializedata();
        return updates_view;
    }

    private void initializedata() {
        updatesObjectsList=new ArrayList<>();
        databaseReference_updates= FirebaseDatabase.getInstance().getReference().child("Updates");
        databaseReference_updates.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                    String title = dataSnapshot1.child("Title").getValue().toString();
                    String content = dataSnapshot1.child("Content").getValue().toString();
                    updatesObjectsList.add(new UpdatesObject(content, title));
                }
                final UpdatesAdapter adapter=new UpdatesAdapter(updatesObjectsList);
                recyclerView_updates.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.updatesSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
