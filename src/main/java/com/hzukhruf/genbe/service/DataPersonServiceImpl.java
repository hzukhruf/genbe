package com.hzukhruf.genbe.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzukhruf.genbe.model.dto.PersonBioDto;
import com.hzukhruf.genbe.model.entity.Biodata;
import com.hzukhruf.genbe.model.entity.Person;
import com.hzukhruf.genbe.repository.BiodataRepository;
import com.hzukhruf.genbe.repository.PersonRepository;

@Service
@Transactional
public class DataPersonServiceImpl implements DataPersonService {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private BiodataRepository biodataRepository;

	@Override
	public PersonBioDto insertData(PersonBioDto data) {
		Person person = convertToEntityPerson(data);
		personRepository.save(person);
		data.setIdPerson(person.getIdPerson());
		Biodata biodata = convertToEntityBiodata(data);
		biodata.setPerson(person);
		biodataRepository.save(biodata);
		return data;
	}
	
	private Person convertToEntityPerson(PersonBioDto data){
		Person person = new Person();
		person.setNik(data.getNik());
		person.setNama(data.getName());
		person.setAlamat(data.getAddress());
		return person;
	}
	
	private Biodata convertToEntityBiodata(PersonBioDto data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTgl(), formatter);
		Date bYear = Date.from(birthYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Biodata biodata = new Biodata();
		biodata.setIdBio(data.getIdBio());
		biodata.setNoHp(data.getHp());
		biodata.setTanggalLahir(bYear);
		biodata.setTempatLahir(data.getTempatLahir());
		return biodata;
	}
	

}
