package com.hzukhruf.genbe.model.dto;

import lombok.Data;

@Data
public class StatusWithDataDto {
	private boolean status;
	private String message;
	private PersonBioPendDto data;

}
