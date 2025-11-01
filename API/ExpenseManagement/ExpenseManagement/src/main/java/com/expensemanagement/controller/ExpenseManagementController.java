package com.expensemanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.expensemanagement.dto.ApprovalRequest;
import com.expensemanagement.dto.ExpenseResponse;
import com.expensemanagement.dto.UserDto;
import com.expensemanagement.model.Employee;
import com.expensemanagement.model.Expense;
import com.expensemanagement.service.ExpenseManagementService;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
public class ExpenseManagementController {

	private final ExpenseManagementService expenseService = new ExpenseManagementService();

	@PostMapping
	public ResponseEntity<ExpenseResponse> submitExpense(@AuthenticationPrincipal Employee user,
			@RequestParam("title") String title, @RequestParam("description") String description,
			@RequestParam("amount") Double amount, @RequestParam("category") String category,
			@RequestParam(value = "receipt", required = false) MultipartFile receipt) {

		try {
			Expense expense = expenseService.submitExpense(user.getId(), title, description, amount, category, receipt);

			return ResponseEntity.ok(mapToExpenseResponse(expense));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/my-expenses")
	public ResponseEntity<List<ExpenseResponse>> getMyExpenses(@AuthenticationPrincipal Employee user) {
		List<Expense> expenses = expenseService.getMyExpenses(user.getId());
		return ResponseEntity.ok(expenses.stream().map(this::mapToExpenseResponse).toList());
	}

	@GetMapping("/pending")
	public ResponseEntity<List<ExpenseResponse>> getPendingExpenses(@AuthenticationPrincipal Employee user) {
		List<Expense> expenses = expenseService.getPendingExpensesForApprover(user.getId());
		return ResponseEntity.ok(expenses.stream().map(this::mapToExpenseResponse).toList());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ExpenseResponse> getExpense(@PathVariable int id) {
		try {
			Expense expense = expenseService.getExpenseById(id);
			return ResponseEntity.ok(mapToExpenseResponse(expense));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{id}/approve")
	public ResponseEntity<ExpenseResponse> approveExpense(@PathVariable int id, @AuthenticationPrincipal Employee user,
			@RequestBody ApprovalRequest request) {

		try {
			Expense expense;
			if ("APPROVE".equals(request.getAction())) {
				expense = expenseService.approveExpense(id, user.getId());
			} else {
				expense = expenseService.rejectExpense(id, user.getId(), request.getReason());
			}
			return ResponseEntity.ok(mapToExpenseResponse(expense));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	private ExpenseResponse mapToExpenseResponse(Expense expense) {
		ExpenseResponse response = new ExpenseResponse();
		response.setId(expense.getId());
		response.setTitle(expense.getTitle());
		response.setDescription(expense.getDescription());
		response.setAmount(expense.getAmount());
		response.setCategory(expense.getCategory());
		response.setSubmittedDate(expense.getCreatedOn());
		response.setReceiptFileName(expense.getReceiptFileName());
		response.setReceiptData(expense.getReceiptAttachment());
		response.setSubmitter(mapToUserDto(expense.getSubmitter()));
		response.setStatus(expense.getStatus().name());
		response.setCurrentApprovalLevel(expense.getCurrentApprovalLevel());
		response.setCurrentApprover(
				expense.getCurrentApprover() != null ? mapToUserDto(expense.getCurrentApprover()) : null);
		response.setRejectionReason(expense.getApproverComments());
		response.setLastUpdated(expense.getUpdatedOn());
		return response;
	}

	private UserDto mapToUserDto(Employee emp) {
		UserDto dto = new UserDto();
		dto.setId(emp.getId());
		dto.setEmail(emp.getEmployeeEmail());
		dto.setFullName(emp.getEmployeeFullName());
		dto.setJobTitle(emp.getDesignation());
		dto.setRole(emp.getRole().name());
		return dto;
	}

}
