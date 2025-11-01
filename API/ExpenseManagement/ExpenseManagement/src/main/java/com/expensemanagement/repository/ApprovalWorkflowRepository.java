package com.expensemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expensemanagement.model.ApprovalWorkflow;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalWorkflowRepository extends JpaRepository<ApprovalWorkflow, Integer> {

	Optional<ApprovalWorkflow> findByApprovalWorkflowId(Integer id);

	Optional<ApprovalWorkflow> findByDesignation(String designation);

	List<ApprovalWorkflow> findByIsActiveTrue();

}