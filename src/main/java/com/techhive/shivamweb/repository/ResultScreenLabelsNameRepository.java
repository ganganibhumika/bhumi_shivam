package com.techhive.shivamweb.repository;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.ResultScreenLabelsName;

@Repository
public interface ResultScreenLabelsNameRepository extends JpaRepository<ResultScreenLabelsName, String> {

//	List<ResultScreenLabelsName> findAllByOrderByLabelOrderAsc();

	ResultScreenLabelsName findAllByField(@PathParam(value = "field") String fieldName);
	
	@Query(value="SELECT field,headerName from ResultScreenLabelsName")
	List<ResultScreenLabelsName> findAllLabelNameByHeaderNameAndField();

}
