package com.expensemanagement.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensemanagement.dto.WorkflowLevelDto;
import com.expensemanagement.dto.WorkflowRequest;
import com.expensemanagement.dto.WorkflowResponse;
import com.expensemanagement.model.ApprovalWorkflow;
import com.expensemanagement.service.WorkflowService;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:5173" })
public class WorkflowController {

	private final WorkflowService workflowService = new WorkflowService();

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<WorkflowResponse> createWorkflow(@RequestBody WorkflowRequest request) {
		try {
			ApprovalWorkflow workflow = workflowService.createWorkflow(request.getDesignation(),
					request.getApproverRoles());
			return ResponseEntity.ok(mapToWorkflowResponse(workflow));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<WorkflowResponse>> getAllWorkflows() {
		List<ApprovalWorkflow> workflows = workflowService.getAllWorkflows();
		return ResponseEntity.ok(workflows.stream().map(this::mapToWorkflowResponse).toList());
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<WorkflowResponse> getWorkflow(@PathVariable int id) {
		try {
			ApprovalWorkflow workflow = workflowService.getWorkflowById(id);
			return ResponseEntity.ok(mapToWorkflowResponse(workflow));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<WorkflowResponse> updateWorkflow(@PathVariable int id, @RequestBody WorkflowRequest request) {
		try {
			ApprovalWorkflow workflow = workflowService.updateWorkflow(id, request.getApproverRoles());
			return ResponseEntity.ok(mapToWorkflowResponse(workflow));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteWorkflow(@PathVariable int id) {
		try {
			workflowService.deleteWorkflow(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	private WorkflowResponse mapToWorkflowResponse(ApprovalWorkflow workflow) {
		WorkflowResponse response = new WorkflowResponse();
		response.setId(workflow.getApprovalWorkflowId());
		response.setDesignation(workflow.getDesignation());

		List<WorkflowLevelDto> levelDtos = workflow.getLevels().stream().map(level -> {
			WorkflowLevelDto dto = new WorkflowLevelDto();
			dto.setLevelOrder(level.getLevelOrder());
			dto.setApproverRole(level.getApproverRole());
			return dto;
		}).toList();

		response.setLevels(levelDtos);
		response.setIsActive(workflow.getIsActive());

		return response;
	}

}
