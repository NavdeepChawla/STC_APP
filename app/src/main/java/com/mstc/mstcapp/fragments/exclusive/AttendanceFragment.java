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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mstc.mstcapp.R;
import com.mstc.mstcapp.adapter.exclusive.AttendanceAdapter;
import com.mstc.mstcapp.model.exclusive.AttendanceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttendanceFragment extends Fragment {
    List<AttendanceObject> attendanceObjects_list=new ArrayList<>();
    List<String> attendancecontent=new ArrayList<>();
    RecyclerView recyclerView_attendance;
    DatabaseReference databaseReference_attendance;

    public AttendanceFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View attendance_view=inflater.inflate(R.layout.fragment_attendance,container,false);
        recyclerView_attendance=attendance_view.findViewById(R.id.recyclerview_attendance);
        recyclerView_attendance.setLayoutManager(new LinearLayoutManager(getContext()));
        initializedata();
        return attendance_view;
    }

    private void initializedata() {
        attendanceObjects_list=new ArrayList<>();
        databaseReference_attendance=FirebaseDatabase.getInstance().getReference().child("Attendance");
        databaseReference_attendance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1:snapshot.getChildren()) {
                    String title= Objects.requireNonNull(dataSnapshot1.child("Title").getValue()).toString();
                    attendancecontent= (List<String>) dataSnapshot1.child("Content").getValue();
                    attendanceObjects_list.add(new AttendanceObject(attendancecontent,title));
                }
                final AttendanceAdapter attendanceAdapter=new AttendanceAdapter(attendanceObjects_list);
                recyclerView_attendance.setAdapter(attendanceAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.attendanceSwipeRefresh);
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
