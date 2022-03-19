package com.lovelyday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovelyday.model.HopeMessage;

@Repository
public interface HopeMessageRepository extends JpaRepository<HopeMessage, Long> {
	
	List<HopeMessage> findByWebsiteNameOrderByHopeMessageIdDesc(String websiteName);
	
}
