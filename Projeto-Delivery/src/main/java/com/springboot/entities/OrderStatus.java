package com.springboot.entities;

public enum OrderStatus {

	PENDING(0), DELIVERED(1);
	
	private Integer status;
	
	OrderStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}
}
