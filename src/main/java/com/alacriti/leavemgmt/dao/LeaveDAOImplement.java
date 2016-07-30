package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveInstance;
import com.alacriti.leavemgmt.valueobject.Tables;

public class LeaveDAOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(LeaveDAOImplement.class);

	public LeaveDAOImplement() {

	}

	public LeaveDAOImplement(Connection con) {
		this.con = con;
	}

	public long AddLeaveInstance(Leave leave) {
		long generatedLeaveId = -1;
		PreparedStatement pStmt = null;
		String sql = "INSERT INTO "
				+ Tables.LEAVE_INSTANCE
				+ "(leave_type_id,start_date,end_date,No_of_days,emp_id,project_id) VALUES(?,?,?,?,?,?)";
		try {
			pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pStmt.setShort(1, leave.getLeaveTypeId());
			pStmt.setDate(2, leave.getStartdate());
			pStmt.setDate(3, leave.getEndDate());
			pStmt.setShort(4, leave.getNoOfDays());
			pStmt.setInt(5, leave.getEmpId());
			pStmt.setInt(6, leave.getProjectId());
			pStmt.executeUpdate();
			ResultSet addLeaveInstanceResult = pStmt.getGeneratedKeys();
			addLeaveInstanceResult.next();
			generatedLeaveId = addLeaveInstanceResult.getInt(1);
			addLeaveInstanceResult.close();
			logger.info("AddleaveInstance Executed");
		} catch (SQLException ex) {
			logger.error("SQLEXCEPTION OCCURED:" + ex.getMessage());
		} finally {
			try {
				pStmt.close();
			} catch (SQLException e) {
				logger.error("Unable to close PreparedStatement. "
						+ e.getMessage());
			}
		}
		return generatedLeaveId;
	}

	public int addNewEmployeeLeaveInstance(LeaveInstance leaveInstance) {
		int updatedRows = -1;
		PreparedStatement pStmt = null;
		String sql = "INSERT INTO "
				+ Tables.EMPLOYEE_LEAVE_INSTANCE
				+ "(leave_id, emp_id,leave_status_code,creation_time,"
				+ "last_modified_time,approver1_id,approver2_id,approver3_id) "
				+ "VALUES(?,?,?,?,?,?,?,?)";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setLong(1, leaveInstance.getLeaveId());
			pStmt.setInt(2, leaveInstance.getEmployeeProfile().getEmpId());
			pStmt.setShort(3, leaveInstance.getLeaveStatusCode());
			pStmt.setTimestamp(4, leaveInstance.getCreationTime());
			pStmt.setTimestamp(5, leaveInstance.getLastModified());
			pStmt.setInt(6, leaveInstance.getEmployeeProfile().getApprover1());
			pStmt.setInt(7, leaveInstance.getEmployeeProfile().getApprover2());
			pStmt.setInt(8, leaveInstance.getEmployeeProfile().getApprover3());
			updatedRows = pStmt.executeUpdate();
			logger.info("empleaveinstance executed");
		} catch (SQLException ex) {
			logger.debug("SQLException : " + ex.getMessage());
			logger.error("SQLException : " + ex.getMessage());
		} finally{
			try {
				pStmt.close();
			} catch(NullPointerException ex){
				logger.debug("PerparedStatement not opened : " + ex.getMessage());
				logger.error("PerparedStatement not opened : " + ex.getMessage());
			}
			catch (SQLException ex) {
				logger.debug("PerparedStatement has problem : " + ex.getMessage());
				logger.error("PerparedStatement has problem : " + ex.getMessage());
			}
		}
		
		return updatedRows;
	}
}
