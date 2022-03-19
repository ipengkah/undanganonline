package com.lovelyday.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lovelyday.model.User;
import com.lovelyday.model.UserOrders;

@Repository
public interface UserOrdersRepository extends JpaRepository<UserOrders, Long> {
	
	UserOrders findByUserAndWebsiteName(User user, String websiteName);
	
	UserOrders findByWebsiteName(String websiteName);
	
	UserOrders findByUser(User user);
	
	List<UserOrders> findListByUser(User user);
	
	//List<UserOrders> findListByUserPageable(User user, Pageable pageable);
	
	@Query(nativeQuery = true, value = "select website_name from ld_user_orders where user_id =:userId and website_name like %:searchName% order by order_id desc ")
	List<String> findListByUserPageable2(@Param(value = "userId") Long userId, @Param(value = "searchName") String searchName, Pageable pageable);
	
	@Query(nativeQuery = true, value = "select website_name from ld_user_orders where user_id =:userId order by order_id desc ")
	List<String> findListByUserPageable(@Param(value = "userId") Long userId, Pageable pageable);
	
	@Query(nativeQuery = true, value = "select website_name from ld_user_orders where user_id =:userId order by order_id asc limit 1 ")
	String findFirstOrderByUserId(@Param(value = "userId") Long userId);

	@Query(nativeQuery = true, value = "select count(order_id) from ld_user_orders where user_id =:userId and website_name like %:searchName% ")
	Double countListByUser2(@Param(value = "userId") Long userId, @Param(value = "searchName") String searchName);
	
	@Query(nativeQuery = true, value = "select count(order_id) from ld_user_orders where user_id =:userId ")
	Double countListByUser(@Param(value = "userId") Long userId);
}
