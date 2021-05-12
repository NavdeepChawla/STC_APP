package com.mstc.mstcapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mstc.mstcapp.model.explore.BoardMember;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.explore.EventObject;
import com.mstc.mstcapp.model.explore.ProjectsObject;
import com.mstc.mstcapp.model.resources.Resource;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DatabaseDao {

    @Insert(onConflict = IGNORE)
    void insertFeeds(List<FeedObject> list);

    @Insert(onConflict = IGNORE)
    void insertFeed(FeedObject feedObject);

    @Query(value = "SELECT * FROM FEED")
    LiveData<List<FeedObject>> getFeedList();

    @Query(value = "DELETE FROM FEED")
    void deleteFeed();

    //EVENTS
    @Insert(onConflict = REPLACE)
    void insertEvents(List<EventObject> eventObjects);

    @Query("DELETE FROM EVENTS")
    void deleteEvents();

    @Query(value = "SELECT * FROM EVENTS")
    LiveData<List<EventObject>> getEventsList();

    //PROJECTS
    @Insert(entity = ProjectsObject.class, onConflict = REPLACE)
    void insertProjects(List<ProjectsObject> projectsObjects);

    @Query(value = "SELECT * FROM PROJECTS")
    LiveData<List<ProjectsObject>> getProjects();

    @Query("DELETE FROM PROJECTS")
    void deleteProjects();

    //BOARD MEMBERS
    @Insert(onConflict = REPLACE)
    void insertBoardMember(BoardMember boardMember);

    @Query(value = "SELECT * FROM BOARD")
    LiveData<List<BoardMember>> getBoardMembers();

    @Query("DELETE FROM BOARD")
    void deleteBoard();

    //RESOURCES
    @Insert(onConflict = REPLACE)
    void insertResources(List<Resource> list);

    @Query("DELETE FROM RESOURCES WHERE domain=:domain")
    void deleteResources(String domain);

    @Query("SELECT * FROM RESOURCES WHERE domain=:domain")
    LiveData<List<Resource>> getResources(String domain);


}
