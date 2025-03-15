package com.springboot.login.services;

import com.springboot.login.dao.TasksRepository;
import com.springboot.login.model.Tasks;
import com.springboot.login.stub.FetchTaskResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TasksServices {

    private static final Logger log = LoggerFactory.getLogger(TasksServices.class);

    @Autowired
    private TasksRepository repo;

    public String addTask(Tasks task){

        if(task!=null){
            repo.save(task);

            return "Task successfully added";
        }else{
            return "Task is null";
        }
    }

    public List<FetchTaskResponse> getTaskByUsername(String email){
        List<FetchTaskResponse> tasks = repo.getTasksByUsername(email);

        log.debug(tasks.toString());

        return tasks;

    }

    public String tasksCompletedByUsername(String email,int id){
        int result = repo.tasksCompletedByUsername(email,id);

        return result == 1 ? "task completed" : "something went wrong !";
    }

    public String removeTaskByUsername(String email,int id){
        int result = repo.removeTasksByUsername(email,id);

        return result == 1 ? "removed task successfully" : "something went wrong";
    }

    public String updateTaskByUsername(String task,int id,String email){
        int result = repo.updateTasksByUsername(task,id,email);

        return result == 1 ? "task updated successfully" : "something went wrong!";
    }
}
