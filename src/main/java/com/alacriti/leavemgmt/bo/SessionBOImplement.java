package com.alacriti.leavemgmt.bo;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.dao.SessionDAOImplement;
import com.alacriti.leavemgmt.deligate.AuthDeligate;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.UserSession;

public class SessionBOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(AuthDeligate.class);
	
	public SessionBOImplement(){
		this.con =ConnectionHelper.getConnection();
	}
	
	public int createUserSession(UserSession userSession, EmployeeProfile employeeProfile){
		int updatedRows = -1;
		SessionDAOImplement sessionDAOImplement = new SessionDAOImplement(this.con);
		updatedRows = sessionDAOImplement.createUserSession(userSession, employeeProfile);
		logger.info("updated rows : " + updatedRows);
		ConnectionHelper.commitConnection(con);
		ConnectionHelper.finalizeConnection(con);
		return updatedRows;
	}
	
	public UserSession getSession(UserSession session){
		UserSession userSession = null;
		int empId = session.getEmpId();
		SessionDAOImplement sessionDAOImplement = new SessionDAOImplement(this.con);
		userSession = sessionDAOImplement.getSession(empId);
		return userSession;
	}
	
	public int updateSessionBO(UserSession session){
		int updatedrows  = -1;
		SessionDAOImplement sessionDAOImplement = new SessionDAOImplement(this.con);
		updatedrows = sessionDAOImplement.updateSession(session);
		ConnectionHelper.commitConnection(con);
		ConnectionHelper.finalizeConnection(con);
		return updatedrows;
	}
}
