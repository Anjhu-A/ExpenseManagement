package com.expensemanagement.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "mst_approval_workflows")
public class ApprovalWorkflow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "approval_workflow_id")
	private int approvalWorkflowId;

	@Column(nullable = false, unique = true)
	private String designation;

	@OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("levelOrder ASC")
	private List<ApprovalWorkflowLevel> levels = new ArrayList<>();

	@Column(nullable = false)
	private Boolean isActive = true;

	public ApprovalWorkflow() {
		super();
	}

	public ApprovalWorkflow(int approvalWorkflowId, String designation, List<ApprovalWorkflowLevel> levels,
			Boolean isActive) {
		super();
		this.approvalWorkflowId = approvalWorkflowId;
		this.designation = designation;
		this.levels = levels;
		this.isActive = isActive;
	}

	public int getApprovalWorkflowId() {
		return approvalWorkflowId;
	}

	public void setApprovalWorkflowId(int approvalWorkflowId) {
		this.approvalWorkflowId = approvalWorkflowId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<ApprovalWorkflowLevel> getLevels() {
		return levels;
	}

	public void setLevels(List<ApprovalWorkflowLevel> levels) {
		this.levels = levels;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
