package com.expensemanagement.dto;

public class UserDto {

	private int id;
	private String email;
	private String fullName;
	private String jobTitle;
	private String role;

	public UserDto() {
		super();
	}

	public UserDto(int id, String email, String fullName, String jobTitle, String role) {
		super();
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.jobTitle = jobTitle;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
