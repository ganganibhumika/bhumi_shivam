package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.DTO.NotificationDto;
import com.techhive.shivamweb.model.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

	@Query(value = "select fcmToken from tblUser where userId In ?1", nativeQuery = true)
	Set<String> getListOfTocken(Set<String> setOfUserId);

	@Query(value = "select user_userId from tblWishlist where pktMaster_pktMasterId=?1", nativeQuery = true)
	Set<User> getListOfwishListUserIdBypkt(String id);

	@Query(value = "select user_userId from tblCart where pktMaster_pktMasterId=?1", nativeQuery = true)
	Set<User> getListOfCartUserIdBypkt(String id);

	@Query(value = "select user_userId from tblOfferDiscountRequest where pktMaster_pktMasterId=?1", nativeQuery = true)
	Set<User> getListOfPlaceOfferUserIdBypkt(String id);

	@Query("select new com.techhive.shivamweb.master.model.DTO.NotificationDto(n.id,n.description,n.category,n.isAdmin,n.stoneOrUserId,n.isRead,n.isShow,n.createdDate) from Notification as n left join User as u on u.id=n.user.id where u.id=?1 and n.isAdmin=?2")
	Page<NotificationDto> getAllNotificationByUserId(String userId, boolean isAdmin, Pageable request);

	@Query("select n from Notification as n left join User as u on u.id=n.user.id where u.id=?1 and n.isShow=false and n.isAdmin=?2")
	List<Notification> getUnShowNotification(String userId, boolean isAdmin);

	@Query(value = "SELECT COUNT(*) from tblNotification where user_userId=?1 AND isShow='false' AND isAdmin=?2", nativeQuery = true)
	Long getAllUnShowCntByUserId(String userId, boolean isAdmin);

}
