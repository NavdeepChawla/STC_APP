package com.mstc.mstcapp.util;

import com.mstc.mstcapp.model.FeedObject;
import com.mstc.mstcapp.model.highlights.EventObject;
import com.mstc.mstcapp.model.highlights.GithubObject;
import com.mstc.mstcapp.model.highlights.ProjectsObject;
import com.mstc.mstcapp.model.resources.Article;
import com.mstc.mstcapp.model.resources.Resource;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {


    @GET("getProject")
    Call<List<ProjectsObject>> getProjects();

    @GET("getGithub")
    Call<List<GithubObject>> getGithub();

    @GET("getEvent")
    Call<List<EventObject>> getEvents();

    @GET("getFeed")
    Call<List<FeedObject>> getFeed(@Query("skip") String skip);

    @GET("getArticle/{domain}")
    Call<List<Article>> getArticles(@Path("domain") String domain);

    @GET("getResource/{domain}")
    Call<List<Resource>> getResources(@Path("domain") String domain);
}
