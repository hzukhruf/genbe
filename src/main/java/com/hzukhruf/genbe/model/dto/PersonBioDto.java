package com.hzukhruf.genbe.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PersonBioDto {
	private Integer idPerson;
	private Integer idBio;
	private String nik;
	private String nama;
	private String alamat;
	private String noHp;
	private Date tanggalLahir;
	private String tempatLahir;
}
