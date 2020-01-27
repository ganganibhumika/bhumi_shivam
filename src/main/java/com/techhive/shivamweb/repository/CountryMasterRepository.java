package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.CountryMaster;

@Repository
public interface CountryMasterRepository extends JpaRepository<CountryMaster, String>{

	Optional<CountryMaster> findBycountryMasterName(String milkCountryMaster);

	Optional<CountryMaster> findByshortName(String shortName);
	
	@Query(value="select max(cm.countryMasterOrder) from CountryMaster as cm")
	Integer getMaxOrder();

	List<CountryMaster> findAllByOrderByCountryMasterOrderAsc();
}
