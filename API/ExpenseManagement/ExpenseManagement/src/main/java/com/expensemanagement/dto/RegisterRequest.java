package com.expensemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {

	private String email;
	private String password;
	private String employeeFullName;
	private String designation;
	private String role; // EMPLOYEE, MANAGER, ADMIN

}
