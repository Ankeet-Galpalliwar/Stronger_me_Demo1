package com.Strongerme.te.Strongerme_Demo.entity;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Tokenverification {
	static int expiratationtime=10;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int TvId;
	private String token;
	private Date expiratationTime;
	
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid", nullable = false, foreignKey =@ForeignKey(name="FK_User_Token"))
	private UserRegistation user;
	
	/**
	 *  This Constructoer is use when work with Both Tokens and User..!
	 * 
	 */
	
	public Tokenverification( String  Token,UserRegistation user){
		super();
		this.token=Token;
		this.user=user;
		this.expiratationTime=calculateExpiratationtime(expiratationtime);
	}
	
	/**
	 * This Constructoer is use when work with only tokens...!
	 * 
	 */
	
	public Tokenverification(String Token) {
		this.token=Token;
		this.expiratationTime=calculateExpiratationtime(expiratationtime);
	}
	
   // here we are calculate Expire  Time..
	private Date calculateExpiratationtime(int expiratationtime) {
		Calendar cal=Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(cal.MINUTE, expiratationtime);
		return new Date(cal.getTime().getTime());
   }
}
