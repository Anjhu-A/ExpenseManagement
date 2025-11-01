package com.expensemanagement.dto;

public class AuthResponse {
	private String token;
	private int employeeId;
	private String email;
	private String employeeFullName;
	private String designation;
	private String role;

	public AuthResponse() {
		super();
	}

	public AuthResponse(String token, int employeeId, String email, String employeeFullName, String designation,
			String role) {
		super();
		this.token = token;
		this.employeeId = employeeId;
		this.email = email;
		this.employeeFullName = employeeFullName;
		this.designation = designation;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
