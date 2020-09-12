package com.hzukhruf.genbe.controller.restApi;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hzukhruf.genbe.model.dto.DataDto1;
import com.hzukhruf.genbe.model.dto.DataDto2;
import com.hzukhruf.genbe.model.dto.DataDto3;
import com.hzukhruf.genbe.model.dto.Status2;
import com.hzukhruf.genbe.model.dto.StatusDto;
import com.hzukhruf.genbe.model.entity.Biodata;
import com.hzukhruf.genbe.model.entity.Person;
import com.hzukhruf.genbe.repository.BiodataRepository;
import com.hzukhruf.genbe.repository.PendidikanRepository;
import com.hzukhruf.genbe.repository.PersonRepository;
import com.hzukhruf.genbe.service.DataPendidikanService;
import com.hzukhruf.genbe.service.DataPersonService;

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
	private DataPersonService dataPersonService;
	@Autowired
	private DataPendidikanService dataPendidikanService;

	private int umur(DataDto1 data) {
		LocalDate birthYear = data.getTgl().toLocalDate();
		LocalDate dateNow = LocalDate.now();
		Period p = Period.between(birthYear, dateNow);
		int umur = p.getYears();
		return umur;
	}

	// insert data person
	// Insert Tanggal dengan format dd-Month-yyyy (Month full name in english)
	@PostMapping
	public StatusDto insert(@RequestBody DataDto1 data) {
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
			dataPersonService.insertData(data);
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		}
		return statusDto;
	}

	// insert data pendidikan berdasarkan id person
	@PostMapping("/insertPendidikan")
	public StatusDto pendidikan(@RequestParam Integer idPerson, @RequestBody List<DataDto3> dataList) {
		StatusDto statusDto = new StatusDto();
		try {
			dataPendidikanService.insertdataPendidikan(idPerson, dataList);
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		} catch (Exception e) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk");
		}
		return statusDto;
	}

	@GetMapping("/getData")
	public List<DataDto1> getData() {
		List<DataDto1> dataDtoList = new ArrayList<>();
		List<Person> persons = personRepository.findAll();
		for (Person person : persons) {
			DataDto1 dataDto1 = new DataDto1();
            Biodata biodata = biodataRepository.findByPersonIdPerson(person.getIdPerson());
            dataDto1.setIdBio(biodata.getIdBio());
            dataDto1.setHp(biodata.getNoHp());
            dataDto1.setTgl(biodata.getTanggalLahir());
            dataDto1.setTempatLahir(biodata.getTempatLahir());
            dataDto1.setName(person.getNama());
            dataDto1.setAddress(person.getAlamat());
            dataDto1.setIdPerson(person.getIdPerson());
            dataDto1.setNik(person.getNik());
            dataDtoList.add(dataDto1);
        }
		return dataDtoList;
	}

	// Get mapping by nik
	@GetMapping("/{nik}")
	public List<Object> getByNik(@PathVariable String nik) {
		List<Object> values = new ArrayList<>();
		Status2 status2 = new Status2();
		StatusDto statusDto = new StatusDto();
		if (nik.length() == 16) {
			if (personRepository.findByNik(nik).isEmpty() == false) {	
				Person person = personRepository.findByNik(nik).get(0);
				Integer id = person.getIdPerson();
				Biodata biodata = biodataRepository.findAllByPersonIdPerson(id);
				DataDto2 dataDto2 = convertToDto(person, biodata);
				// set status message
				status2.setStatus(true);
				status2.setMessage("success");
				status2.setData(dataDto2);
				values.add(status2);
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

	private DataDto2 convertToDto(Person person, Biodata biodata) {
		DataDto2 dataDto2 = new DataDto2();
		Integer id = person.getIdPerson();
		dataDto2.setNik(person.getNik());
		dataDto2.setName(person.getNama());
		dataDto2.setAddress(person.getAlamat());
		dataDto2.setHp(biodata.getNoHp());

		// convert date to String
		dataDto2.setTgl(biodata.getTanggalLahir());
		dataDto2.setTempatLahir(biodata.getTempatLahir());

		// set Umur
		LocalDate birthYear = biodata.getTanggalLahir().toLocalDate();
		LocalDate dateNow = LocalDate.now();
		Period p = Period.between(birthYear, dateNow);
		int umur = p.getYears();

		dataDto2.setUmur(umur);
		dataDto2.setPendidikanTerakhir(pendidikanRepository.cariPendidikanTerakhir(id));
		return dataDto2;
	}
	
	@GetMapping("/data/{idBio}")
	public DataDto1 getBio(@PathVariable Integer idBio) {
		Biodata biodata = biodataRepository.findById(idBio).get();
		DataDto1 data= new DataDto1();
		data.setIdBio(biodata.getIdBio());
		data.setHp(biodata.getNoHp());
		data.setTgl(biodata.getTanggalLahir());
		data.setTempatLahir(biodata.getTempatLahir());
		data.setAddress(biodata.getPerson().getAlamat());
		data.setName(biodata.getPerson().getNama());
		data.setNik(biodata.getPerson().getNik());
		data.setIdPerson(biodata.getPerson().getIdPerson());
		return data;
	}
}
