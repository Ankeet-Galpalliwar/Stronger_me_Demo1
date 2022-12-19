package com.Strongerme.te.Strongerme_Demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Strongerme.te.Strongerme_Demo.Event.RegistationEvent;
import com.Strongerme.te.Strongerme_Demo.dto.UserRegistationdto;
import com.Strongerme.te.Strongerme_Demo.entity.UserRegistation;
import com.Strongerme.te.Strongerme_Demo.response.GlobleResponce;
import com.Strongerme.te.Strongerme_Demo.service.UserRegistationService;

@RestController
@RequestMapping("/mycontroller")
public class Mycontroller {

	@Autowired /// FUNCTIONAL INTERFACE
	private ApplicationEventPublisher publisher;

	@Autowired
	UserRegistationService userRegistationService;

	GlobleResponce obj = new GlobleResponce();

	@PostMapping("/register")
	public ResponseEntity<GlobleResponce> Userregister(@RequestBody UserRegistationdto userdetail,
			final HttpServletRequest request) {

		UserRegistation resgister = userRegistationService.resgister(userdetail);
		String applicatationURL = applicatationURL(request);
		publisher.publishEvent(new RegistationEvent(resgister, applicatationURL));

		return new ResponseEntity<>(new GlobleResponce(false, "Data Entered Sucessfully", obj.getData()),
				HttpStatus.OK);
	}

	/**
	 * When we use link then controller Automaticly Call..!
	 */
	@GetMapping("/verifyRegistration")
	public ResponseEntity<GlobleResponce> verificatationurl(@RequestParam("token") String token) {

		String validTokens = userRegistationService.validTokens(token);
		if (validTokens == "not valid") {
			return new ResponseEntity<GlobleResponce>(new GlobleResponce(true, "USer not Verifyed ...!", obj.getData()),
					HttpStatus.NOT_FOUND);
		}
		if (validTokens == "Expire") {
			return new ResponseEntity<GlobleResponce>(
					new GlobleResponce(true, "USer not Verifyed \n Time Expire...!", obj.getData()),
					HttpStatus.REQUEST_TIMEOUT);
		}
		return new ResponseEntity<GlobleResponce>(
				new GlobleResponce(false, "USer Verifyed Sucessfually.....!", obj.getData()), HttpStatus.OK);
	}

	private String applicatationURL(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

//=======================================================================================================================
	@GetMapping("/hello")
	public String Demojsp() {
		return "hello";
	}
}
