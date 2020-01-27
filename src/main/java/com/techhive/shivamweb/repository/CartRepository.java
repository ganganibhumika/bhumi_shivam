package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

	@Query(value = "select c from Cart as c inner join User as u on c.user.id=u.id inner join PktMaster as pkt on c.pktMaster.id=pkt.id where u.id=?1 and pkt.isSold=false and pkt.id=?2")
	Optional<Cart> findByUserAndPkt(String userId, String pktMasterId);

	@Query(value = "select c from Cart as c inner join User as u on c.user.id=u.id inner join PktMaster as pkt on c.pktMaster.id=pkt.id where u.id=?1 and pkt.isSold=false order by c.createdDate DESC")
	List<Cart> findAllByUser(String userId);

	@Query(value = "select distinct new com.techhive.shivamweb.master.model.User(c.user.id, c.user.username)  from Cart as c")
	Set<User> findAllUserWithAbandondStone();

	@Query(value = "select count(*) from Cart as c inner join User as u on c.user.id=u.id inner join PktMaster as pkt on c.pktMaster.id=pkt.id where u.id=?1 and pkt.isSold=false")
	Integer countforUser(String userId);

//	@Query(value = "select c from Cart as c inner join PktMaster as pkt on c.pktMaster.id=pkt.id where c.user_userId=?1 and pkt.isSold=false order by c.createdDate DESC")
//	List<Cart> findAllByShowUsers(String userId);

}
