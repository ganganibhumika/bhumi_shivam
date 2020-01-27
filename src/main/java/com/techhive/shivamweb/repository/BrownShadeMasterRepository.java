package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.BrownShadeMaster;

@Repository
public interface BrownShadeMasterRepository  extends JpaRepository<BrownShadeMaster, String>{
	
	Optional<BrownShadeMaster> findBybrownShadeMasterName(String brownShadeMasterName);

	Optional<BrownShadeMaster> findByshortName(String shortName);
	
	@Query(value="select max(bsm.brownShadeMasterOrder) from BrownShadeMaster as bsm")
	Integer getMaxOrder();

	List<BrownShadeMaster> findAllByOrderByBrownShadeMasterOrderAsc();
}
