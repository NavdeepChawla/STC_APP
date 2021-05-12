package com.mstc.mstcapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.mstc.mstcapp.database.DatabaseDao;
import com.mstc.mstcapp.database.STCDatabase;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.explore.BoardMember;
import com.mstc.mstcapp.model.explore.EventObject;
import com.mstc.mstcapp.model.explore.ProjectsObject;
import com.mstc.mstcapp.model.resources.Resource;
import com.mstc.mstcapp.util.RetrofitInstance;
import com.mstc.mstcapp.util.RetrofitInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mstc.mstcapp.util.Functions.isNetworkAvailable;

public class Repository {
    public static final String STC_SHARED_PREFERENCES = "STC_shared_preferences";
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
        sharedPreferences = context.getSharedPreferences(STC_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
//
//    public static Repository getInstance(Context context) {
//        if (instance == null) {
//            instance = new Repository(context);
//        }
//        return instance;
//    }

    @MainThread
    public LiveData<List<EventObject>> getEvents() {
        if (isNetworkAvailable(context)) {
            Call<List<EventObject>> call = retrofitInterface.getEvents();
            call.enqueue(new Callback<List<EventObject>>() {
                @Override
                public void onResponse(@NonNull Call<List<EventObject>> call, @NonNull Response<List<EventObject>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<EventObject> events = response.body();
                        STCDatabase.databaseWriteExecutor.execute(() -> {
                            databaseDao.insertEvents(events);
                        });
                        for (EventObject event : events) {
                            Log.i(TAG, "onResponse: " + event.getDescription());
                        }
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<EventObject>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getEventsList();
    }


    @MainThread
    public LiveData<List<ProjectsObject>> getProjects() {
        if (isNetworkAvailable(context)) {
            Call<List<ProjectsObject>> call = retrofitInterface.getProjects();

            call.enqueue(new Callback<List<ProjectsObject>>() {
                @Override
                public void onResponse(@NonNull Call<List<ProjectsObject>> call, @NonNull Response<List<ProjectsObject>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<ProjectsObject> projectsObjects = response.body();
                        assert projectsObjects != null;
                        STCDatabase.databaseWriteExecutor.execute(() -> databaseDao.insertProjects(projectsObjects));
                        for (ProjectsObject object : projectsObjects) {
                            Log.i(TAG, "onResponse: " + object.getTitle());
                        }
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ProjectsObject>> call, @NonNull Throwable t) {
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
    public LiveData<List<BoardMember>> getBoardMembers() {
        long lastChecked = sharedPreferences.getLong("lastChecked", -1);
        long nextCheck = System.currentTimeMillis();
        if (lastChecked != -1) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(lastChecked));
            cal.add(Calendar.DAY_OF_MONTH, 180);
            nextCheck = cal.getTime().getTime();
        }
        if (lastChecked == -1 || nextCheck <= new Date().getTime()) {
//            databaseReference.child("Board").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        String name = getString(dataSnapshot, "name");
//                        String link = getString(dataSnapshot, "link");
//                        String position = getString(dataSnapshot, "position");
//                        String image = getString(dataSnapshot, "image");
//                        Log.d(TAG, "onDataChange() returned: " + name + " " + link + " " + position + " " + image);
//                        STCDatabase.databaseWriteExecutor.execute(() -> databaseDao.insertBoardMember(new BoardMember(image, name, position, link)));
//                    }
//                    editor.putLong("lastChecked", new Date().getTime());
//                    editor.apply();
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    Toast.makeText(context, "Couldn't fetch list of board members", Toast.LENGTH_SHORT).show();
//                    Log.e(TAG, "onCancelled: " + error);
//                }
//            });
        }
        return databaseDao.getBoardMembers();
    }

    public LiveData<List<Resource>> getResources(String domain) {
        if (isNetworkAvailable(context)) {
            Call<List<Resource>> call = retrofitInterface.getResources(domain);
            call.enqueue(new Callback<List<Resource>>() {
                @Override
                public void onResponse(@NonNull Call<List<Resource>> call, @NonNull Response<List<Resource>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<Resource> resources = response.body();
                        assert resources != null;
                        STCDatabase.databaseWriteExecutor.execute(() -> {
                            databaseDao.deleteResources(domain);
                            databaseDao.insertResources(resources);
                        });
                        for (Resource resource : resources) {
                            Log.i(TAG, "onResponse: " + resource.getTitle());
                        }
                    } else {
                        Log.d(TAG, "onResponse() returned: " + response.message());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Resource>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getResources(domain);
    }


    private String listToString(ArrayList<String> list) {
        String content = "";
        for (String str : list) {
            content += str + "\n";
        }
        return content;
    }

//    private String getString(DataSnapshot dataSnapshot, String child) {
//        return Objects.requireNonNull(dataSnapshot.child(child).getValue()).toString();
//    }

    public LiveData<List<FeedObject>> getSavedFeedList() {
        if (isNetworkAvailable(context)) {
            Call<List<FeedObject>> call = retrofitInterface.getFeed("1");
            call.enqueue(new Callback<List<FeedObject>>() {
                @Override
                public void onResponse(@NonNull Call<List<FeedObject>> call, @NonNull Response<List<FeedObject>> response) {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "onResponse: successfull");
                        Log.d(TAG, "onResponse() returned: " + response.body());
                        List<FeedObject> feeds = response.body();
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
                public void onFailure(@NonNull Call<List<FeedObject>> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: ", t);
                }
            });
        }
        return databaseDao.getFeedList();
    }

    public void insertFeed(FeedObject feedObject) {
        STCDatabase.databaseWriteExecutor.execute(() -> databaseDao.insertFeed(feedObject));
    }
}
