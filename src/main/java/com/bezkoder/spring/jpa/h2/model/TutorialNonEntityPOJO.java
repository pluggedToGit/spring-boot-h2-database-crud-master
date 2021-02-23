package com.bezkoder.spring.jpa.h2.model;

import javax.persistence.*;


public class TutorialNonEntityPOJO {
    private String title;


    private String description;


    private boolean published;


    public TutorialNonEntityPOJO(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}
