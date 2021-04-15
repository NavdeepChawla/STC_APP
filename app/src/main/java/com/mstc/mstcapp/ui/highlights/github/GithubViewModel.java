package com.mstc.mstcapp.ui.highlights.github;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.highlights.GithubObject;

import java.util.List;

public class GithubViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<GithubObject>> list;

    public GithubViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        list = repository.getGithub();

    }

    public LiveData<List<GithubObject>> getList() {
        return list;
    }
}
