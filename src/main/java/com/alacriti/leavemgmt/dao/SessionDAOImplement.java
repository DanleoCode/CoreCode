package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.util.SessionBOUtility;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Tables;
import com.alacriti.leavemgmt.valueobject.UserSession;

public class SessionDAOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(SessionDAOImplement.class);
	
	public SessionDAOImplement(){
		
	}
	
	public SessionDAOImplement(Connection con){
		this.con = con;
	}
	
	public int createUserSession(UserSession userSession, EmployeeProfile employeeProfile){
		int numberRowsUpdated = -1;
		PreparedStatement pStmt = null;
		String sql = "INSERT INTO "+ Tables.SESSION_TABLE
				+ "(emp_id,emp_type,emp_login_ip,emp_privilege_type,session_creation_time,emp_session_id,last_modified_time )"
				+ " VALUES (?,?,?,?,?,?,?)";
		try{
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, userSession.getEmpId());
			pStmt.setInt(2, employeeProfile.getEmployeeType());
			pStmt.setString(3, userSession.getEmpLoginIp());
			pStmt.setString(4, userSession.getEmpPrivilegeType());
			pStmt.setTimestamp(5, userSession.getSessionCreationTime());
			pStmt.setString(6, userSession.getEmpSessionId());
			pStmt.setTimestamp(7, userSession.getLastMOdifiedTime());
			numberRowsUpdated = pStmt.executeUpdate();
		} catch(NullPointerException ex){
			logger.error("ERROR : " + ex.getMessage());
		} catch(SQLException ex){
			logger.error("SQLException : " + ex.getMessage());
		} catch(Exception ex){
			logger.error("UnCaught Exception :" + ex.getMessage());
		} finally{
			try {
				pStmt.close();
			} catch (SQLException ex) {
				logger.error("NO worries." + ex.getMessage());
			}
		}
		return numberRowsUpdated;
	}
	
	public UserSession getSession(String sessionId){
		logger.info("Searching for sessionId");
		UserSession userSession = null;
		String sql = "SELECT * FROM " +  Tables.SESSION_TABLE +" WHERE emp_session_id = ?";
		PreparedStatement pStmt = null;
		try{
			pStmt = con.prepareStatement(sql);
			pStmt.setString(1, sessionId);
			ResultSet sessionResultSet = pStmt.executeQuery();
			userSession = SessionBOUtility.getSession(sessionResultSet);
		} catch(NullPointerException ex){
			logger.error("ERROR : " + ex.getMessage());
		} catch(SQLException ex){
			logger.error("SQLException : " + ex.getMessage());
		} catch(Exception ex){
			logger.error("UnCaught Exception :" + ex.getMessage());
		} finally{
			try {
				pStmt.close();
			} catch (SQLException ex) {
				logger.error("NO worries." + ex.getMessage());
			}
		}
		return userSession;
	}
}
