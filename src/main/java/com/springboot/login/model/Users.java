package com.springboot.login.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	private String email;
	private String password;
	private String role;

	//column will be make on some other table

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

	@Override
	public String toString() {
		return "Users{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
