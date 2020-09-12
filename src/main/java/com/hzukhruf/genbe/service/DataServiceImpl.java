package com.hzukhruf.genbe.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzukhruf.genbe.model.dto.PendidikanDto;
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
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Biodata insertData(Biodata data) {
		personRepository.save(data.getPerson());
		biodataRepository.save(data);
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


	private Pendidikan convertToEntityPendidikan(Integer idPerson, PendidikanDto data) {
		Pendidikan pendidikan = modelMapper.map(data, Pendidikan.class);
		if (personRepository.findById(idPerson).isPresent()) {
			Person person = personRepository.findById(idPerson).get();
			pendidikan.setPerson(person);
		}
		return pendidikan;
	}

}
