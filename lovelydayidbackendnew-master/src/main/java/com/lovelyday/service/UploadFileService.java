package com.lovelyday.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lovelyday.model.UploadFile;
import com.lovelyday.repository.UploadFileRepository;

@Service
public class UploadFileService {
	
	@Resource
	private UploadFileRepository uploadFileRepository;
	
	public void saveUploadFile(UploadFile uploadFile) {
		this.uploadFileRepository.save(uploadFile);
	}
	
	public UploadFile findByUploadFileId(Long uploadFileId) {
		return this.uploadFileRepository.findByUploadFileId(uploadFileId);
	}
	
	public List<UploadFile> findByUserName(String userName){
		return this.uploadFileRepository.findByUserName(userName);
	}
	
}
