package com.hzukhruf.genbe.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import com.hzukhruf.genbe.model.entity.Pendidikan;
import com.hzukhruf.genbe.model.entity.Person;
import com.hzukhruf.genbe.repository.BiodataRepository;
import com.hzukhruf.genbe.repository.PendidikanRepository;
import com.hzukhruf.genbe.repository.PersonRepository;

@RestController
@RequestMapping("/dataPerson")
public class Controller {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private BiodataRepository biodataRepository;
	@Autowired
	private PendidikanRepository pendidikanRepository;

	private int umur(DataDto1 data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTgl(), formatter);
		LocalDate dateNow = LocalDate.now();
		Period p = Period.between(birthYear, dateNow);
		int umur = p.getYears();
		return umur;
	}

	// insert data person
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
			Person person = convertToPerson(data);
			Biodata biodata = convertToBiodata(data);
			personRepository.save(person);
			biodata.setPerson(person);
			biodataRepository.save(biodata);
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		}
		return statusDto;
	}

	private Person convertToPerson(DataDto1 data) {
		Person person = new Person();
		person.setIdPerson(data.getIdPerson());
		person.setNik(data.getNik());
		person.setNama(data.getName());
		person.setAlamat(data.getAddress());
		return person;
	}

	private Biodata convertToBiodata(DataDto1 data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		LocalDate birthYear = LocalDate.parse(data.getTgl(), formatter);
		Date bYear = Date.from(birthYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Biodata biodata = new Biodata();
		biodata.setIdBio(data.getIdBio());
		biodata.setNoHp(data.getHp());
		biodata.setTanggalLahir(bYear);
		biodata.setTempatLahir(data.getTempatLahir());
		return biodata;
	}

	// insert data pendidikan berdasarkan id person
	@PostMapping("/insertPendidikan")
	public StatusDto pendidikan(@RequestParam Integer idPerson, @RequestBody List<DataDto3> dataList) {
		StatusDto statusDto = new StatusDto();
		try {
			dataList.forEach(data -> {
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
				pendidikanRepository.save(pendidikan);
			});
			statusDto.setStatus(true);
			statusDto.setMessage("data berhasil masuk");
		} catch (Exception e) {
			statusDto.setStatus(false);
			statusDto.setMessage("data gagal masuk");
		}
		return statusDto;
	}
	
	// Get mapping  by nik
	@GetMapping("/{nik}")
	public List<Object> getByNik(@PathVariable String nik) {
		List<Object> values = new ArrayList<>();
		StatusDto statusDto = new StatusDto();
		DataDto2 dataDto2 = new DataDto2();
		Status2 status2 = new Status2();
		if (nik.length() == 16) {
			if (personRepository.findByNik(nik).isEmpty() == false) {
				Person person = personRepository.findByNik(nik).get(0);
				Integer id = person.getIdPerson();
				Biodata biodata = biodataRepository.findAllByPersonIdPerson(id);
				dataDto2.setNik(nik);
				dataDto2.setName(person.getNama());
				dataDto2.setAddress(person.getAlamat());
				dataDto2.setHp(biodata.getNoHp());

				// convert date to String
				DateFormat format = new SimpleDateFormat("dd-MMMMMMMMM-yyyy");
				String date = format.format(biodata.getTanggalLahir());
				dataDto2.setTgl(date);
				dataDto2.setTempatLahir(biodata.getTempatLahir());

				// set Umur
				LocalDate birthYear = biodata.getTanggalLahir().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				LocalDate dateNow = LocalDate.now();
				Period p = Period.between(birthYear, dateNow);
				int umur = p.getYears();

				dataDto2.setUmur(umur);
				dataDto2.setPendidikanTerakhir(pendidikanRepository.cariPendidikanTerakhir(id));

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

}
