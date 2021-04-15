package com.mstc.mstcapp.ui.resources.articleTab;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.resources.Article;

import java.util.List;

public class ArticleTabViewModel extends AndroidViewModel {
    Repository repository;
    LiveData<List<Article>> listLiveData;

    public ArticleTabViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public LiveData<List<Article>> getList(String domain) {
        listLiveData = repository.getArticles(domain);
        return listLiveData;
    }
}
