package com.hzukhruf.genbe.model.dto;

import java.util.List;

public class PersonBioDto {
	private Integer idPerson;
	private Integer idBio;
	private String nik;
	private String nama;
	private String alamat;
	private String hp;
	private String tanggalLahir;
	private String tempatLahir;
	//private List<PendidikanDto> pendidikanDtoList;
	
	public Integer getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}
	public Integer getIdBio() {
		return idBio;
	}
	public void setIdBio(Integer idBio) {
		this.idBio = idBio;
	}
	public String getNik() {
		return nik;
	}
	public void setNik(String nik) {
		this.nik = nik;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public String getHp() {
		return hp;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public String getTanggalLahir() {
		return tanggalLahir;
	}
	public void setTanggalLahir(String tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}
	public String getTempatLahir() {
		return tempatLahir;
	}
	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}
//	public List<PendidikanDto> getPendidikanDtoList() {
//		return pendidikanDtoList;
//	}
//	public void setPendidikanDtoList(List<PendidikanDto> pendidikanDtoList) {
//		this.pendidikanDtoList = pendidikanDtoList;
//	}
	
	
	

	

}
