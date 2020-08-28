package com.hzukhruf.genbe.model.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="t_biodata")
public class Biodata {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bio")
	private Integer idBio;
	
	@Column(name="nohp",length = 16)
	private String noHp;
	
	@Column(name="tanggal_lahir", nullable = false)
	private Date tanggalLahir;
	
	@Column(name="tempat_lahir")
	private String tempatLahir;
	
	@OneToOne
	@JoinColumn(name="id_person",unique = true,nullable = false)
	private Person person;

	public Integer getIdBio() {
		return idBio;
	}

	public void setIdBio(Integer idBio) {
		this.idBio = idBio;
	}

	public String getNoHp() {
		return noHp;
	}

	public void setNoHp(String noHp) {
		this.noHp = noHp;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
