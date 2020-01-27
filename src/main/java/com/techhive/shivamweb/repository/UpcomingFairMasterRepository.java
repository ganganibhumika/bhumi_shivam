package com.techhive.shivamweb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.UpcomingFairMaster;

@Repository
public interface UpcomingFairMasterRepository extends JpaRepository<UpcomingFairMaster, String>{
	@Query(value="select uf from UpcomingFairMaster as uf where (?1 between uf.startDate and uf.endDate ) and uf.isFairActive=true")
	List<UpcomingFairMaster> findFairBetweenDates(Date result);
	
	@Query(value="select uf from UpcomingFairMaster as uf where (?1 between uf.startDate and uf.endDate ) and uf.isFairActive=true and uf.id not in ?2")
	List<UpcomingFairMaster> findFairBetweenDatesAndNotIdIn(Date result, String fairId);

}
