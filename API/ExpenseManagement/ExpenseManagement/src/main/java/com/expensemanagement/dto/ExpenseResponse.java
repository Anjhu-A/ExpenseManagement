package com.expensemanagement.dto;

import java.util.Date;

public class ExpenseResponse {
	private int id;
	private String title;
	private String description;
	private Double amount;
	private String category;
	private Date submittedDate;
	private String receiptFileName;
	private String receiptData;
	private UserDto submitter;
	private String status;
	private Integer currentApprovalLevel;
	private UserDto currentApprover;
	private String rejectionReason;
	private Date lastUpdated;

	public ExpenseResponse() {
		super();
	}

	public ExpenseResponse(int id, String title, String description, Double amount, String category, Date submittedDate,
			String receiptFileName, String receiptData, UserDto submitter, String status, Integer currentApprovalLevel,
			UserDto currentApprover, String rejectionReason, Date lastUpdated) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.category = category;
		this.submittedDate = submittedDate;
		this.receiptFileName = receiptFileName;
		this.receiptData = receiptData;
		this.submitter = submitter;
		this.status = status;
		this.currentApprovalLevel = currentApprovalLevel;
		this.currentApprover = currentApprover;
		this.rejectionReason = rejectionReason;
		this.lastUpdated = lastUpdated;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getReceiptFileName() {
		return receiptFileName;
	}

	public void setReceiptFileName(String receiptFileName) {
		this.receiptFileName = receiptFileName;
	}

	public String getReceiptData() {
		return receiptData;
	}

	public void setReceiptData(String receiptData) {
		this.receiptData = receiptData;
	}

	public UserDto getSubmitter() {
		return submitter;
	}

	public void setSubmitter(UserDto submitter) {
		this.submitter = submitter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCurrentApprovalLevel() {
		return currentApprovalLevel;
	}

	public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
		this.currentApprovalLevel = currentApprovalLevel;
	}

	public UserDto getCurrentApprover() {
		return currentApprover;
	}

	public void setCurrentApprover(UserDto currentApprover) {
		this.currentApprover = currentApprover;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
