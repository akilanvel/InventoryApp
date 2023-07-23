package com.springapp.inventoryapi.dto;

public class IntegerDto {
	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "IntegerDto [value=" + value + "]";
	}
}
