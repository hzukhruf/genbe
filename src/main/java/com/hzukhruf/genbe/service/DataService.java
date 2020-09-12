package com.hzukhruf.genbe.service;

import java.util.List;

import com.hzukhruf.genbe.model.dto.PendidikanDto;

import com.hzukhruf.genbe.model.entity.Biodata;

public interface DataService {
	Biodata insertData(Biodata data);

	List<PendidikanDto> insertdataPendidikan(Integer idPerson, List<PendidikanDto> dataList);
}
