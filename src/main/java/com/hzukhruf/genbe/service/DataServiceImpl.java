package com.hzukhruf.genbe.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzukhruf.genbe.model.dto.PendidikanDto;
import com.hzukhruf.genbe.model.dto.PersonBioDto;
import com.hzukhruf.genbe.model.entity.Biodata;
import com.hzukhruf.genbe.model.entity.Pendidikan;
import com.hzukhruf.genbe.model.entity.Person;
import com.hzukhruf.genbe.repository.BiodataRepository;
import com.hzukhruf.genbe.repository.PendidikanRepository;
import com.hzukhruf.genbe.repository.PersonRepository;

@Service
@Transactional
public class DataServiceImpl implements DataService {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private BiodataRepository biodataRepository;
	@Autowired
	private PendidikanRepository pendidikanRepository;

	@Override
	public PersonBioDto insertData(PersonBioDto data) {
		//person
		Person person = convertToEntityPerson(data);
		personRepository.save(person);
		data.setIdPerson(person.getIdPerson());
		//biodata
		Biodata biodata = convertToEntityBiodata(data);
		biodata.setPerson(person);
		biodataRepository.save(biodata);
		//pendidikan
//		List<PendidikanDto> pendidikanDtoList= data.getPendidikanDtoList();
//		pendidikanDtoList.forEach(dataList -> {
//			Pendidikan pendidikan = convertToEntityPendidikan(dataList);
//			pendidikan.setPerson(person);
//			pendidikanRepository.save(pendidikan);
//		});
		return data;
	}
	
	private Person convertToEntityPerson(PersonBioDto data){
		Person person = new Person();
		person.setNik(data.getNik());
		person.setNama(data.getNama());
		person.setAlamat(data.getAlamat());
		return person;
	}
	
	private Biodata convertToEntityBiodata(PersonBioDto data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTanggalLahir(), formatter);
		Date bYear = Date.from(birthYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Biodata biodata = new Biodata();
		biodata.setIdBio(data.getIdBio());
		biodata.setNoHp(data.getHp());
		biodata.setTanggalLahir(bYear);
		biodata.setTempatLahir(data.getTempatLahir());
		return biodata;
	}
	
	private Pendidikan convertToEntityPendidikan(PendidikanDto data) {
		Pendidikan pendidikan = new Pendidikan();
		pendidikan.setIdPendidikan(data.getIdPendidikan());
		pendidikan.setJenjang(data.getJenjang());
		pendidikan.setInstitusi(data.getInstitusi());
		pendidikan.setTahunMasuk(data.getMasuk());
		pendidikan.setTahunLulus(data.getLulus());
		return pendidikan;
	}
}
