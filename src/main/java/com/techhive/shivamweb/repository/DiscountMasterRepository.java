package com.techhive.shivamweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.DiscountMaster;
import com.techhive.shivamweb.master.model.User;

@Repository
public interface DiscountMasterRepository extends JpaRepository<DiscountMaster, String> {

	@Query(value = "SELECT discount FROM tblDiscountMaster disc WHERE user_userId=?1 AND fromCarat <=?2 AND toCarat >=?2 AND fromDays <=?3 AND toDays>=?3 AND isActive ='true' AND isFancy=?4", nativeQuery = true)
	List<Double> getDiscountByUserId(String userId, Double carat, int daysBetweenForDisc, boolean isShapeFancy);
	
	@Query(value="SELECT * FROM tblDiscountMaster as dis WHERE dis.user_userId=:#{#discountMaster.idOfUser} ",nativeQuery=true)
	List<DiscountMaster> checkIsObjectExitOrNot(@Param ("discountMaster") DiscountMaster discountMaster);
	
	List<DiscountMaster> findAllByUserAndDiscountAndToCaratAndFromCaratAndToDaysAndFromDaysAndIsFancyAndIsActive
	(@Param("user") User user ,@Param ("discount") Double discount,
			@Param ("toCarat") Double toCarat,@Param ("fromCarat") Double fromCarat,
			@Param ("toDays") Integer toDays,@Param ("fromDays") Integer fromDays,
			@Param ("isFancy") boolean isFancy,@Param ("isActive") boolean isActive);

	// @Query(value = "SELECT discount FROM tblDiscountMaster WHERE user_userId=?1
	// ", nativeQuery = true)
	// List<Double> getDiscountByUserId(String userId);
	// public List<DiscountMaster> getAllDiscountWithPagination(Pageable
	// pageRequest);

}
