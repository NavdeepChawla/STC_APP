package com.mstc.mstcapp.ui.exclusive.attendance;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.exclusive.Attendance;

import java.util.List;

public class AttendanceViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Attendance>> list;

    public AttendanceViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        list = repository.getAttendance();
    }

    public LiveData<List<Attendance>> getList() {
        return list;
    }
}