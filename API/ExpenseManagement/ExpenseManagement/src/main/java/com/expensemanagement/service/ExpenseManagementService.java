package com.expensemanagement.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.expensemanagement.model.ApprovalWorkflow;
import com.expensemanagement.model.Employee;
import com.expensemanagement.model.Expense;
import com.expensemanagement.model.ExpenseStatus;
import com.expensemanagement.repository.ApprovalWorkflowRepository;
import com.expensemanagement.repository.EmployeeRepository;
import com.expensemanagement.repository.ExpenseRepository;

import jakarta.transaction.Transactional;

@Service
public class ExpenseManagementService {

	private final ExpenseRepository expenseRepository = null;
	private final EmployeeRepository employeeRepository = null;
	private final ApprovalWorkflowRepository workflowRepository = null;

	@Transactional
	public Expense submitExpense(int submitterId, String title, String description, Double amount, String category,
			MultipartFile receipt) throws IOException {
		Employee submitter = employeeRepository.findById(submitterId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Get workflow for submitter's job title
		ApprovalWorkflow workflow = workflowRepository.findByDesignation(submitter.getDesignation()).orElseThrow(
				() -> new RuntimeException("No workflow configured for job title: " + submitter.getDesignation()));

		if (workflow.getLevels().isEmpty()) {
			throw new RuntimeException("Workflow has no approval levels configured");
		}

		// Get first approver
		String firstApproverRole = workflow.getLevels().get(0).getApproverRole();
		List<Employee> approvers = employeeRepository.findByDesignation(firstApproverRole);

		if (approvers.isEmpty()) {
			throw new RuntimeException("No approver found for role: " + firstApproverRole);
		}

		// Encode receipt
		String receiptData = null;
		String fileName = null;
		if (receipt != null && !receipt.isEmpty()) {
			receiptData = Base64.getEncoder().encodeToString(receipt.getBytes());
			fileName = receipt.getOriginalFilename();
		}

		Expense expense = new Expense();
		expense.setTitle(title);
		expense.setDescription(description);
		expense.setAmount(amount);
		expense.setCategory(category);
		expense.setCreatedOn(new Date());
		expense.setReceiptFileName(fileName);
		expense.setReceiptAttachment(receiptData);
		expense.setCurrentApprover(approvers.get(0));
		expense.setStatus(ExpenseStatus.PENDING);
		expense.setCurrentApprovalLevel(0);
		expense.setUpdatedOn(new Date());

		return expenseRepository.save(expense);
	}

	public List<Expense> getMyExpenses(int userId) {
		return expenseRepository.findBySubmitterId(userId);
	}

	public List<Expense> getPendingExpensesForApprover(int approverId) {
		return expenseRepository.findByCurrentApproverId(approverId);
	}

	@Transactional
	public Expense approveExpense(int expenseId, int approverId) {
		Expense expense = expenseRepository.findById(expenseId)
				.orElseThrow(() -> new RuntimeException("Expense not found"));

		if (expense.getCurrentApprover().getId() != approverId) {
			throw new RuntimeException("You are not authorized to approve this expense");
		}

		if (expense.getStatus() != ExpenseStatus.PENDING) {
			throw new RuntimeException("Expense is not pending approval");
		}

		// Get workflow
		ApprovalWorkflow workflow = workflowRepository.findByDesignation(expense.getSubmitter().getDesignation())
				.orElseThrow(() -> new RuntimeException("Workflow not found"));

		int nextLevel = expense.getCurrentApprovalLevel() + 1;

		if (nextLevel >= workflow.getLevels().size()) {
			// Final approval
			expense.setStatus(ExpenseStatus.APPROVED);
			expense.setCurrentApprover(null);
		} else {
			// Move to next approval level
			String nextApproverRole = workflow.getLevels().get(nextLevel).getApproverRole();
			List<Employee> nextApprovers = employeeRepository.findByDesignation(nextApproverRole);

			if (nextApprovers.isEmpty()) {
				throw new RuntimeException("No approver found for next level");
			}

			expense.setCurrentApprovalLevel(nextLevel);
			expense.setCurrentApprover(nextApprovers.get(0));
		}

		expense.setUpdatedOn(new Date());
		return expenseRepository.save(expense);
	}

	@Transactional
	public Expense rejectExpense(int expenseId, int approverId, String reason) {
		Expense expense = expenseRepository.findById(expenseId)
				.orElseThrow(() -> new RuntimeException("Expense not found"));

		if (expense.getCurrentApprover().getId() != approverId) {
			throw new RuntimeException("You are not authorized to reject this expense");
		}

		if (expense.getStatus() != ExpenseStatus.PENDING) {
			throw new RuntimeException("Expense is not pending approval");
		}

		expense.setStatus(ExpenseStatus.REJECTED);
		expense.setApproverComments(reason);
		expense.setCurrentApprover(null);
		expense.setUpdatedOn(new Date());
		;

		return expenseRepository.save(expense);
	}

	public Expense getExpenseById(int expenseId) {
		return expenseRepository.findById(expenseId).orElseThrow(() -> new RuntimeException("Expense not found"));
	}

}
