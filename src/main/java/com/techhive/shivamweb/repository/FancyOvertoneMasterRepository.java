package com.techhive.shivamweb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.FancyOvertoneMaster;
@Repository
public interface FancyOvertoneMasterRepository extends JpaRepository<FancyOvertoneMaster,String>{

	Optional<FancyOvertoneMaster> findByfancyOvertoneName(String name);

	@Query(value="select max(fom.fancyOvertoneOrder) from FancyOvertoneMaster as fom")
	Integer getMaxOrder();

	List<FancyOvertoneMaster> findAllByOrderByFancyOvertoneOrderAsc();

	/**
	 * this arraylist user in search screen for get list for fancy color.
	 * 
	 * @bhumi
	 */
	@Query(value = "SELECT fancyOvertoneName from tblFancyOvertoneMaster ", nativeQuery = true)
	
	ArrayList<String> getArrayListForfOvertone();
}
