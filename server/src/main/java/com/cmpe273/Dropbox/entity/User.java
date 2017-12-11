package com.cmpe273.Dropbox.entity;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer noContentCreated;
    private Integer noContentDeleted;
    private Integer noContentShared;
    @PrePersist
    protected void onCreate(){
        noContentCreated = 0;
        noContentDeleted = 0;
        noContentShared = 0;
    }
    public User(String firstName,String lastName,String email,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public Integer getNoContentCreated() {
        return noContentCreated;
    }

    public void setNoContentCreated(Integer noContentCreated) {
        this.noContentCreated = noContentCreated;
    }

    public Integer getNoContentDeleted() {
        return noContentDeleted;
    }

    public void setNoContentDeleted(Integer noContentDeleted) {
        this.noContentDeleted = noContentDeleted;
    }

    public Integer getNoContentShared() {
        return noContentShared;
    }

    public void setNoContentShared(Integer noContentShared) {
        this.noContentShared = noContentShared;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
