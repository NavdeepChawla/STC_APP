package com.mstc.mstcapp.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.mstc.mstcapp.Repository;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.util.RetrofitInstance;
import com.mstc.mstcapp.util.RetrofitInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mstc.mstcapp.util.Functions.isNetworkAvailable;

public class HomeViewModel extends AndroidViewModel {
    private static final String TAG = "HomeViewModel";
    RetrofitInterface retrofitInterface;
    Repository repository;
    Context context;
    private LiveData<List<FeedObject>> listFeed;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        listFeed = repository.getSavedFeedList();
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        context = application;
    }

    public LiveData<List<FeedObject>> getList() {
        listFeed = repository.getSavedFeedList();
        return listFeed;
    }

    public void more(int skip, SwipeRefreshLayout load) {
        if (isNetworkAvailable(context)) {
            load.setRefreshing(true);
            Call<List<FeedObject>> call = retrofitInterface.getFeed(String.valueOf(skip));
            call.enqueue(new Callback<List<FeedObject>>() {
                @Override
                public void onResponse(@NonNull Call<List<FeedObject>> call, @NonNull Response<List<FeedObject>> response) {
                    if (response.isSuccessful()) {
                        load.setRefreshing(false);
                        List<FeedObject> arrayList = response.body();
                        assert arrayList != null;
                        for (FeedObject feedObject : arrayList) {
                            repository.insertFeed(feedObject);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<FeedObject>> call, Throwable t) {
                    load.setRefreshing(false);
                    Snackbar.make(load, "Unable to connect to the Internet", BaseTransientBottomBar.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }
}