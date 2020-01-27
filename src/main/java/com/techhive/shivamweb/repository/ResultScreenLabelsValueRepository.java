package com.techhive.shivamweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.ResultScreenLabelsValue;

@Repository
public interface ResultScreenLabelsValueRepository extends JpaRepository<ResultScreenLabelsValue,String> {
	
	@Query(value="SELECT labelNames.*,labelValues.* FROM tblResultScreenLabelsName as labelNames "
			+ "LEFT JOIN tblResultScreenLabelsValue as labelValues on"
			+ " labelValues.resultScreenLablesName_lableNameId=labelNames.lableNameId WHERE labelValues.userId=?1 AND labelNames.lableNameId=?2 ",nativeQuery=true)
	List<ResultScreenLabelsValue> findAllLabelsValueByUserIdAndLabelNameId(@Param(value="userId") String userId,@Param(value="labelNameId") String labelNameId);
	
	@Query(value="SELECT labelNames.*,labelValues.* FROM tblResultScreenLabelsName as labelNames "
			+ "LEFT JOIN tblResultScreenLabelsValue as labelValues on"
			+ " labelValues.resultScreenLablesName_lableNameId=labelNames.lableNameId WHERE labelValues.userId=?1 ORDER BY labelValues.labelOrder ASC",nativeQuery=true)
	List<ResultScreenLabelsValue> findAllLabelsValueByUserId(@Param(value="userId") String userId);

}
