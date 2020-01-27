package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.MilkMaster;
@Repository
public interface MilkMasterRepository extends JpaRepository<MilkMaster, String>{
	
	Optional<MilkMaster> findBymilkMasterName(String milkMasterName);

	Optional<MilkMaster> findByshortName(String shortName);
	
	@Query(value="select max(mm.milkMasterOrder) from MilkMaster as mm")
	Integer getMaxOrder();

	List<MilkMaster> findAllByOrderByMilkMasterOrderAsc();
}
