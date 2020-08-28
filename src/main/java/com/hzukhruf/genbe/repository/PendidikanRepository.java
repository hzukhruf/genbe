package com.hzukhruf.genbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hzukhruf.genbe.model.entity.Pendidikan;

@Repository
public interface PendidikanRepository extends JpaRepository<Pendidikan, Integer> {

}
