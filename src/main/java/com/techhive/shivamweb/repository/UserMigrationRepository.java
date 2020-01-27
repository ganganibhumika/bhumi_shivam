package com.techhive.shivamweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.UserMigration;

@Repository
public interface UserMigrationRepository extends JpaRepository<UserMigration, String>{
	@Query("select um from UserMigration as um where um.username=?1 or um.email =?1")
	Optional<UserMigration> findByUsernameOrEmail(String userNameOrEmail);
	@Query("select um from UserMigration as um where (um.username=?1 or um.email =?1) and um.id <> ?2")
	Optional<UserMigration> findByUsernameOrEmailNotIdIn(String userNameOrEmail, String userId);


}
