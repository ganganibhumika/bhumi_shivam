package com.techhive.shivamweb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.FancyIntensityMaster;

@Repository
public interface FancyIntensityMasterRepository extends JpaRepository<FancyIntensityMaster, String> {

	Optional<FancyIntensityMaster> findByfancyIntensityName(String name);

	@Query(value = "select max(fim.fancyIntensityOrder) from FancyIntensityMaster as fim")
	Integer getMaxOrder();

	List<FancyIntensityMaster> findAllByOrderByFancyIntensityOrderAsc();

	/**
	 * this arraylist user in search screen for get list for fancy color.
	 * 
	 * @bhumi
	 */
	@Query(value = "SELECT fancyIntensityName from tblFancyIntensityMaster ", nativeQuery = true)
	ArrayList<String> getArrayListForfIntensity();
}
