package com.Strongerme.te.Strongerme_Demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Strongerme.te.Strongerme_Demo.entity.UserRegistation;
@Repository
public interface Userrepository extends JpaRepository<UserRegistation,Integer > {
	
//  @Query("select * from UsetRegistaion u where id=?")
//	@Query("select a from UserRegistation a Where a.id=:n and a.name=:m")
//	@Query(value="select * from UserUegistation", nativeQuery =true)
	//public UserRegistation user(@Param("n")Integer id,@Param("m")String name);
	
}
