package com.techhive.shivamweb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.PopupManagerMaster;

@Repository
public interface PopupManagerMasterRepository extends JpaRepository<PopupManagerMaster, String>{
	@Query(value="select p from PopupManagerMaster as p where (?1 between p.startDate and p.endDate ) and p.isPopupActive=true")
	List<PopupManagerMaster> findPopupBetweenDates(Date result);
	
	@Query(value="select p from PopupManagerMaster as p where (?1 between p.startDate and p.endDate ) and p.isPopupActive=true and p.id not in ?2")
	List<PopupManagerMaster> findPopupBetweenDatesAndNotIdIn(Date result, String popupManagerMasterId);

}
