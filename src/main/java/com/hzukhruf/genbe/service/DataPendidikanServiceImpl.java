package com.hzukhruf.genbe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzukhruf.genbe.model.dto.PendidikanDto;
import com.hzukhruf.genbe.model.entity.Pendidikan;
import com.hzukhruf.genbe.model.entity.Person;
import com.hzukhruf.genbe.repository.PendidikanRepository;
import com.hzukhruf.genbe.repository.PersonRepository;

@Service
@Transactional
public class DataPendidikanServiceImpl implements DataPendidikanService {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private PendidikanRepository pendidikanRepository;

	@Override
	public List<PendidikanDto> insertdataPendidikan(Integer idPerson, List<PendidikanDto> dataList) {
		dataList.forEach(data -> {
			Pendidikan pendidikan = convertToEntityPendidikan(idPerson, data);
			pendidikanRepository.save(pendidikan);
		});
		return dataList;
	}

	private Pendidikan convertToEntityPendidikan(Integer idPerson, PendidikanDto data) {
		Pendidikan pendidikan = new Pendidikan();
		pendidikan.setIdPendidikan(data.getIdPendidikan());
		pendidikan.setJenjang(data.getJenjang());
		pendidikan.setInstitusi(data.getInstitusi());
		pendidikan.setTahunMasuk(data.getMasuk());
		pendidikan.setTahunLulus(data.getLulus());
		if (personRepository.findById(idPerson).isPresent()) {
			Person person = personRepository.findById(idPerson).get();
			pendidikan.setPerson(person);
		}
		return pendidikan;
	}

}
