package com.springboot.login.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.springboot.login.jwt.JwtUtils;
import com.springboot.login.model.Tasks;
import com.springboot.login.model.Users;
import com.springboot.login.services.TasksServices;
import com.springboot.login.services.UsersServices;
import com.springboot.login.stub.FetchTaskResponse;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {

   private static final Logger log = LoggerFactory.getLogger(TaskController.class);

   @Autowired
   private TasksServices taskService;

   @Autowired
    private UsersServices usersServices;

   @Autowired
    private JwtUtils jwtUtils;

   @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody Map<String,String> task){

        UserDetails user = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       Optional<Users> usr = usersServices.getUserFromEmail(user.getUsername());

       if(usr.isPresent()){
           Tasks taskObj = new Tasks();
           taskObj.setTasks(task.get("task"));
           taskObj.setActive(true);
           taskObj.setUser(usr.get());
           taskObj.setCreatedDate(new Date());

           String response = taskService.addTask(taskObj);

           if(response.contains("successfully")) return ResponseEntity.ok("task successfully add!");
           else return ResponseEntity.ok("technical Error occurred, null task might passed ");
       }else{

           return ResponseEntity.ok("Invalid user");
       }
   }

   @GetMapping("/fetchTasks")
    public ResponseEntity<List<FetchTaskResponse>> fetchTasks(){

       UserDetails user = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       List<FetchTaskResponse> taskLst = taskService.getTaskByUsername(user.getUsername());

       log.debug(taskLst.toString());

       return ResponseEntity.ok(taskLst);
   }

   @PostMapping("/taskCompleted")
    public ResponseEntity<String> taskCompletedByEmail(@RequestBody Map<String,Integer>body){
       UserDetails user = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       String respo = taskService.tasksCompletedByUsername(user.getUsername(),body.get("id"));

       return ResponseEntity.ok(respo);
   }

   @DeleteMapping("/removeTask")
    public ResponseEntity<String> removeTaskByEmail(@RequestBody Map<String,Integer> body){
       UserDetails user = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       String response = taskService.removeTaskByUsername(user.getUsername(),body.get("id"));

       return ResponseEntity.ok(response);
   }

   @PostMapping("/updateTask")
    public ResponseEntity<String> updateTask(@RequestBody String request){
       log.debug(request);
       UserDetails user = (UserDetails)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       try {
           ObjectMapper objectMapper = new ObjectMapper();
           JsonNode jsonNode = objectMapper.readTree(request);
           JsonNode jsonNode1 = objectMapper.readTree(jsonNode.get("request").asText());
           int id = jsonNode1.get("id").asInt();
           String task = jsonNode1.get("task").asText();

           log.debug("id :{}, task : {}",id,task);

           String response = taskService.updateTaskByUsername(jsonNode1.get("task").asText(),jsonNode1.get("id").asInt(),user.getUsername());
           return ResponseEntity.ok(response);
       }catch (Exception e){
           log.error(e.toString());
       }

       return ResponseEntity.ok("Technical Error occurred");
   }

}
