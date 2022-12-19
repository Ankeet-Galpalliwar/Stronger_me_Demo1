package com.Strongerme.te.Strongerme_Demo.Event;

import org.springframework.context.ApplicationEvent;

import com.Strongerme.te.Strongerme_Demo.entity.UserRegistation;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RegistationEvent extends ApplicationEvent {
	
	private UserRegistation userdetails;
	private String applicatationurl;

	public RegistationEvent(UserRegistation userdetails, String applicatationurl) {
		super(userdetails);
		this.applicatationurl=applicatationurl;
		this.userdetails=userdetails;
 	}
}
