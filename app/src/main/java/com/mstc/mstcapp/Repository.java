package com.mstc.mstcapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.database.DatabaseDao;
import com.mstc.mstcapp.database.STCDatabase;
import com.mstc.mstcapp.model.FeedModel;
import com.mstc.mstcapp.model.explore.BoardMemberModel;
import com.mstc.mstcapp.model.explore.EventModel;
import com.mstc.mstcapp.model.explore.ProjectsModel;
import com.mstc.mstcapp.model.resources.DetailModel;
import com.mstc.mstcapp.model.resources.ResourceModel;
import com.mstc.mstcapp.model.resources.RoadmapModel;
import com.mstc.mstcapp.util.Constants;
import com.mstc.mstcapp.util.RetrofitInstance;
import com.mstc.mstcapp.util.RetrofitInterface;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mstc.mstcapp.util.Functions.isNetworkAvailable;

public class Repository {
    private static final String TAG = "Repository";
    public DatabaseDao databaseDao;
    RetrofitInterface retrofitInterface;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private Repository instance;

    public Repository(Context context) {
        this.context = context;
        databaseDao = STCDatabase.getInstance(context).databaseDao();
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        sharedPreferences = context.getSharedPreferences(Constants.STC_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @MainThread
    public LiveData<List<EventModel>> getEvents() {
        if (isNetworkAvailable(context)) {
            Call<List<EventModel>> call = retrofitInterface.getEvents(1);
            call.enqueue(new Callback<List<EventModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<EventModel>> call, @NonNull Response<List<EventModel>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<EventModel> events = response.body();
                        STCDatabase.databaseWriteExecutor.execute(() -> {
                            databaseDao.insertEvents(events);
                        });
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<EventModel>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getEventsList();
    }


    @MainThread
    public LiveData<List<ProjectsModel>> getProjects() {
        if (isNetworkAvailable(context)) {
            Call<List<ProjectsModel>> call = retrofitInterface.getProjects();

            call.enqueue(new Callback<List<ProjectsModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<ProjectsModel>> call, @NonNull Response<List<ProjectsModel>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<ProjectsModel> projectsModels = response.body();
                        assert projectsModels != null;
                        STCDatabase.databaseWriteExecutor.execute(() -> databaseDao.insertProjects(projectsModels));
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ProjectsModel>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getProjects();
    }

    /**
     * As the list of board members updates only after a year, we fetch the results from the firebase realtime
     * database every six months and store it locally on our database.
     * The last date when the board was updated is stored in the shared preferences.
     *
     * @return List of board members from the database
     */
    @MainThread
    public LiveData<List<BoardMemberModel>> getBoardMembers() {
        long lastChecked = sharedPreferences.getLong("lastChecked", -1);
        long nextCheck = System.currentTimeMillis();
        if (lastChecked != -1) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(lastChecked));
            cal.add(Calendar.DAY_OF_MONTH, 180);
            nextCheck = cal.getTime().getTime();
        }
        if (lastChecked == -1 || nextCheck <= new Date().getTime()) {
            if (isNetworkAvailable(context)) {
                Call<List<BoardMemberModel>> call = retrofitInterface.getBoard();
                call.enqueue(new Callback<List<BoardMemberModel>>() {
                    @Override
                    public void onResponse(Call<List<BoardMemberModel>> call, Response<List<BoardMemberModel>> response) {
                        if (response.isSuccessful()) {
                            Log.i(TAG, "onResponse: successfull");
                            Log.d(TAG, "onResponse() returned: " + response.body());
                            List<BoardMemberModel> boardMembers = response.body();
                            assert boardMembers != null;
                            STCDatabase.databaseWriteExecutor.execute(() -> {
                                databaseDao.deleteBoard();
                                databaseDao.insertBoard(boardMembers);
                            });
                            editor.putLong("lastChecked", new Date().getTime());
                            editor.apply();
                        } else {
                            Log.e(TAG, "onResponse() returned: " + response);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BoardMemberModel>> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                    }
                });
            }
        }
        return databaseDao.getBoardMembers();
    }

    public LiveData<List<ResourceModel>> getResources(String domain) {
        if (isNetworkAvailable(context)) {
            Call<List<ResourceModel>> call = retrofitInterface.getResources(domain);
            call.enqueue(new Callback<List<ResourceModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<ResourceModel>> call, @NonNull Response<List<ResourceModel>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<ResourceModel> resourceModels = response.body();
                        assert resourceModels != null;
                        STCDatabase.databaseWriteExecutor.execute(() -> {
                            databaseDao.deleteResources(domain);
                            databaseDao.insertResources(resourceModels);
                        });
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ResourceModel>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getResources(domain);
    }

    public LiveData<RoadmapModel> getRoadmap(String domain) {
        if (isNetworkAvailable(context)) {
            Call<RoadmapModel> call = retrofitInterface.getRoadmap(domain);
            call.enqueue(new Callback<RoadmapModel>() {
                @Override
                public void onResponse(@NonNull Call<RoadmapModel> call, @NonNull Response<RoadmapModel> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        RoadmapModel roadmap = response.body();
                        assert roadmap != null;
                        STCDatabase.databaseWriteExecutor.execute(() -> {
                            databaseDao.deleteRoadmap(domain);
                            databaseDao.insertRoadmap(roadmap);
                        });
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RoadmapModel> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getRoadmap(domain);
    }

    public LiveData<List<FeedModel>> getSavedFeedList() {
        if (isNetworkAvailable(context)) {
            Call<List<FeedModel>> call = retrofitInterface.getFeed(1);
            call.enqueue(new Callback<List<FeedModel>>() {
                @Override
                public void onResponse(@NonNull Call<List<FeedModel>> call, @NonNull Response<List<FeedModel>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<FeedModel> feeds = response.body();
                        STCDatabase.databaseWriteExecutor.execute(() -> {
                            if (!MainActivity.isAppRunning) {
                                databaseDao.deleteFeed();
                                MainActivity.isAppRunning = true;
                            }
                            databaseDao.insertFeeds(feeds);
                        });
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<FeedModel>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getFeedList();
    }

    public void insertFeeds(List<FeedModel> list) {
        STCDatabase.databaseWriteExecutor.execute(() -> databaseDao.insertFeeds(list));
    }

    public void insertEvents(List<EventModel> list) {
        STCDatabase.databaseWriteExecutor.execute(() -> databaseDao.insertEvents(list));
    }

    public LiveData<DetailModel> getDetails(String domain) {
        if (isNetworkAvailable(context)) {
            Call<DetailModel> call = retrofitInterface.getDetails(domain);
            call.enqueue(new Callback<DetailModel>() {
                @Override
                public void onResponse(@NonNull Call<DetailModel> call, @NonNull Response<DetailModel> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        DetailModel details = response.body();
                        assert details != null;
                        STCDatabase.databaseWriteExecutor.execute(() -> {
                            databaseDao.deleteDetails(domain);
                            databaseDao.insertDetails(details);
                        });
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DetailModel> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getDetails(domain);
    }
}