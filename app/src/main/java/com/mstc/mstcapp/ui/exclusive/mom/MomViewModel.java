package com.mstc.mstcapp.ui.exclusive.mom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.exclusive.MOM;

import java.util.List;

public class MomViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<MOM>> list;

    public MomViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        list = repository.getMOM();
    }

    public LiveData<List<MOM>> getList() {
        return list;
    }
}