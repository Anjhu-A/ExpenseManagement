package com.expensemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "approval_workflow_level")
public class ApprovalWorkflowLevel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int approval_workflow_level_id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "workflow_id", nullable = false)
	private ApprovalWorkflow workflow;

	@Column(nullable = false)
	private Integer levelOrder;

	@Column(nullable = false)
	private String approverRole; // Designation of the approver

	public ApprovalWorkflowLevel() {
		super();
	}

	public ApprovalWorkflowLevel(int approval_workflow_level_id, ApprovalWorkflow workflow, Integer levelOrder,
			String approverRole) {
		super();
		this.approval_workflow_level_id = approval_workflow_level_id;
		this.workflow = workflow;
		this.levelOrder = levelOrder;
		this.approverRole = approverRole;
	}

	public int getApproval_workflow_level_id() {
		return approval_workflow_level_id;
	}

	public void setApproval_workflow_level_id(int approval_workflow_level_id) {
		this.approval_workflow_level_id = approval_workflow_level_id;
	}

	public ApprovalWorkflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(ApprovalWorkflow workflow) {
		this.workflow = workflow;
	}

	public Integer getLevelOrder() {
		return levelOrder;
	}

	public void setLevelOrder(Integer levelOrder) {
		this.levelOrder = levelOrder;
	}

	public String getApproverRole() {
		return approverRole;
	}

	public void setApproverRole(String approverRole) {
		this.approverRole = approverRole;
	}

}
