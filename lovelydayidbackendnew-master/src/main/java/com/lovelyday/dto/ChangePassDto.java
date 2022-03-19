package com.lovelyday.dto;

public class ChangePassDto {

	private String userName;
	private String oldPassword;
	private String newPassword;
	
	@Override
	public String toString() {
		return "ChangePassDto [userName=" + userName + ", oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ "]";
	}
	
	public ChangePassDto(String userName, String oldPassword, String newPassword) {
		super();
		this.userName = userName;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	//getter setter
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
