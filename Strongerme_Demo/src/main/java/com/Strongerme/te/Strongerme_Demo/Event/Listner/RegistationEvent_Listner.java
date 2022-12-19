package com.Strongerme.te.Strongerme_Demo.Event.Listner;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.Strongerme.te.Strongerme_Demo.Event.RegistationEvent;
import com.Strongerme.te.Strongerme_Demo.entity.UserRegistation;
import com.Strongerme.te.Strongerme_Demo.service.UserRegistationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegistationEvent_Listner implements ApplicationListener<RegistationEvent> {

	@Autowired
	UserRegistationService registationService;

//	@Autowired
//	JavaMailSender javaMailSender;

	@Override
	@Transactional
	public void onApplicationEvent(RegistationEvent event) {

		UserRegistation userdetails = event.getUserdetails(); // Get User Details

		String token = UUID.randomUUID().toString(); // Token generated
		registationService.SaveTokenverification(token, userdetails);

		String url = event.getApplicatationurl() + "/mycontroller/verifyRegistration?token=" + token;

		// call Method For Mail Send...
		SendMail(url, userdetails);

		// call method for OTP send ....
		try {
			sendotp(); // Mobile Nubmber will be hard Coaded..
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// Authentication URL print in console..
		log.info("Click the link to verify your account:{ " + url + " }");
	}

//====================================================================================================	

	/**
	 * LOGIC FOR SEND EMAIL.....!
	 */
	private void SendMail(String url, UserRegistation userdetails) {

		String host = "smtp.gmail.com";
		
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", 465);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("dummymail.fake.massage@gmail.com", "psamhyawiykfxfba");
			}
		});
		session.setDebug(true);
		// compose the massage
		MimeMessage obj = new MimeMessage(session);
		try {
			obj.setFrom(new InternetAddress("dummymail.fake.massage@gmail.com"));
			obj.addRecipient(Message.RecipientType.TO, new InternetAddress(userdetails.getEmailid()));
			obj.setSubject("Thank You for Registation... click the link for Authentication");
//			obj.setText(url + "  \n" + "From Ankit....! " );
			MimeMultipart mimeMultipart=new MimeMultipart();
			MimeBodyPart textpart=new MimeBodyPart();
			MimeBodyPart filepart=new MimeBodyPart();
			
			
			textpart.setText(url + "  \n" + "From Ankit....! "+"\n Attachmeant Pdf is Password Protected\n");
			try {
				filepart.attachFile(new File("C:\\Users\\Ankeet\\Desktop\\jasper report\\address.txt"));
			} catch (IOException e) {//"C:\Users\Ankeet\Desktop\jasper report\address.txt"
				e.printStackTrace();
			}
			
			mimeMultipart.addBodyPart(textpart);
			mimeMultipart.addBodyPart(filepart);
			obj.setContent(mimeMultipart);
			// send massage to Transport class..
			Transport.send(obj);
			log.info("Mail send sucessfuly.....!");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Exceptation print.........!");
		}
	}
//========================================================================================================

	/**
	 * LOGIC FOR GENERATE OTP.....!
	 */
	private int Otpgenerate() {

		String numbers = "0123456789";
		Random rndm_method = new Random();

		char[] otp = new char[6];

		for (int i = 0; i < 6; i++) {
			otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
		}
		String otpvalue = new String(otp);
		log.info("otp===>>" + otpvalue);
		int OTP = Integer.parseInt(otpvalue);
		return OTP;
	}

	/**
	 * LOGIC FOR SEND OTP
	 * @throws UnsupportedEncodingException
	 */

	private void sendotp() throws UnsupportedEncodingException {
		String Apikey = "69bZg0WavVF8zV3rfHhW2zn1KkBa6VciJMFP9VYlESfytD6f1hVPu7ZlSCRL";
		// String sendID="FSTSMS";
		// String massage=URLEncoder.encode((Otpgenerate()+"\n"+url),"UTF-8");
		// String language="english";
		int variables_values = Otpgenerate(); // (int) (Math.random() * 9000) + 1000;
		String route = "otp";
		String myurl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + Apikey + "&route=" + route
				+ "&numbers=8329498635" + "&variables_values=" + variables_values;
		try {
			URL url = new URL(myurl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestMethod("GET");
//			con.setRequestProperty("cache-control", "no-cache");
//			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			int responseCode = con.getResponseCode();
//			con.getResponseMessage();
			log.info("responseCode=>" + responseCode);

		} catch (Exception e) {
			e.printStackTrace();
			log.error("exceptation print......!");
		}
	}
}



// =========ROUGH=============

/**
 * An0ther logic for MailSend....
 */

/*
 * SimpleMailMessage mailMessage=new SimpleMailMessage();
 * mailMessage.setFrom("dummymail.fake.massage@gmail.com");
 * mailMessage.setTo(userdetails.getEmailid()); mailMessage.setText(url);
 * mailMessage.setSubject("Registstion Authantication");
 * javaMailSender.send(mailMessage);
 */
