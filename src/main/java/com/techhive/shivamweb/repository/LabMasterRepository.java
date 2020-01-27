package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.LabMaster;
@Repository
public interface LabMasterRepository extends JpaRepository<LabMaster, String>{
	Optional<LabMaster> findBylabMasterName(String name);

	@Query(value="select max(lm.labMasterOrder) from LabMaster as lm")
	Integer getMaxOrder();

	List<LabMaster> findAllByOrderByLabMasterOrderAsc();
}
