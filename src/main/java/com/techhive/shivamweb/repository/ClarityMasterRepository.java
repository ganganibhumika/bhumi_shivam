package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.ClarityMaster;
@Repository
public interface ClarityMasterRepository extends JpaRepository<ClarityMaster, String>{

	Optional<ClarityMaster> findByclarityMasterName(String name);

	@Query(value="select max(clm.clarityMasterOrder) from ClarityMaster as clm")
	Integer getMaxOrder();

	List<ClarityMaster> findAllByOrderByClarityMasterOrderAsc();
}
