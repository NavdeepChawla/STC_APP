package com.mstc.mstcapp.model.highlights;

public class GithubObject {
    private String githubTitle;
    private String githubLink;

    public GithubObject(String githubTitle, String githubLink) {
        this.githubTitle = githubTitle;
        this.githubLink = githubLink;
    }

    public String getGithubTitle() {
        return githubTitle;
    }

    public void setGithubTitle(String githubTitle) {
        this.githubTitle = githubTitle;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public void setGithubLink(String githubLink) {
        this.githubLink = githubLink;
    }
}
