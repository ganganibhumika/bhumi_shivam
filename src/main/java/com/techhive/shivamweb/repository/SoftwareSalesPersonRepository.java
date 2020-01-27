package com.techhive.shivamweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;

@Repository
public interface SoftwareSalesPersonRepository extends JpaRepository<SoftwareSalePersonMaster, String> {

	Optional<SoftwareSalePersonMaster> findByName(String name);
	
	@Query("select ssp from SoftwareSalePersonMaster as ssp inner join SalesPersonMaster as sp on ssp.salesPersonMaster.id=sp.id inner join User as u on u.salesPersonMaster.id=sp.id where u.id=?1")
	Optional<SoftwareSalePersonMaster> findByUser(String userId);
}
