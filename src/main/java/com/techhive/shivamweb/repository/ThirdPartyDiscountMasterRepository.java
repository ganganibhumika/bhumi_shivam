package com.techhive.shivamweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.PartyMaster;
import com.techhive.shivamweb.master.model.ThirdPartyDiscountMaster;

@Repository
public interface ThirdPartyDiscountMasterRepository extends JpaRepository<ThirdPartyDiscountMaster,String> {

	@Query(value = "SELECT discount FROM tblThirdPartyDiscountMaster disc WHERE partyMaster_partyId=?1 AND fromCarat <=?2 AND toCarat >=?2 AND fromDays <=?3 AND toDays>=?3 AND isActive ='true' AND isFancy=?4", nativeQuery = true)
	List<Double> getThirdPartyDiscountByUserId(String userId, Double carat, int daysBetweenForDisc,
			boolean isShapeFancy);
	
	List<ThirdPartyDiscountMaster> findAllByPartyMasterAndDiscountAndToCaratAndFromCaratAndToDaysAndFromDaysAndIsFancyAndIsActive
	(@Param("partyMaster") PartyMaster partyMaster ,@Param ("discount") Double discount,
			@Param ("toCarat") Double toCarat,@Param ("fromCarat") Double fromCarat,
			@Param ("toDays") Integer toDays,@Param ("fromDays") Integer fromDays,
			@Param ("isFancy") boolean isFancy,@Param ("isActive") boolean isActive);

	

}
