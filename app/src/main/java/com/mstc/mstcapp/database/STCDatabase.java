package com.mstc.mstcapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mstc.mstcapp.model.explore.BoardMember;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.explore.EventObject;
import com.mstc.mstcapp.model.explore.ProjectsObject;
import com.mstc.mstcapp.model.resources.Resource;
import com.mstc.mstcapp.util.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {
                FeedObject.class,
                EventObject.class,
                ProjectsObject.class,
                BoardMember.class,
                Resource.class
        },
        version = 1,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class STCDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "STCDatabase";

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    public static volatile STCDatabase INSTANCE = null;

    public static Callback callback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                DatabaseDao databaseDao = INSTANCE.databaseDao();
            });
        }
    };

    public static STCDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (STCDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, STCDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DatabaseDao databaseDao();
}
