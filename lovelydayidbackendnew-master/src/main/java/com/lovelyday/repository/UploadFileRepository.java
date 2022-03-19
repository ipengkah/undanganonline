package com.lovelyday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovelyday.model.UploadFile;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
	
	UploadFile findByUploadFileId(Long uploadFileId);
	
	List<UploadFile> findByUserName(String userName);
	
}
