package com.Strongerme.te.Strongerme_Demo.service;

import com.Strongerme.te.Strongerme_Demo.dto.UserRegistationdto;
import com.Strongerme.te.Strongerme_Demo.entity.UserRegistation;

public interface UserRegistationService {

	UserRegistation resgister(UserRegistationdto user);

    void SaveTokenverification(String token, UserRegistation user);
   
    String validTokens(String token);
    	
	
}
