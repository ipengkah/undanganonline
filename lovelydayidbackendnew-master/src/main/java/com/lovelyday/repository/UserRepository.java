package com.lovelyday.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lovelyday.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUserName(String userName);
	
	User findByUserNameAndUserPass(String userName, String usePass);
	
	User findByUserNameAndToken(String userName, String token);
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("update ld_users u set u.token = :token where u.userName = :username")
	void updateToken(@Param(value = "token") String token, @Param(value = "username") String username);
	
	@Modifying
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Query("update ld_users u set u.active = 'T' where u.userName = :username")
	void updateActiveUser(@Param(value = "username") String username);
	
}
