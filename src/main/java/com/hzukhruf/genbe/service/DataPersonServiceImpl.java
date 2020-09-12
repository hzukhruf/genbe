package com.hzukhruf.genbe.service;

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
		Person person = convertToEntityPerson(data);
		person.setIdPerson(data.getIdPerson());
		Biodata biodata = convertToEntityBiodata(data);
		biodata.setPerson(person);
		personRepository.save(person);
		biodataRepository.save(biodata);
		return data;
	}

	private Person convertToEntityPerson(DataDto1 data) {
		Person person = new Person();
		person.setNik(data.getNik());
		person.setNama(data.getName());
		person.setAlamat(data.getAddress());
		return person;
	}

	private Biodata convertToEntityBiodata(DataDto1 data) {
		Biodata biodata = new Biodata();
		biodata.setIdBio(data.getIdBio());
		biodata.setNoHp(data.getHp());
		biodata.setTanggalLahir(data.getTgl());
		biodata.setTempatLahir(data.getTempatLahir());
		return biodata;
	}

}
