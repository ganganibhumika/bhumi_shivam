package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.CutPolishSymmentryMaster;

@Repository
public interface CutPolishSymmentryMasterRepository extends JpaRepository<CutPolishSymmentryMaster, String>{
	Optional<CutPolishSymmentryMaster> findBycutPolishSymmentryMasterName(String colorName);

	@Query(value="select max(cpsm.cutPolishSymmentryMasterOrder) from CutPolishSymmentryMaster as cpsm")
	Integer getMaxOrder();

	List<CutPolishSymmentryMaster> findAllByOrderByCutPolishSymmentryMasterOrderAsc();
}
