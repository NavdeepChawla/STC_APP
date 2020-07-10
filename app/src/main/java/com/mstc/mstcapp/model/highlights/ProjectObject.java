package com.mstc.mstcapp.model.highlights;

public class ProjectObject {
    private String projectTitle;
    private String projectDescription;
    private String projectLink;
    private String projectContributors;

    public ProjectObject(String projectTitle, String projectDescription, String projectLink, String projectContributors) {
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.projectLink = projectLink;
        this.projectContributors = projectContributors;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public String getProjectContributors() {
        return projectContributors;
    }

    public void setProjectContributors(String projectContributors) {
        this.projectContributors = projectContributors;
    }
}
