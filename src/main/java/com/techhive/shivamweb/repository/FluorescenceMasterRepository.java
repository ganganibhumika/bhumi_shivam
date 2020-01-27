package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.FluorescenceMaster;
@Repository
public interface FluorescenceMasterRepository extends JpaRepository<FluorescenceMaster, String>{
	
	Optional<FluorescenceMaster> findByfluorescenceMasterName(String fluorescenceMasterName);

	Optional<FluorescenceMaster> findByshortName(String shortName);
	
	@Query(value="select max(fm.fluorescenceMasterOrder) from FluorescenceMaster as fm")
	Integer getMaxOrder();

	List<FluorescenceMaster> findAllByOrderByFluorescenceMasterOrderAsc();
}
