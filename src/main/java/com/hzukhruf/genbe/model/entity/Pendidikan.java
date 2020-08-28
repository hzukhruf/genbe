package com.hzukhruf.genbe.model.entity;

import javax.persistence.*;


@Entity
@Table(name="t_pendidikan")
public class Pendidikan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pendidikan")
	private Integer idPendidikan;
	
	@ManyToOne
	@JoinColumn(name="id_person",nullable = false)
	private Person person;
	
	@Column(name="jenjang", nullable = false, length = 10)
	private String jenjang;
	
	@Column(name="institusi", nullable = false, length = 50)
	private String institusi;

	@Column(name="tahun_masuk", nullable = false, length = 10)
	private String tahunMasuk;
	
	@Column(name="tahun_lulus", nullable = false, length = 10)
	private String tahunLulus;

	public Integer getIdPendidikan() {
		return idPendidikan;
	}

	public void setIdPendidikan(Integer idPendidikan) {
		this.idPendidikan = idPendidikan;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getJenjang() {
		return jenjang;
	}

	public void setJenjang(String jenjang) {
		this.jenjang = jenjang;
	}

	public String getInstitusi() {
		return institusi;
	}

	public void setInstitusi(String institusi) {
		this.institusi = institusi;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public String getTahunLulus() {
		return tahunLulus;
	}

	public void setTahunLulus(String tahunLulus) {
		this.tahunLulus = tahunLulus;
	}
}
