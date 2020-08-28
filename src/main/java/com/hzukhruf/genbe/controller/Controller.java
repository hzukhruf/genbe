package com.hzukhruf.genbe.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.hzukhruf.genbe.model.dto.DataDto1;
import com.hzukhruf.genbe.model.dto.StatusDto;
import com.hzukhruf.genbe.model.entity.Biodata;
import com.hzukhruf.genbe.model.entity.Person;
import com.hzukhruf.genbe.repository.BiodataRepository;
import com.hzukhruf.genbe.repository.PendidikanRepository;
import com.hzukhruf.genbe.repository.PersonRepository;


@RestController
@RequestMapping("/dataPerson")
public class Controller {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private BiodataRepository biodataRepository;
	@Autowired
	private PendidikanRepository pendidikanRepository;

	private int umur(DataDto1 data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTgl(),formatter);
		LocalDate dateNow = LocalDate.now();
		Period p = Period.between(birthYear, dateNow);
		int umur = p.getYears();
		return umur;
	}
	
	@PostMapping
	public StatusDto insert(@RequestBody DataDto1 data) {
		StatusDto statusDto = new StatusDto();
		int umur = umur(data);
		if(umur<30 && data.getNik().length()!=16) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk, umur kurang dari 30 tahun dan jumlah digit nik tidak sama dengan 16");
		}else if(umur<30) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk, umur kurang dari 30 tahun");
		}else if(data.getNik().length()!=16) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk, jumlah digit nik tidak sama dengan 16");
		}
		else {
			Person person = convertToPerson(data);
			Biodata biodata = convertToBiodata(data);
			personRepository.save(person);
			biodataRepository.save(biodata);
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		}
		return statusDto;
	}

	private Person convertToPerson(DataDto1 data) {
		Person person = new Person();
		person.setIdPerson(data.getIdPerson());
		person.setNik(data.getNik());
		person.setNama(data.getName());
		person.setAlamat(data.getAddress());
		return person;
	}

	private Biodata convertToBiodata(DataDto1 data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTgl(),formatter);
		Date bYear= Date.from(birthYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Biodata biodata = new Biodata();
		biodata.setIdBio(data.getIdBio());
		biodata.setNoHp(data.getHp());
		biodata.setTanggalLahir(bYear);
		biodata.setTempatLahir(data.getTempatLahir());
		return biodata;
	}

	// @PostMapping("/{id}")
	// @GetMapping("/get")

}
