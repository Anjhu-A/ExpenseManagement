package com.expensemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensemanagement.model.Expense;
import com.expensemanagement.model.ExpenseStatus;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

	List<Expense> findBySubmitterId(int submitterId);

	List<Expense> findByCurrentApproverId(int approverId);

	List<Expense> findByStatus(ExpenseStatus status);

}