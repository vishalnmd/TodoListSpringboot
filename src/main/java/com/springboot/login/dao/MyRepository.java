package com.springboot.login.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springboot.login.model.Users;

public interface MyRepository extends CrudRepository<Users, Integer>{

	public Optional<Users> findUsersByEmail(String user);
}
