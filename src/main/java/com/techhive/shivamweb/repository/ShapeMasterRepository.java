package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.ShapeMaster;


@Repository
public interface ShapeMasterRepository extends JpaRepository<ShapeMaster, String>{

	Optional<ShapeMaster> findByshapeName(String shapeName);

	Optional<ShapeMaster> findByshortName(String shortName);
	
	@Query(value="select max(sm.shapeOrder) from ShapeMaster as sm")
	Integer getMaxOrder();

	List<ShapeMaster> findAllByOrderByShapeOrderAsc();


	
//	@Query(value="select s.id,s.shapeName,s.shortName,s.shapeImage,s.shapeOrder from ShapeMaster as s")
//	List<ShapeMaster> findAllShapDetails();
}
