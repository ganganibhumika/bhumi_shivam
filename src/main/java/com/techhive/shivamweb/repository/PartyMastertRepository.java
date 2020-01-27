package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.PartyMaster;
import com.techhive.shivamweb.master.model.DTO.PartyMasterDto;

@Repository
public interface PartyMastertRepository extends JpaRepository<PartyMaster, String>{

	Optional<PartyMaster> findBypartyname(String partyname);

	@Query("select new com.techhive.shivamweb.master.model.DTO.PartyMasterDto(pm.id,pm.partyname) from PartyMaster as pm where pm.isActive=true")
	List<PartyMasterDto> findAllIdName();

	Optional<PartyMaster> findByemail(String partyname);
	
	@Query("select pm from PartyMaster as pm where (pm.partyname=?1 OR pm.email = ?1)")
	Optional<PartyMaster> findBypartynameEmail(String partyName);

}
