package com.alacriti.leavemgmt.bo;

import java.sql.Connection;

import com.alacriti.leavemgmt.dao.SessionDAOImplement;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.UserSession;

public class SessionBOImplement {
	private Connection con;
	
	public SessionBOImplement(){
		this.con =ConnectionHelper.getConnection();
	}
	
	public int createUserSession(UserSession userSession, EmployeeProfile employeeProfile){
		int updatedRows = -1;
		SessionDAOImplement sessionDAOImplement = new SessionDAOImplement(this.con);
		updatedRows = sessionDAOImplement.createUserSession(userSession, employeeProfile);
		ConnectionHelper.finalizeConnection(con);
		return updatedRows;
	}
	
	public UserSession getSession(UserSession session){
		UserSession userSession = null;
		String sessionId = session.getEmpSessionId();
		SessionDAOImplement sessionDAOImplement = new SessionDAOImplement(this.con);
		userSession = sessionDAOImplement.getSession(sessionId);
		return userSession;
	}
}
