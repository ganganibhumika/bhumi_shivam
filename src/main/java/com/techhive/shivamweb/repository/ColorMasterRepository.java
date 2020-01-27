package com.techhive.shivamweb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.ColorMaster;

@Repository
public interface ColorMasterRepository extends JpaRepository<ColorMaster, String> {

	Optional<ColorMaster> findBycolorName(String colorName);

	@Query(value = "select max(cm.colorOrder) from ColorMaster as cm")
	Integer getMaxOrder();

	List<ColorMaster> findAllByOrderByColorOrderAsc();

	/**
	 * this arraylist user in search screen for get list od white color
	 * 
	 * @bhumi
	 */
	@Query(value = "SELECT colorName from tblColorMaster ", nativeQuery = true)
	ArrayList<String> getArrayListForWhiteColor();
}
