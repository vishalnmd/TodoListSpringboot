package com.springboot.login.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.springboot.login.dao.MyRepository;
import com.springboot.login.model.Users;

import javax.swing.text.html.Option;

@Component
public class UsersServices {

	@Autowired
	MyRepository repo;
	
	public List<Users> getAllUser(){
		return (List<Users>) repo.findAll();
	}
	
	public String insertUser(Users usr) {
		
		List<Users> userLs = (List<Users>) repo.findAll();
		
		 Optional<Users> cpyUser = userLs.stream().filter(e->e.getEmail().equals(usr.getEmail())).findFirst();
		 if(cpyUser.isEmpty()) {
			 
			 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();			
			 String encryptedPassword = encoder.encode(usr.getPassword());
			 usr.setPassword(encryptedPassword);
			 
			 repo.save(usr);
			 return "User successfully registered";
		 }else {
			 return "email already exist";
		 }
	}

	public int getUserIdFromEmail(String email) {
		Optional<Users> usr =  repo.findUsersByEmail(email);

        if (usr.isEmpty()) {
            return -1;
        } else {
            return usr.get().getId();
        }
    }

	public Optional<Users> getUserFromEmail(String email){

		Optional<Users> usr = repo.findUsersByEmail(email);

		return usr;
	}
}
