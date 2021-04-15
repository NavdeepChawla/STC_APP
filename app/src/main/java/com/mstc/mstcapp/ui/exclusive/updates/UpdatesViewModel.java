package com.mstc.mstcapp.ui.exclusive.updates;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.exclusive.Updates;

import java.util.List;

public class UpdatesViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Updates>> list;

    public UpdatesViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        list = repository.getUpdates();
    }

    public LiveData<List<Updates>> getList() {
        return list;
    }
}