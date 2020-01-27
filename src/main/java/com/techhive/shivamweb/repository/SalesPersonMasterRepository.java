package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.SalesPersonMaster;

@Repository
public interface SalesPersonMasterRepository extends JpaRepository<SalesPersonMaster, String> {

	Optional<SalesPersonMaster> findByname(String name);

	Optional<SalesPersonMaster> findBymobileNo(String name);

	// Optional<SalesPersonMaster> findBysoftwareUserId(String name);

	Optional<SalesPersonMaster> findByisPrimary(boolean b);

	List<SalesPersonMaster> findAllByisActive(boolean b);

	Optional<SalesPersonMaster> findByisPrimaryAndIdNotIn(Boolean isPrimary, String id);

	@Query(value = "select new com.techhive.shivamweb.master.model.SalesPersonMaster(sp. id, sp. name, sp. mobileNo, sp. email, sp. skype, sp. qQaddress,sp. isActive, sp. isPrimary) from SalesPersonMaster as sp inner join User as u on u.salesPersonMaster.id=sp.id where u.id=?1")
	Optional<SalesPersonMaster> getSalesPersonByUserId(String userId);

	@Query(value = "select * from tblSalesPersonMaster where softwareSalePersonMaster_softwaresalesPersonId=?1", nativeQuery = true)
	Optional<SalesPersonMaster> findBysoftwareUserIdNew(String softwareUserIdNew);

}
