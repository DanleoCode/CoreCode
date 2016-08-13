package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.ConnectionHelper;
import com.alacriti.leavemgmt.util.SessionBOUtility;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Tables;
import com.alacriti.leavemgmt.valueobject.UserSession;

public class SessionDAOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(SessionDAOImplement.class);

	public SessionDAOImplement() {

	}

	public SessionDAOImplement(Connection con) {
		this.con = con;
	}

	public int createUserSession(UserSession userSession,
			EmployeeProfile employeeProfile) {
		int numberRowsUpdated = -1;
		PreparedStatement pStmt = null;
		String sql = "INSERT INTO "
				+ Tables.SESSION_TABLE
				+ "(emp_id,emp_type,emp_login_ip,emp_privilege_type"
				+ ",session_creation_time,emp_session_id,last_modified_time )"
				+ " VALUES (?,?,?,?,?,?,?)";
		try {
			logger.info("in session dao");
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, userSession.getEmpId());
			pStmt.setInt(2, employeeProfile.getEmployeeType());
			pStmt.setString(3, userSession.getEmpLoginIp());
			pStmt.setString(4, userSession.getEmpPrivilegeType());
			pStmt.setTimestamp(5, userSession.getSessionCreationTime());
			pStmt.setString(6, userSession.getEmpSessionId());
			pStmt.setTimestamp(7, userSession.getLastMOdifiedTime());
			numberRowsUpdated = pStmt.executeUpdate();
		} catch (NullPointerException ex) {
			logger.error("ERROR : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("UnCaught Exception :" + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return numberRowsUpdated;
	}

	public UserSession getSession(int empId) {
		logger.info("Searching for sessionId");
		UserSession userSession = null;
		String sql = "SELECT * FROM " + Tables.SESSION_TABLE
				+ " WHERE emp_id = ?";
		PreparedStatement pStmt = null;
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			ResultSet sessionResultSet = pStmt.executeQuery();
			logger.info(pStmt);
			userSession = SessionBOUtility.getSession(sessionResultSet);
		} catch (NullPointerException ex) {
			logger.error("ERROR : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("UnCaught Exception :" + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return userSession;
	}

	public int updateSession(UserSession userSession) {
		int updatedrows = -1;
		PreparedStatement pStmt = null;
		String sql = "UPDATE "
				+ Tables.SESSION_TABLE
				+ " set emp_type = ?, emp_session_id = ?, last_modified_time = ? where emp_Id = ?;";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, userSession.getEmployeeType());
			pStmt.setString(2,userSession.getEmpSessionId());
			pStmt.setTimestamp(3, userSession.getLastMOdifiedTime());
			pStmt.setInt(4, userSession.getEmpId());
			updatedrows = pStmt.executeUpdate();
		} catch (NullPointerException ex) {
			logger.error("ERROR : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("update SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("UnCaught Exception :" + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return updatedrows;
	}
}
