package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.SoftwarePartyMaster;

@Repository
public interface SoftwarePartyMasterRespository extends JpaRepository<SoftwarePartyMaster, String> {

	Optional<SoftwarePartyMaster> findByPartyName(String partyName);

	Optional<SoftwarePartyMaster> findByEmailId(String emailId);
	
	@Query("select new com.techhive.shivamweb.master.model.SoftwarePartyMaster(spm.id, spm.partyName) from SoftwarePartyMaster as spm")
	List<SoftwarePartyMaster> findAllIdName();

	@Query(value = "select  new com.techhive.shivamweb.master.model.SoftwarePartyMaster(sp.id, sp.partyName) from SoftwarePartyMaster as sp inner join User as u on u.softwarePartyMaster.id=sp.id where u.id=?1")
	Optional<SoftwarePartyMaster>  getSoftwarePartyByUserId(String userId);

}
