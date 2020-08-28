package com.hzukhruf.genbe.model.dto;

public class Status2 {
	private boolean status;
	private String message;
	private DataDto2 data;

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

	public DataDto2 getData() {
		return data;
	}

	public void setData(DataDto2 data) {
		this.data = data;
	}

}
