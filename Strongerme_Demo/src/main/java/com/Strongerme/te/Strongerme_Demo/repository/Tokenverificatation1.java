 package com.Strongerme.te.Strongerme_Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Strongerme.te.Strongerme_Demo.entity.Tokenverification;
@Repository
public interface Tokenverificatation1 extends JpaRepository<Tokenverification,Integer> {
	
	Tokenverification findByToken(String token);
							

}
