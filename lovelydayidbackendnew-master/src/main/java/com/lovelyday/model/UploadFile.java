package com.lovelyday.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

@Entity(name="ld_upload_file")
@Table(name="ld_Upload_file",uniqueConstraints= {@UniqueConstraint(columnNames={"upload_file_id"})})
@BatchSize(size = 100)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UploadFile implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="upload_file_seq",sequenceName="upload_file_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="upload_file_seq")
	@Column(name="upload_file_id",nullable=false)
	private Long uploadFileId;
	
	@Lob
	@Type(type = "org.hibernate.type.BinaryType")
    @Column(name = "file_content_byte")
	private byte[] fileConyenByte;
	
	@Column(length=30,name="file_type")
	private String fileType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_on",insertable=true,updatable=false)
	private Date createOn;
	
	@Column(length=128,name="user_name")
	private String userName;

	//getter setter
	public Long getUploadFileId() {
		return uploadFileId;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public byte[] getFileConyenByte() {
		return fileConyenByte;
	}

	public void setFileConyenByte(byte[] fileConyenByte) {
		this.fileConyenByte = fileConyenByte;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
