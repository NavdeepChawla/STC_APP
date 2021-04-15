package com.mstc.mstcapp.ui.highlights.project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.highlights.ProjectsObject;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<ProjectsObject>> list;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        list = repository.getProjects();
    }

    public LiveData<List<ProjectsObject>> getList() {
        return list;
    }
}
