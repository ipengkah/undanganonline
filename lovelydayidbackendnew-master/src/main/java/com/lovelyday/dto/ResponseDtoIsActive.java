package com.lovelyday.dto;

public class ResponseDtoIsActive {
	
	private Boolean userVerified;
	long resendTime;
	String accType;
	
	public long getResendTime() {
		return resendTime;
	}

	public void setResendTime(long resendTime) {
		this.resendTime = resendTime;
	}

	
	public ResponseDtoIsActive(Boolean userVerified, long resendTime) {
		super();
		
		this.userVerified = userVerified;
		this.resendTime = resendTime;
	}
	
	public ResponseDtoIsActive(Boolean userVerified, long resendTime, String accType) {
		super();
		
		this.userVerified = userVerified;
		this.resendTime = resendTime;
		this.accType = accType;
	}
	
	@Override
	public String toString() {
		return "ResponseDtoIsActive [userVerified =" + userVerified + ",resendTime =" + resendTime + "]";
	}

	//Getter Setter
	
	public Boolean getUserVerified() {
		return userVerified;
	}

	public void setUserVerified(Boolean userVerified) {
		this.userVerified = userVerified;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}
	
	
}
