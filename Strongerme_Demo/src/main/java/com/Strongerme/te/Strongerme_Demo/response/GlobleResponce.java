package com.Strongerme.te.Strongerme_Demo.response;

import lombok.Data;

@Data
public class GlobleResponce {
	private boolean error;
	private String massage;
	private Object data = "Nothing to Show";
	
	
	public GlobleResponce(boolean error, String massage, Object data) {
		super();
		this.error = error;
		this.massage = massage;
		this.data = data;
	}


	public GlobleResponce() {
		super();
		System.out.println("Nothing to Show");
	}
	
	

}
