package com.techhive.shivamweb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.FancyColorMaster;

@Repository
public interface FancyColorMasterRepository extends JpaRepository<FancyColorMaster, String> {
	Optional<FancyColorMaster> findByfancyColorName(String name);

	@Query(value="select max(fcm.fancyColorOrder) from FancyColorMaster as fcm")
	Integer getMaxOrder();

	List<FancyColorMaster> findAllByOrderByFancyColorOrderAsc();

	
	/**
	 * this arraylist user in search screen for get list for fancy color.
	 * 
	 * @bhumi
	 */
	@Query(value = "SELECT fancyColorName from tblFancyColorMaster ", nativeQuery = true)
	ArrayList<String> getArrayListForFcolor();

//	ArrayList<String> getArrayListOfFancyColorForDis();
}
