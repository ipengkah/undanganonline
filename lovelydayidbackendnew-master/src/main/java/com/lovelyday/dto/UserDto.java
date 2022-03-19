package com.lovelyday.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class UserDto {

	private String name;
	private String userName;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String userPass;
	private String token;
	
	public UserDto(String name, String userName, String token) {
		super();
		this.name = name;
		this.userName = userName;
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "UserDto [name =" + name + ",userName=" + userName + ", token=" + token + "]";
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
