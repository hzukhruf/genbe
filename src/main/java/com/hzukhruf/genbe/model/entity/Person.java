package com.hzukhruf.genbe.model.entity;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "t_person")
@Data
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_person")
	private Integer idPerson;

	@Column(name = "nik", length = 16, nullable = false,unique = true)
	private String nik;

	@Column(name = "nama", length = 50)
	private String nama;

	@Column(name = "alamat")
	private String alamat;

}
