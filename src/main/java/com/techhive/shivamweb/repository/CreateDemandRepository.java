package com.techhive.shivamweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.CreateDemand;

@Repository
public interface CreateDemandRepository extends JpaRepository<CreateDemand, String> {
	
	@Query("select cd from CreateDemand as cd where cd.shape like concat('%',?1,'%')")
	List<CreateDemand> findAllByShape(String shape);

}
