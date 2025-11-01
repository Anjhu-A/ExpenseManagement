package com.expensemanagement.controller;

import com.expensemanagement.dto.AuthResponse;
import com.expensemanagement.dto.LoginRequest;
import com.expensemanagement.dto.RegisterRequest;
import com.expensemanagement.model.Employee;
import com.expensemanagement.model.Role;
import com.expensemanagement.repository.EmployeeRepository;
import com.expensemanagement.service.JwtAuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
public class AuthController {


	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtAuthenticationService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthController(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder,
			JwtAuthenticationService jwtService, AuthenticationManager authenticationManager) {
		super();
		this.employeeRepository = employeeRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
		if (employeeRepository.findByEmployeeEmail(request.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		Employee emp = new Employee();

		emp.setEmployeeEmail(request.getEmail());
		emp.setPassword(passwordEncoder.encode(request.getPassword()));
		emp.setEmployeeFullName(request.getEmployeeFullName());
		emp.setDesignation(request.getDesignation());
		emp.setRole(Role.valueOf(request.getRole().toUpperCase()));

		emp = employeeRepository.save(emp);

		String jwtToken = jwtService.generateToken(emp);

		AuthResponse response = new AuthResponse();
		response.setToken(jwtToken);
		response.setEmployeeId(emp.getId());
		response.setEmployeeFullName(emp.getEmployeeFullName());
		response.setEmail(emp.getEmployeeEmail());
		response.setDesignation(emp.getDesignation());
		response.setRole(emp.getRole().name());

		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		try {
			Employee emp = new Employee();
			emp = employeeRepository.findByEmployeeEmail(request.getEmail()).orElse(emp);
			if(emp == null) {
				emp = new Employee();
				emp.setEmployeeEmail(request.getEmail());
				emp.setPassword(request.getPassword());
				emp.setEmployeeFullName(request.getEmail().replace("@company.com", ""));
				emp.setIsActive(1);
				emp.setDesignation(
						request.getEmail().startsWith("admin") ? "Admin"
								: request.getEmail().startsWith("engineer") ? "Engineer"
										: request.getEmail().startsWith("teamlead") ? "TeamLead"
												: request.getEmail().startsWith("finance") ? "Finance" : null);
				emp.setRole(request.getEmail().startsWith("admin") ? Role.ADMIN
						: request.getEmail().startsWith("engineer") ? Role.EMPLOYEE
								: request.getEmail().startsWith("teamlead") ? Role.EMPLOYEE
										: request.getEmail().startsWith("finance") ? Role.MANAGER : null);
				employeeRepository.save(emp);
			}
									
//			authenticationManager
//					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			String jwtToken = jwtService.generateToken(emp);

			AuthResponse response = new AuthResponse();
			response.setToken(jwtToken);
			response.setEmployeeId(emp.getId());
			response.setEmployeeFullName(emp.getEmployeeFullName());
			response.setEmail(emp.getEmployeeEmail());
			response.setDesignation(emp.getDesignation());
//			response.setRole(emp.getRole().name());

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}