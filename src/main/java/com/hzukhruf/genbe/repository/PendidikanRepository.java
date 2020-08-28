package com.hzukhruf.genbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hzukhruf.genbe.model.entity.Pendidikan;

@Repository
public interface PendidikanRepository extends JpaRepository<Pendidikan, Integer> {
	@Query(value = "SELECT jenjang FROM t_pendidikan WHERE id_person = ?1 ORDER BY tahun_lulus DESC LIMIT 1", nativeQuery = true)
	String cariPendidikanTerakhir(Integer id);
}
