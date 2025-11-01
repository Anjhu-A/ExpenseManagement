package com.expensemanagement.dto;

public class WorkflowLevelDto {

	private Integer levelOrder;
	private String approverRole;

	public WorkflowLevelDto() {
		super();
	}

	public WorkflowLevelDto(Integer levelOrder, String approverRole) {
		super();
		this.levelOrder = levelOrder;
		this.approverRole = approverRole;
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
