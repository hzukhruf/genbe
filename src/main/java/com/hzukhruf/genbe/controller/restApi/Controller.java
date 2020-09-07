package com.hzukhruf.genbe.controller.restApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzukhruf.genbe.model.dto.PersonBioDto;
import com.hzukhruf.genbe.model.dto.PersonBioPendidikanDto;
import com.hzukhruf.genbe.model.dto.StatusData;
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

	private int umur(PersonBioDto data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTanggalLahir(), formatter);
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
			dataService.insertData(data);
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		}
		return statusDto;
	}

	// insert data pendidikan berdasarkan id person
//	@PostMapping("/insertPendidikan")
//	public StatusDto pendidikan(@RequestBody List<PendidikanDto> dataList) {
//		StatusDto statusDto = new StatusDto();
//		try {
//			dataService.insertdataPendidikan(dataList);
//			statusDto.setStatus(true);
//			statusDto.setMessage("data berhasil masuk");
//		} catch (Exception e) {
//			statusDto.setStatus(false);
//			statusDto.setMessage("data gagal masuk");
//		}
//		return statusDto;
//	}


	// Get mapping by nik
	@GetMapping("/{nik}")
	public List<Object> getByNik(@PathVariable String nik) {
		List<Object> values = new ArrayList<>();
		StatusDto statusDto = new StatusDto();
		StatusData status2 = new StatusData();
		if (nik.length() == 16) {
			if (personRepository.findByNik(nik).isEmpty() == false) {
				Person person = personRepository.findByNik(nik).get(0);
				Integer id = person.getIdPerson();
				Biodata biodata = biodataRepository.findAllByPersonIdPerson(id);
				PersonBioPendidikanDto dataDto2 = convertToDto(person, biodata);
				// set status message
				status2.setStatus(true);
				status2.setMessage("success");
				status2.setData(dataDto2);
				values.add(status2);
			} else {
				statusDto.setStatus(true);
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
	
	private PersonBioPendidikanDto convertToDto(Person person, Biodata biodata) {
		PersonBioPendidikanDto personBioPendidikanDto = new PersonBioPendidikanDto();
		Integer id = person.getIdPerson();
		personBioPendidikanDto.setNik(person.getNik());
		personBioPendidikanDto.setNama(person.getNama());
		personBioPendidikanDto.setAlamat(person.getAlamat());
		personBioPendidikanDto.setHp(biodata.getNoHp());

		// convert date to String
		DateFormat format = new SimpleDateFormat("dd-MMMMMMMMM-yyyy");
		String date = format.format(biodata.getTanggalLahir());
		personBioPendidikanDto.setTanggalLahir(date);
		personBioPendidikanDto.setTempatLahir(biodata.getTempatLahir());

		// set Umur
		LocalDate birthYear = biodata.getTanggalLahir().toInstant().atZone(ZoneId.systemDefault())
				.toLocalDate();
		LocalDate dateNow = LocalDate.now();
		Period p = Period.between(birthYear, dateNow);
		int umur = p.getYears();

		personBioPendidikanDto.setUmur(umur);
		personBioPendidikanDto.setPendidikanTerakhir(pendidikanRepository.cariPendidikanTerakhir(id));
		return personBioPendidikanDto;
	}

}
