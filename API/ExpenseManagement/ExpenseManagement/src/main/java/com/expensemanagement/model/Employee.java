package com.expensemanagement.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "mst_employee")
public class Employee implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mst_employee_id")
	private int id;

	@Column(name = "employee_name", nullable = false)
	private String employeeFullName;

	@Column(name = "employee_email", unique = true, nullable = false)
	private String employeeEmail;

	@Column(name = "designation", nullable = false)
	private String designation;

	@Column(name = "login_password", nullable = false)
	private String password;

	@Column(name = "is_active")
	private int isActive;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	public String getUsername() {
		return employeeEmail;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public Employee() {
		super();
	}

	public Employee(int id, String employeeFullName, String employeeEmail, String designation, String password,
			int isActive, Role role) {
		super();
		this.id = id;
		this.employeeFullName = employeeFullName;
		this.employeeEmail = employeeEmail;
		this.designation = designation;
		this.password = password;
		this.isActive = isActive;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeFullName() {
		return employeeFullName;
	}

	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
