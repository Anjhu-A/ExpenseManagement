package com.expensemanagement.dto;

import java.util.List;

public class WorkflowResponse {

	private int id;
	private String designation;
	private List<WorkflowLevelDto> levels;
	private Boolean isActive;

	public WorkflowResponse() {
		super();
	}

	public WorkflowResponse(int id, String designation, List<WorkflowLevelDto> levels, Boolean isActive) {
		super();
		this.id = id;
		this.designation = designation;
		this.levels = levels;
		this.isActive = isActive;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<WorkflowLevelDto> getLevels() {
		return levels;
	}

	public void setLevels(List<WorkflowLevelDto> levels) {
		this.levels = levels;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
