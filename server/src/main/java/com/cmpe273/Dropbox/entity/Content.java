package com.cmpe273.Dropbox.entity;


import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Entity
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String rootFolder;
    private String contentPath;
    private String contentName;
    private Integer createdBy;
    private Date createdOn;
    private Boolean star;
    @PrePersist
    protected void onCreate(){
        createdOn = new Date();
    }

    public Content(String rootFolder, String contentPath, String contentName, Integer createdBy, Boolean star) {
        this.rootFolder = rootFolder;
        this.contentPath = contentPath;
        this.contentName = contentName;
        this.createdBy = createdBy;
        this.star = star;
    }

    public Content() {
    }

    public String getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(String rootFolder) {
        this.rootFolder = rootFolder;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getStar() {
        return star;
    }

    public void setStar(Boolean star) {
        this.star = star;
    }
}
