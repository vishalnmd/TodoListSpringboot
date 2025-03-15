package com.springboot.login.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tasks;
    private boolean isActive;
    private Date createdDate;
    private Date updatedDate;

    @ManyToOne(targetEntity = Users.class)
    private Users user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "id=" + id +
                ", tasks='" + tasks + '\'' +
                ", isActive=" + isActive +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", user=" + user +
                '}';
    }
}
