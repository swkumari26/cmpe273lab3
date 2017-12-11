package com.cmpe273.Dropbox.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String groupName;
    private Integer memberId;
    private Date addedOn;
    @PrePersist
    protected void onCreate(){
        addedOn = new Date();
    }

    public UserGroup(String groupName, Integer memberId) {
        this.groupName = groupName;
        this.memberId = memberId;
    }

    public UserGroup() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Date getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }
}
