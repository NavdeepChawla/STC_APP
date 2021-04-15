package com.mstc.mstcapp.ui.resources.resourceTab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.resources.Resource;

import java.util.List;

public class ResourceTabViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Resource>> listLiveData;

    public ResourceTabViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Resource>> getList(String domain) {
        listLiveData = repository.getResources(domain);
        return listLiveData;
    }
}
