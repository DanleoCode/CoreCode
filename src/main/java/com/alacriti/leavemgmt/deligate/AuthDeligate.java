package com.alacriti.leavemgmt.deligate;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.bo.SessionBOImplement;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.LoginCredential;
import com.alacriti.leavemgmt.valueobject.UserSession;

public class AuthDeligate {

	public static Logger logger = Logger.getLogger(AuthDeligate.class);
	public static EmployeeProfile validateCredentials(LoginCredential tempCred, UserSession session){
		
		EmployeeBOImplement employeeBOImplement = new EmployeeBOImplement();
		String loginId = tempCred.getUser();
		String passwd = tempCred.getPass();
		EmployeeProfile employeeProfile = employeeBOImplement.AutherizedAccess(loginId, passwd);
		session.setEmpId(employeeProfile.getEmpId());
		if(employeeProfile.getEmpId() > 0){
			logger.info("valid user");
			SessionBOImplement sessionBOImplement = new SessionBOImplement();
			sessionBOImplement.createUserSession(session, employeeProfile);
		}
		return employeeProfile;
	}
	
	public static UserSession getSession(String sessionId){
		logger.info("In getsession DEloigate");
		UserSession session = new UserSession();
		session.setEmpSessionId(sessionId);
		SessionBOImplement sessionBOImplement = new SessionBOImplement();
		return sessionBOImplement.getSession(session);
		
	}
}
