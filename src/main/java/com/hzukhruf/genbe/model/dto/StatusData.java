package com.hzukhruf.genbe.model.dto;

public class StatusData {
	private boolean status;
	private String message;
	private PersonBioPendidikanDto data;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PersonBioPendidikanDto getData() {
		return data;
	}

	public void setData(PersonBioPendidikanDto data) {
		this.data = data;
	}

}
