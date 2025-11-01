package com.expensemanagement.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mst_expense")
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mst_expense_id")
	private int id;

	@Column(name = "description", length = 2000)
	private String description;

	@Column(name = "amount", nullable = false)
	private Double amount;

	@Lob
	@Column(name = "receipt_attachment", columnDefinition = "TEXT")
	private String receiptAttachment; // Base64 encoded receipt

	@Column(name = "receipt_file_name")
	private String receiptFileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private Employee submitter;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ExpenseStatus status;

	@Column(name = "current_approval_level")
	private Integer currentApprovalLevel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_approver_id")
	private Employee currentApprover;

	@Column(name = "approver_comments", length = 1000)
	private String approverComments;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_on")
	private Date updatedOn;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String category;

	public Expense() {
		super();
	}

	public Expense(int id, String description, Double amount, String receiptAttachment, String receiptFileName,
			Employee submitter, ExpenseStatus status, Integer currentApprovalLevel, Employee currentApprover,
			String approverComments, Date createdOn, Date updatedOn, String title, String category) {
		super();
		this.id = id;
		this.description = description;
		this.amount = amount;
		this.receiptAttachment = receiptAttachment;
		this.receiptFileName = receiptFileName;
		this.submitter = submitter;
		this.status = status;
		this.currentApprovalLevel = currentApprovalLevel;
		this.currentApprover = currentApprover;
		this.approverComments = approverComments;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
		this.title = title;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getReceiptAttachment() {
		return receiptAttachment;
	}

	public void setReceiptAttachment(String receiptAttachment) {
		this.receiptAttachment = receiptAttachment;
	}

	public String getReceiptFileName() {
		return receiptFileName;
	}

	public void setReceiptFileName(String receiptFileName) {
		this.receiptFileName = receiptFileName;
	}

	public Employee getSubmitter() {
		return submitter;
	}

	public void setSubmitter(Employee submitter) {
		this.submitter = submitter;
	}

	public ExpenseStatus getStatus() {
		return status;
	}

	public void setStatus(ExpenseStatus status) {
		this.status = status;
	}

	public Integer getCurrentApprovalLevel() {
		return currentApprovalLevel;
	}

	public void setCurrentApprovalLevel(Integer currentApprovalLevel) {
		this.currentApprovalLevel = currentApprovalLevel;
	}

	public Employee getCurrentApprover() {
		return currentApprover;
	}

	public void setCurrentApprover(Employee currentApprover) {
		this.currentApprover = currentApprover;
	}

	public String getApproverComments() {
		return approverComments;
	}

	public void setApproverComments(String approverComments) {
		this.approverComments = approverComments;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
