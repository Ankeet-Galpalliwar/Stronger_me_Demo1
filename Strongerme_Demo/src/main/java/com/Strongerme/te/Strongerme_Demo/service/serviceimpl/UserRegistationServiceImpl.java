package com.Strongerme.te.Strongerme_Demo.service.serviceimpl;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.Strongerme.te.Strongerme_Demo.dto.UserRegistationdto;
import com.Strongerme.te.Strongerme_Demo.entity.Tokenverification;
import com.Strongerme.te.Strongerme_Demo.entity.UserRegistation;
import com.Strongerme.te.Strongerme_Demo.repository.Tokenverificatation1;
//import com.Strongerme.te.Strongerme_Demo.dto.UserRegistationdto.UserRegistation;
import com.Strongerme.te.Strongerme_Demo.repository.Userrepository;
import com.Strongerme.te.Strongerme_Demo.service.UserRegistationService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class UserRegistationServiceImpl implements UserRegistationService {

	@Autowired
	Userrepository userrepository;

	@Autowired
	Tokenverificatation1 Tokenverificatation1repo;

	@Autowired
	PasswordEncoder encoder;

	/**
	 * Api works to save User Registation Data in DB..! {password save in encode
	 * Form..!}
	 */
	@Override
	public UserRegistation resgister(UserRegistationdto user) {
		UserRegistation data = new UserRegistation();
		BeanUtils.copyProperties(user, data);
		data.setPassword(encoder.encode(user.getPassword()));
		UserRegistation save = userrepository.save(data);
		return save;
	}

	/**
	 * CALL FROM EVENT_LISTENER...! Api use for save Token/user [Expiratation time
	 * also] Expiratation Time Calculate in Entity class [ initialized value throw
	 * constructor....! ]
	 */
	@Override
	public void SaveTokenverification(String token, UserRegistation user) {
		Tokenverification tokenverification = new Tokenverification(token, user);
		Tokenverificatation1repo.save(tokenverification);
	}

	/**
	 * API works only for returning Token Status
	 */
	@Override
	public String validTokens(String token) {
		Tokenverification data = Tokenverificatation1repo.findByToken(token);
		if (data == null) {
			return "not valid";
		}
		UserRegistation user = data.getUser();
		long time = data.getExpiratationTime().getTime();

		Calendar cal = Calendar.getInstance(); // Takeing Current Time

		if ((time - cal.getTime().getTime()) <= 0) {
			Tokenverificatation1repo.delete(data);
			return "Expire";
		}
		user.setEnabled(true);
		userrepository.save(user);
		try {
			Jasperreport();    // jasper
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "valid";
	}
	
	/**
	 *  Method is use for Jasper Report
	 */
	public  void Jasperreport() throws Exception {
		
		System.err.println("jasper method run...!");
		
			List<UserRegistation> findAll = userrepository.findAll();
			File obj = ResourceUtils.getFile("src/main/resources/Simple_Blue.jrxml");  
			JasperReport jasperReport = JasperCompileManager.compileReport(obj.getAbsolutePath());
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(findAll);
//		Map<String, String> parameter=new HashMap<String, String> ();
//		parameter.put("value added "," sucessfully");
			Map parameter = null;
			JasperPrint print = JasperFillManager.fillReport(jasperReport, parameter, dataSource);
			JasperExportManager.exportReportToPdfFile(print,
					"C:\\Users\\Ankeet\\Desktop\\jasper report\\details.pdf");
	}
}





//===================Rough=============================================
/**	
// ResponseEntity<byte[]>
//JRBeanCollectionDataSource jrBeanCollectionDataSource = 
//		new JRBeanCollectionDataSource(
//		employeeService.employeeList());
JasperReport compileReport = JasperCompileManager
		.compileReport(new FileInputStream("src/main/resources/address.jrxml"));
HashMap<String, Object> hashMap = new HashMap<>();

JasperPrint fillReport = JasperFillManager.fillReport(compileReport, hashMap, dataSource.getConnection());
JasperExportManager.exportReportToPdfFile(fillReport, "C:\\techno\\address.pdf");

return new ResponseEntity<>(ResponseApi.builder().message("PDF Generated Successfully").error(false).build(),
		HttpStatus.OK);
**/
