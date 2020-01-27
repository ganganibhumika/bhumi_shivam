package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.DTO.UserDto;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	@Query(value = "SELECT * FROM tblUser aliasUser WHERE (username=?1 OR email = ?1) and aliasUser.isDeleted=0", nativeQuery = true)
	public Optional<User> findByUsernameOrEmail(String usernameOrEmail);

	@Query(value = "select new com.techhive.shivamweb.master.model.DTO.UserDto(u. id, u. firstName, u. lastName, u. username, u. prefix, u. companyAddress, u. companyName, u. phoneNo, u. mobileNo, u. gender, u. email, u. pinCode,u. city, u. state, u. country) from User as u where u.isApproved =true and u.isDeleted=false")
	List<UserDto> findAllClient();

	@Query(value = "select new com.techhive.shivamweb.master.model.DTO.UserDto(u. id, u. firstName, u. lastName, u. username, u. prefix, u. companyAddress, u. companyName, u. phoneNo, u. mobileNo, u. gender, u. email, u. pinCode,u. city, u. state, u. country, u. createdDate,u. approveDate,u. ipAddress,u.isApproved,sp.id,sp.name,spm.id,spm.partyName,u.isAdmin) from User as u left join SalesPersonMaster as sp on u.salesPersonMaster.id=sp.id left join SoftwarePartyMaster as spm on u.softwarePartyMaster.id=spm.id where u.isEmailVerified =true and u.isDeleted=false and u.isSuperAdmin=false")
	public Page<UserDto> findAllByIsEmailVerified(Pageable request);

	@Query(value = "select new com.techhive.shivamweb.master.model.DTO.UserDto(u. id, u. firstName, u. lastName, u. username, u. prefix, u. companyAddress, u. companyName, u. phoneNo, u. mobileNo, u. gender, u. email, u. pinCode,u. city, u. state, u. country, u. createdDate,u. approveDate,u. ipAddress,u.isApproved,sp.id,sp.name,spm.id,spm.partyName,u.isAdmin) from User as u left join SalesPersonMaster as sp on u.salesPersonMaster.id=sp.id left join SoftwarePartyMaster as spm on u.softwarePartyMaster.id=spm.id where u.isEmailVerified =true and u.isApproved=?1 and u.isDeleted=false  and u.isSuperAdmin=false")
	public Page<UserDto> findAllByIsEmailVerifiedAndIsApprove(Boolean isApprove, Pageable request);

	@Query(value = "select new com.techhive.shivamweb.master.model.DTO.UserDto(u. id,u. username) from User as u where u.isApproved =true and u.isDeleted=false")
	List<UserDto> findAllApprovedUser();

	public Optional<User> findByEmailAndIsDeleted(String email, Boolean flag);

	@Query(value = "select u from User as u inner join SalesPersonMaster as spm on u.salesPersonMaster.id= spm.id where u.isEmailVerified =true and u.isDeleted=false and spm.id=?1")
	List<User> getAllClientOfSalesPerson(String salesPersonId);

	@Query(value = "select new com.techhive.shivamweb.master.model.DTO.UserDto(u. id, u. firstName, u. lastName, u. username, u. prefix, u. companyAddress, u. companyName, u. phoneNo, u. mobileNo, u. gender, u. email, u. pinCode,u. city, u. state, u. country, u. createdDate,u. approveDate,u. ipAddress,u.isApproved,u.isAdmin) from User as u  where (u.firstName like concat('%',?1,'%') or u.lastName like concat('%',?1,'%') or u.email like concat('%',?1,'%') or u.phoneNo like concat('%',?1,'%') or u.mobileNo like concat('%',?1,'%') or u.pinCode like concat('%',?1,'%') or u.companyName like concat('%',?1,'%') or u.country like concat('%',?1,'%') or u.state like concat('%',?1,'%') or u.city like concat('%',?1,'%') or u.companyAddress like concat('%',?1,'%')) and u.isEmailVerified =true and u.isDeleted=false")
	public Page<UserDto> findAllByIsEmailVerifiedSearch(String searchText, Pageable request);

	/**
	 * @author neel
	 * @return get all user for seeing tracking if they are deleted than also to see
	 *         there tracking
	 */
	@Query(value = "select new com.techhive.shivamweb.master.model.DTO.UserDto(u. id, u. username) from User as u")
	List<UserDto> getAllUser();

	public Optional<User> findByIdAndIsDeletedAndIsApproved(String id, boolean isDeleted, boolean isApprove);

	public Set<User> findByisDeletedAndIsApprovedAndIsAdmin(boolean b, boolean c, boolean d);

	// use if email verify
	// @Query(value = "select new com.techhive.shivamweb.master.model.User(u. id, u.
	// firstName, u.lastName, u. username, u. email,sp.id,sp.name) from User as u
	// left join SalesPersonMaster as sp on u.salesPersonMaster.id=sp.id where
	// u.isEmailVerified =true and u.isApproved=true and u.isDeleted=false and
	// u.isSuperAdmin=false")
	// public List<User> getAllUserFromShow();

	@Query(value = "select new com.techhive.shivamweb.master.model.DTO.UserDto(u. id, u. firstName, u.lastName, u. username, u. email,sp.id,sp.name) from User as u left join SalesPersonMaster as sp on u.salesPersonMaster.id=sp.id where u.isShow=true and u.isDeleted=false and u.isSuperAdmin=false")
	public List<UserDto> getAllUserFromShow();

}
