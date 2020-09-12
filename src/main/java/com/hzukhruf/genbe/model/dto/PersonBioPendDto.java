package com.hzukhruf.genbe.model.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class PersonBioPendDto {
	private String nik;
	private String nama;
	private String alamat;
	private String noHp;
	private Date tanggalLahir;
	private String tempatLahir;
	private Integer umur;
	private String pendidikanTerakhir;
}
