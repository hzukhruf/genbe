package com.hzukhruf.genbe.service;

import java.util.List;

import com.hzukhruf.genbe.model.dto.DataDto3;

public interface DataPendidikanService {
	List<DataDto3> insertdataPendidikan(Integer idPerson,List<DataDto3> dataList);
}
