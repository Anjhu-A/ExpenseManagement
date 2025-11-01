package com.expensemanagement.dto;

public class ApprovalRequest {

	private String action; // APPROVE or REJECT
	private String reason;

	public ApprovalRequest() {
		super();
	}

	public ApprovalRequest(String action, String reason) {
		super();
		this.action = action;
		this.reason = reason;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
