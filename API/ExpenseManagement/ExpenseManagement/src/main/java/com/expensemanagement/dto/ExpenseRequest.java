package com.expensemanagement.dto;

public class ExpenseRequest {
	private String title;
	private String description;
	private Double amount;
	private String category;

	public ExpenseRequest() {
		super();
	}

	public ExpenseRequest(String title, String description, Double amount, String category) {
		super();
		this.title = title;
		this.description = description;
		this.amount = amount;
		this.category = category;
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
}
