package com.hzukhruf.genbe.service;

import java.util.List;

import com.hzukhruf.genbe.model.dto.PendidikanDto;
import com.hzukhruf.genbe.model.dto.PersonBioDto;

public interface DataService {
	PersonBioDto insertData(PersonBioDto dataDto1);

	List<PendidikanDto> insertdataPendidikan(Integer idPerson, List<PendidikanDto> dataList);
}
