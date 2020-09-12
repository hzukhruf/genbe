package com.hzukhruf.genbe.model.entity;

import java.sql.Date;

import javax.persistence.*;

import lombok.Data;


@Entity
@Data
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
}
