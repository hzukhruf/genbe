package com.hzukhruf.genbe.service;

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
		Person person = convertToEntityPerson(data);
		person.setIdPerson(data.getIdPerson());
		Biodata biodata = convertToEntityBiodata(data);
		biodata.setPerson(person);
		personRepository.save(person);
		biodataRepository.save(biodata);
		return data;
	}

	@Override
	public List<PendidikanDto> insertdataPendidikan(Integer idPerson, List<PendidikanDto> dataList) {
		dataList.forEach(data -> {
			Pendidikan pendidikan = convertToEntityPendidikan(idPerson, data);
			pendidikanRepository.save(pendidikan);
		});
		return dataList;
	}

	private Person convertToEntityPerson(PersonBioDto data) {
		Person person = new Person();
		person.setNik(data.getNik());
		person.setNama(data.getNama());
		person.setAlamat(data.getAlamat());
		return person;
	}

	private Biodata convertToEntityBiodata(PersonBioDto data) {
		Biodata biodata = new Biodata();
		biodata.setIdBio(data.getIdBio());
		biodata.setNoHp(data.getNoHp());
		biodata.setTanggalLahir(data.getTanggalLahir());
		biodata.setTempatLahir(data.getTempatLahir());
		return biodata;
	}

	private Pendidikan convertToEntityPendidikan(Integer idPerson, PendidikanDto data) {
		Pendidikan pendidikan = new Pendidikan();
		pendidikan.setIdPendidikan(data.getIdPendidikan());
		pendidikan.setJenjang(data.getJenjang());
		pendidikan.setInstitusi(data.getInstitusi());
		pendidikan.setTahunMasuk(data.getTahunMasuk());
		pendidikan.setTahunLulus(data.getTahunLulus());
		if (personRepository.findById(idPerson).isPresent()) {
			Person person = personRepository.findById(idPerson).get();
			pendidikan.setPerson(person);
		}
		return pendidikan;
	}

}
