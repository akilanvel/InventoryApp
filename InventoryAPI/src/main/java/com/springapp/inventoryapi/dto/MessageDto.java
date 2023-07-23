package com.springapp.inventoryapi.dto;

public class MessageDto {
	private String msg;

	public MessageDto(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
