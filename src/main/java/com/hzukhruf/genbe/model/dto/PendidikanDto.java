package com.hzukhruf.genbe.model.dto;

import lombok.Data;

@Data
public class PendidikanDto {
	private Integer idPendidikan;
	private String jenjang;
	private String institusi;
	private String tahunMasuk;
	private String tahunLulus;
}
