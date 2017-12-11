package com.cmpe273.Dropbox.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SharedContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer sharedToId;
    private Integer sharedById;
    private Integer contentId;
    private Date sharedOn;
    @PrePersist
    protected void onCreate(){
        sharedOn = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Date getSharedOn() {
        return sharedOn;
    }

    public void setSharedOn(Date sharedOn) {
        this.sharedOn = sharedOn;
    }

    public Integer getSharedToId() {
        return sharedToId;
    }

    public void setSharedToId(Integer sharedToId) {
        this.sharedToId = sharedToId;
    }

    public Integer getSharedById() {
        return sharedById;
    }

    public void setSharedById(Integer sharedById) {
        this.sharedById = sharedById;
    }
}
