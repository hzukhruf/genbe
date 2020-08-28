package com.hzukhruf.genbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hzukhruf.genbe.model.entity.Biodata;

@Repository
public interface BiodataRepository extends JpaRepository<Biodata, Integer>{

}
