package com.hzukhruf.genbe.model.entity;

import javax.persistence.*;

import lombok.Data;


@Entity
@Data
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
}
