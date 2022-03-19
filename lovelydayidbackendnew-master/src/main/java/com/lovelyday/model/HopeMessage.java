package com.lovelyday.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity(name="ld_hope_message")
@Table(name="ld_hope_message",uniqueConstraints= {@UniqueConstraint(columnNames={"hope_message_id"})})
@BatchSize(size = 100)
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class HopeMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(allocationSize=1,initialValue=1,name="hope_message_seq",sequenceName="hope_message_seq")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="hope_message_seq")
	@Column(name="hope_message_id",nullable=false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Long hopeMessageId;
	
	@Column(length=100)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String sender;
	
	@Column(length=500)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String text;
	
	@Type(type="true_false")
	@Column(length=1,name="is_attend")
	@JsonProperty(access = Access.WRITE_ONLY)
	private boolean attend=false;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_on",insertable=true,updatable=false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private Date createOn;
	
	@Column(length=128,name="website_name")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String websiteName;
	
	//forgetList
	/*private String ucapanText;
	private String ucapanFrom;
	private Long ucapanDate;*/

	//getter setter
	public Long getHopeMessageId() {
		return hopeMessageId;
	}

	public void setHopeMessageId(Long hopeMessageId) {
		this.hopeMessageId = hopeMessageId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isAttend() {
		return attend;
	}

	public void setAttend(boolean attend) {
		this.attend = attend;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	
	//list
	public String getUcapanText() {
		return text;
	}

	public String getUcapanFrom() {
		return sender;
	}

	public Long getUcapanDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createOn);
		return calendar.getTimeInMillis();
	}
}
