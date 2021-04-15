package com.mstc.mstcapp.ui.information;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.BoardMember;

import java.util.List;

public class InformationViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<BoardMember>> list;

    public InformationViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        list = repository.getBoardMembers();
    }

    public LiveData<List<BoardMember>> getList() {
        return list;
    }
}
