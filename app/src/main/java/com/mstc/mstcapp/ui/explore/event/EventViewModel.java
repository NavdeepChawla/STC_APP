package com.mstc.mstcapp.ui.explore.event;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.explore.EventObject;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<EventObject>> list;

    public EventViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        list = repository.getEvents();
    }

    public LiveData<List<EventObject>> getList() {
        return list;
    }
}
