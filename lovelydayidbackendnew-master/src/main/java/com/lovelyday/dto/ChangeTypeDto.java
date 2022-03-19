package com.lovelyday.dto;

public class ChangeTypeDto {
	
	private String userName;
	private String websiteName;
	private String changeType;
	private String changeTo;
	
	public ChangeTypeDto(String userName, String websiteName,String changeType,String changeTo) {
		super();
		this.userName = userName;
		this.websiteName = websiteName;
		this.changeType = changeType;
		this.changeTo = changeTo;

	}
	
	@Override
	public String toString() {
		return "ChangeTypeDto [userName=" + userName + ", websiteName=" + websiteName + ",changeType=" + changeType + ", changeTo=" + changeTo + "]";
	}
	
	//GETTER SETTER
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getWebsiteName() {
		return websiteName;
	}
	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	
	public String getchangeType() {
		return changeType;
	}
	public void setchangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getchangeTo() {
		return changeTo;
	}
	public void setchangeTo(String changeTo) {
		this.changeTo = changeTo;
	}
}
