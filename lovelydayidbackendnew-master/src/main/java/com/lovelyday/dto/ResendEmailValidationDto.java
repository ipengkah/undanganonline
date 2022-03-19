package com.lovelyday.dto;

public class ResendEmailValidationDto {
	long resendTime;
	
	public ResendEmailValidationDto(long resendTime) {
		super();
		this.resendTime = resendTime;
	}
	
	@Override
	public String toString() {
		return "ResendEmailValidationDto [resendTime =" + resendTime + "]";
	}

	public long getResendTime() {
		return resendTime;
	}

	public void setResendTime(long resendTime) {
		this.resendTime = resendTime;
	}
	
	
}
