package com.hzukhruf.genbe.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzukhruf.genbe.model.dto.DataDto1;
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
	public DataDto1 insertData(DataDto1 data) {
		Person person = new Person();
		person.setNik(data.getNik());
		person.setNama(data.getName());
		person.setAlamat(data.getAddress());
		personRepository.save(person);
		data.setIdPerson(person.getIdPerson());

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTgl(), formatter);
		Date bYear = Date.from(birthYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Biodata biodata = new Biodata();
		biodata.setPerson(person);
		biodata.setIdBio(data.getIdBio());
		biodata.setNoHp(data.getHp());
		biodata.setTanggalLahir(bYear);
		biodata.setTempatLahir(data.getTempatLahir());
		biodataRepository.save(biodata);
		
		return data;
	}

}
