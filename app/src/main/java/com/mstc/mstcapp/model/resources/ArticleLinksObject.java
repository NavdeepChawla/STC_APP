package com.mstc.mstcapp.model.resources;

import com.google.gson.annotations.SerializedName;

public class ArticleLinksObject {
    @SerializedName("name")
    String articlelinksTitle;
    @SerializedName("link")
    String articlelinksLink;
    @SerializedName("desc")
    String articlelinksDesc;

    public ArticleLinksObject() {
    }

    public ArticleLinksObject(String articlelinksTitle, String articlelinksLink, String articlelinksDesc) {
        this.articlelinksTitle = articlelinksTitle;
        this.articlelinksLink = articlelinksLink;
        this.articlelinksDesc = articlelinksDesc;
    }

    public String getArticlelinksDesc() {
        return articlelinksDesc;
    }

    public void setArticlelinksDesc(String articlelinksDesc) {
        this.articlelinksDesc = articlelinksDesc;
    }

    public String getArticlelinksTitle() {
        return articlelinksTitle;
    }

    public void setArticlelinksTitle(String articlelinksTitle) {
        this.articlelinksTitle = articlelinksTitle;
    }

    public String getArticlelinksLink() {
        return articlelinksLink;
    }

    public void setArticlelinksLink(String articlelinksLink) {
        this.articlelinksLink = articlelinksLink;
    }
}
