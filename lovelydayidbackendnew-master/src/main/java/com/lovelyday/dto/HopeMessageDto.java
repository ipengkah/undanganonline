package com.lovelyday.dto;

public class HopeMessageDto {
	
	private String ucapanText;
	private String ucapanSender;
	private String websiteName;
	private Boolean willAttend;
	private Integer ucapanDate;
	
	public HopeMessageDto(String ucapanText, String ucapanSender, String websiteName, Boolean willAttend,
			Integer ucapanDate) {
		super();
		this.ucapanText = ucapanText;
		this.ucapanSender = ucapanSender;
		this.websiteName = websiteName;
		this.willAttend = willAttend;
		this.ucapanDate = ucapanDate;
	}

	@Override
	public String toString() {
		return "HopeMessageDto [ucapanText=" + ucapanText + ", ucapanSender=" + ucapanSender + ", websiteName="
				+ websiteName + ", willAttend=" + willAttend + ", ucapanDate=" + ucapanDate + "]";
	}
	
	public String getUcapanText() {
		return ucapanText;
	}
	public void setUcapanText(String ucapanText) {
		this.ucapanText = ucapanText;
	}
	public String getUcapanSender() {
		return ucapanSender;
	}
	public void setUcapanSender(String ucapanSender) {
		this.ucapanSender = ucapanSender;
	}
	public String getWebsiteName() {
		return websiteName;
	}
	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	public Boolean getWillAttend() {
		return willAttend;
	}
	public void setWillAttend(Boolean willAttend) {
		this.willAttend = willAttend;
	}
	public Integer getUcapanDate() {
		return ucapanDate;
	}
	public void setUcapanDate(Integer ucapanDate) {
		this.ucapanDate = ucapanDate;
	}
}
