package com.alacriti.leavemgmt.deligate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.bo.SessionBOImplement;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.LoginCredential;
import com.alacriti.leavemgmt.valueobject.UserSession;

public class AuthDeligate {

	public static Logger logger = Logger.getLogger(AuthDeligate.class);

	public static List<EmployeeProfile> validateCredentials(
			LoginCredential tempCred, UserSession session) {
		List<EmployeeProfile> list = new ArrayList<EmployeeProfile>();
		EmployeeBOImplement employeeBOImplement = new EmployeeBOImplement();
		String loginId = tempCred.getUser();
		String passwd = tempCred.getPass();
		list = employeeBOImplement.AutherizedAccess(loginId, passwd);
		logger.info("retuened back to deligete");
		if (list.size() == 1) {
			logger.info("passed the test");
			int empId = list.get(0).getEmpId();
			short empType = list.get(0).getEmployeeType();
			Timestamp lastMOdifiedTime = new Timestamp(
					new java.util.Date().getTime());

			session.setLastMOdifiedTime(lastMOdifiedTime);
			session.setEmployeeType(empType);
			session.setEmpId(empId);

			logger.info("emp id in auth deligate  " + list.get(0).getEmpId());
			if (list.get(0).getEmpId() > 0) {
				logger.info("valid user");
				SessionBOImplement sessionBOImplement = new SessionBOImplement();
				if (getSession(empId).getEmpId() == empId) {
					logger.info("empId exist");
					sessionBOImplement.updateSessionBO(session);
				} else {
					logger.info("empId does not exist");
					sessionBOImplement.createUserSession(session,
							list.get(0));
				}
			}
		} else{
			logger.info("failed");
		}
		return list;
	}

	public static UserSession getSession(int empId) {
		logger.info("In getsession DEloigate");
		UserSession session = new UserSession();
		session.setEmpId(empId);
		SessionBOImplement sessionBOImplement = new SessionBOImplement();
		return sessionBOImplement.getSession(session);

	}
}
