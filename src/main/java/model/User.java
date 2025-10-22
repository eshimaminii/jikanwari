package model;

import java.time.LocalDate;

public class User {
	private String userId;
	private String password;
	private String name;
	private LocalDate birthday;
	
	public User() {	}
	
	public User(String userId, String password, String name, LocalDate birthday) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.birthday = birthday;
	}

	public User(String userId, String password) {
		this.userId = userId;
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
}

