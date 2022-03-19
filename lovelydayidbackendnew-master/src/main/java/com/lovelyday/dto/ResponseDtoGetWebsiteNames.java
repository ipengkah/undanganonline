package com.lovelyday.dto;

import java.util.ArrayList;
import java.util.List;

public class ResponseDtoGetWebsiteNames {
	
	List<String> websiteName = new ArrayList<String>();
	int maxPage;
	
	public List<String> getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(List<String> websiteName) {
		this.websiteName = websiteName;	
	}
	
	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public ResponseDtoGetWebsiteNames(List<String> websiteName, int maxPage) {
		super();
		
		this.websiteName = websiteName;
		this.maxPage = maxPage;
	}

	@Override
	public String toString() {
		return "ResponseDtoGetWebsiteNames [websiteName=" + websiteName + ", maxPage=" + maxPage + "]";
	}

	
}
