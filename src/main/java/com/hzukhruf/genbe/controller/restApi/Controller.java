package com.hzukhruf.genbe.controller.restApi;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hzukhruf.genbe.model.dto.PersonBioDto;
import com.hzukhruf.genbe.model.dto.PersonBioPendDto;
import com.hzukhruf.genbe.model.dto.PendidikanDto;
import com.hzukhruf.genbe.model.dto.StatusWithDataDto;
import com.hzukhruf.genbe.model.dto.StatusDto;
import com.hzukhruf.genbe.model.entity.Biodata;
import com.hzukhruf.genbe.model.entity.Person;
import com.hzukhruf.genbe.repository.BiodataRepository;
import com.hzukhruf.genbe.repository.PendidikanRepository;
import com.hzukhruf.genbe.repository.PersonRepository;
import com.hzukhruf.genbe.service.DataService;

@RestController
@RequestMapping("/dataPerson")
public class Controller {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private BiodataRepository biodataRepository;
	@Autowired
	private PendidikanRepository pendidikanRepository;
	@Autowired
	private DataService dataService;
	@Autowired
	private ModelMapper modelMapper;

	private int umur(PersonBioDto data) {
		LocalDate birthYear = data.getTanggalLahir().toLocalDate();
		LocalDate dateNow = LocalDate.now();
		Period p = Period.between(birthYear, dateNow);
		int umur = p.getYears();
		return umur;
	}

	// insert data person
	// Insert Tanggal dengan format dd-Month-yyyy (Month full name in english)
	@PostMapping
	public StatusDto insert(@RequestBody PersonBioDto data) {
		StatusDto statusDto = new StatusDto();
		int umur = umur(data);
		if (umur < 30 && data.getNik().length() != 16) {
			statusDto.setStatus(false);
			statusDto.setMessage(
					"data gagal masuk, umur kurang dari 30 tahun dan jumlah digit nik tidak sama dengan 16");
		} else if (umur < 30) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk, umur kurang dari 30 tahun");
		} else if (data.getNik().length() != 16) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk, jumlah digit nik tidak sama dengan 16");
		} else {
			Person person = modelMapper.map(data, Person.class);
			Biodata biodata = modelMapper.map(data, Biodata.class);
			biodata.setPerson(person);
			dataService.insertData(biodata);
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		}
		return statusDto;
	}

	// insert data pendidikan berdasarkan id person
	@PostMapping("/insertPendidikan")
	public StatusDto pendidikan(@RequestParam Integer idPerson, @RequestBody List<PendidikanDto> dataList) {
		StatusDto statusDto = new StatusDto();
		try {
			dataService.insertdataPendidikan(idPerson, dataList);
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		} catch (Exception e) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk");
		}
		return statusDto;
	}

	@GetMapping("/getData")
	public List<PersonBioDto> getData() {
		List<PersonBioDto> dataDtoList = new ArrayList<>();
		List<Person> persons = personRepository.findAll();
		for (Person person : persons) {
			Biodata biodata = biodataRepository.findByPersonIdPerson(person.getIdPerson());
			PersonBioDto dto = modelMapper.map(biodata, PersonBioDto.class);
			modelMapper.map(biodata.getPerson(), dto);
			dataDtoList.add(dto);
		}
		return dataDtoList;
	}

	// Get mapping by nik
	@GetMapping("/{nik}")
	public List<Object> getByNik(@PathVariable String nik) {
		List<Object> values = new ArrayList<>();
		StatusWithDataDto status = new StatusWithDataDto();
		StatusDto statusDto = new StatusDto();
		if (nik.length() == 16) {
			if (personRepository.findByNik(nik).isEmpty() == false) {
				Person person = personRepository.findByNik(nik).get(0);
				Integer id = person.getIdPerson();
				Biodata biodata = biodataRepository.findAllByPersonIdPerson(id);
				PersonBioPendDto dataDto2 = convertToDto(person, biodata);
				// set status message
				status.setStatus(true);
				status.setMessage("success");
				status.setData(dataDto2);
				values.add(status);
			} else {
				statusDto.setStatus(false);
				statusDto.setMessage("data dengan nik " + nik + " tidak ditemukan");
				values.add(statusDto);
			}
		} else {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk, jumlah digit nik tidak sama dengan 16");
			values.add(statusDto);
		}
		return values;

	}

	private PersonBioPendDto convertToDto(Person person, Biodata biodata) {
		PersonBioPendDto dto = modelMapper.map(person, PersonBioPendDto.class);
		Integer id = person.getIdPerson();
		modelMapper.map(biodata, dto);

		// set Umur
		LocalDate birthYear = biodata.getTanggalLahir().toLocalDate();
		LocalDate dateNow = LocalDate.now();
		Period p = Period.between(birthYear, dateNow);
		int umur = p.getYears();

		dto.setUmur(umur);
		dto.setPendidikanTerakhir(pendidikanRepository.cariPendidikanTerakhir(id));
		return dto;
	}

	@GetMapping("/data/{idBio}")
	public PersonBioDto getBio(@PathVariable Integer idBio) {
		Biodata biodata = biodataRepository.findById(idBio).get();
		PersonBioDto data = modelMapper.map(biodata, PersonBioDto.class);
		modelMapper.map(biodata.getPerson(), data);
		return data;
	}
}
