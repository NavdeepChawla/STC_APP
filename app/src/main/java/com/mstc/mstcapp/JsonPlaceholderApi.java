package com.mstc.mstcapp;

import com.mstc.mstcapp.model.highlights.EventObject;
import com.mstc.mstcapp.model.highlights.GithubObject;
import com.mstc.mstcapp.model.highlights.ProjectsObject;
import com.mstc.mstcapp.model.resources.ArticleLinksObject;
import com.mstc.mstcapp.model.resources.ResourcesFolderObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JsonPlaceholderApi {


    @GET("projects")
    Call<List<ProjectsObject>> getProjects();

    @GET("github")
    Call<List<GithubObject>> getGithub();

    @GET("events")
    Call<List<EventObject>> getEvents();

    @GET()
    Call<List<ArticleLinksObject>>  getArticleLinksObject(@Url String url);

    @GET()
    Call<List<ResourcesFolderObject>> getResourcesFolderObject(@Url String url);
}
