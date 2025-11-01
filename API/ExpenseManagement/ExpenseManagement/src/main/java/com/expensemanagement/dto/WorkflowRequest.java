package com.expensemanagement.dto;

import java.util.List;

public class WorkflowRequest {

	private String designation;
	private List<String> approverRoles;

	public WorkflowRequest() {
		super();
	}

	public WorkflowRequest(String designation, List<String> approverRoles) {
		super();
		this.designation = designation;
		this.approverRoles = approverRoles;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<String> getApproverRoles() {
		return approverRoles;
	}

	public void setApproverRoles(List<String> approverRoles) {
		this.approverRoles = approverRoles;
	}

}
