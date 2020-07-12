package com.mstc.mstcapp.model.resources;

public class ArticleLinksObject {
    String articlelinksTitle;
    String articlelinksLink;

    public ArticleLinksObject() {
    }

    public ArticleLinksObject(String articlelinksTitle, String articlelinksLink) {
        this.articlelinksTitle = articlelinksTitle;
        this.articlelinksLink = articlelinksLink;
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
