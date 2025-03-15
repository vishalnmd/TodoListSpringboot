package com.springboot.login.stub;

import java.util.List;

public class FetchTaskResponse {

    private String task;
    private boolean isActive;
    private int id;

    public FetchTaskResponse(String task, boolean isActive,int id){
        this.task = task;
        this.isActive = isActive;
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
