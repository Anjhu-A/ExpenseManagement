package com.expensemanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.expensemanagement.model.ApprovalWorkflow;
import com.expensemanagement.model.ApprovalWorkflowLevel;
import com.expensemanagement.repository.ApprovalWorkflowRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkflowService {

	private final ApprovalWorkflowRepository workflowRepository = null;

	@Transactional
	public ApprovalWorkflow createWorkflow(String jobTitle, List<String> approverRoles) {
		// Check if workflow already exists
		workflowRepository.findByDesignation(jobTitle).ifPresent(w -> {
			throw new RuntimeException("Workflow already exists for job title: " + jobTitle);
		});

		ApprovalWorkflow workflow = new ApprovalWorkflow();
		workflow.setDesignation(jobTitle);
		workflow.setIsActive(true);
		workflow.setLevels(new ArrayList<>());

		for (int i = 0; i < approverRoles.size(); i++) {
			ApprovalWorkflowLevel level = new ApprovalWorkflowLevel();
			level.setWorkflow(workflow);
			level.setLevelOrder(i);
			level.setApproverRole(jobTitle);
			workflow.getLevels().add(level);
		}

		return workflowRepository.save(workflow);
	}

	@Transactional
	public ApprovalWorkflow updateWorkflow(Integer workflowId, List<String> approverRoles) {
		ApprovalWorkflow workflow = workflowRepository.findByApprovalWorkflowId(workflowId)
				.orElseThrow(() -> new RuntimeException("Workflow not found"));

		workflow.getLevels().clear();

		for (int i = 0; i < approverRoles.size(); i++) {
			ApprovalWorkflowLevel level = new ApprovalWorkflowLevel();
			level.setWorkflow(workflow);
			level.setLevelOrder(i);
			level.setApproverRole(approverRoles.get(i));
			workflow.getLevels().add(level);
			workflow.getLevels().add(level);
		}

		return workflowRepository.save(workflow);
	}

	public List<ApprovalWorkflow> getAllWorkflows() {
		return workflowRepository.findAll();
	}

	public ApprovalWorkflow getWorkflowById(int id) {
		return workflowRepository.findByApprovalWorkflowId(id)
				.orElseThrow(() -> new RuntimeException("Workflow not found"));
	}

	@Transactional
	public void deleteWorkflow(int workflowId) {
		workflowRepository.deleteById(workflowId);
	}

}
