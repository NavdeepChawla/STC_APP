package com.mstc.mstcapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mstc.mstcapp.model.BoardMember;
import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.exclusive.Attendance;
import com.mstc.mstcapp.model.exclusive.MOM;
import com.mstc.mstcapp.model.exclusive.Updates;
import com.mstc.mstcapp.model.highlights.EventObject;
import com.mstc.mstcapp.model.highlights.GithubObject;
import com.mstc.mstcapp.model.highlights.ProjectsObject;
import com.mstc.mstcapp.model.resources.Article;
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

    //GITHUB
    @Insert(entity = GithubObject.class, onConflict = REPLACE)
    void insertGithub(List<GithubObject> githubObjects);

    @Query(value = "SELECT * FROM GITHUB")
    LiveData<List<GithubObject>> getGithub();

    @Query("DELETE FROM GITHUB")
    void deleteGithub();

    //PROJECTS
    @Insert(entity = ProjectsObject.class, onConflict = REPLACE)
    void insertProjects(List<ProjectsObject> projectsObjects);

    @Query(value = "SELECT * FROM PROJECTS")
    LiveData<List<ProjectsObject>> getProjects();

    @Query("DELETE FROM GITHUB")
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

    //ARTICLES
    @Insert(onConflict = REPLACE)
    void insertArticles(List<Article> list);

    @Query("SELECT * FROM ARTICLES WHERE domain=:domain")
    LiveData<List<Article>> getArticles(String domain);

    @Query("DELETE FROM ARTICLES WHERE domain=:domain")
    void deleteArticles(String domain);

    //MOM
    @Insert(onConflict = REPLACE)
    void insertMOM(MOM mom);

    @Query("SELECT * FROM MOM")
    LiveData<List<MOM>> getMOM();

    @Query("DELETE FROM MOM")
    void deleteMOM();

    //ATTENDANCE
    @Insert(onConflict = REPLACE)
    void insertAttendance(Attendance attendance);

    @Query("SELECT * FROM ATTENDANCE ORDER BY title")
    LiveData<List<Attendance>> getAttendance();

    @Query("DELETE FROM ATTENDANCE")
    void deleteAttendance();

    //UPDATES
    @Insert(onConflict = REPLACE)
    void insertUpdate(Updates update);

    @Query("SELECT * FROM UPDATES")
    LiveData<List<Updates>> getUpdates();

    @Query("DELETE FROM UPDATES")
    void deleteUpdates();

}
