package com.alacriti.leavemgmt.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.valueobject.UserSession;

public class SessionBOUtility {
	public static Logger logger = Logger.getLogger(SessionBOUtility.class);
	
	public static UserSession getSession(ResultSet sessionRS){
		UserSession userSession = new UserSession();
		try{
			if(sessionRS.next()){
				userSession.setEmpId(sessionRS.getInt("emp_id"));
				userSession.setEmpLoginIp(sessionRS.getString("emp_login_ip"));
				userSession.setEmployeeType(sessionRS.getShort("emp_type"));
				userSession.setEmpPrivilegeType(sessionRS.getString("emp_privilege_type"));
				userSession.setEmpSessionId(sessionRS.getString("emp_session_id"));
				userSession.setLastMOdifiedTime(sessionRS.getTimestamp("last_modified_time"));
				userSession.setSessionCreationTime(sessionRS.getTimestamp("session_creation_time"));
			}
		} catch(NullPointerException ex){
			logger.error("No session data : " + ex.getMessage());
		} catch(SQLException ex){
			logger.error("SQLException : " + ex.getMessage());
		} catch(Exception ex){
			logger.error("UnCaught Exception :" + ex.getMessage());
		}
		return userSession;
	}
}
