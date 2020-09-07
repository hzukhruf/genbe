package com.hzukhruf.genbe.service;

import java.util.List;

import com.hzukhruf.genbe.model.dto.PendidikanDto;

public interface DataPendidikanService {
	List<PendidikanDto> insertdataPendidikan(Integer idPerson,List<PendidikanDto> dataList);
}
