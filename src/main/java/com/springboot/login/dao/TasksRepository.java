package com.springboot.login.dao;

import com.springboot.login.model.Tasks;
import com.springboot.login.stub.FetchTaskResponse;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TasksRepository extends CrudRepository<Tasks, Integer> {

    @Query(value = "select t.tasks,t.is_active,t.id from tasks t inner join users u on t.user_id = u.id where u.email = :email ", nativeQuery = true)
    public List<FetchTaskResponse> getTasksByUsername(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "update tasks t set  is_active = false  where t.id = :id AND t.user_id = (select id from users where email = :email)", nativeQuery = true)
    public int tasksCompletedByUsername(@Param("email") String email,@Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "delete from tasks t where t.user_id = (select id from users where email=:email) AND t.id = :id", nativeQuery = true)
    public int removeTasksByUsername(@Param("email") String email, @Param("id") int id);

    @Modifying
    @Transactional
    @Query(value = "update tasks t set tasks=:task,updated_date = Now() where t.id=:id AND t.user_id = (select id from users where email=:email)", nativeQuery = true)
    public int updateTasksByUsername(@Param("task")String task, @Param("id") int id,@Param("email")String email);
}
