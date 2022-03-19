package com.lovelyday.dto;

public class ResponseDtoGetWebsiteName {
	
	String websiteName = "";

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public ResponseDtoGetWebsiteName(String websiteName) {
		super();
		
		this.websiteName = websiteName;
	}

	@Override
	public String toString() {
		return "ResponseDtoGetWebsiteName [websiteName=" + websiteName + "]";
	}
	
}
