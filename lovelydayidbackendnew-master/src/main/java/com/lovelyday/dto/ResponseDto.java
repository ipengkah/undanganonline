package com.lovelyday.dto;

public class ResponseDto {

	private String responseStatus;
	private String responseMessage;
	private Object data;
	
	public ResponseDto(String responseStatus, String responseMessage) {
		super();
		this.responseStatus = responseStatus;
		this.responseMessage = responseMessage;
	}
	public ResponseDto(String responseStatus, String responseMessage, Object data) {
		super();
		this.responseStatus = responseStatus;
		this.responseMessage = responseMessage;
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "ResponseDto [responseStatus=" + responseStatus + ", responseMessage=" + responseMessage +  ",data=" + data +"]";
	}

	//Getter Setter
	public String getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
