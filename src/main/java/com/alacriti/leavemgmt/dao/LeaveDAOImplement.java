package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.util.LeaveBoUtility;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
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
				+ "(leave_type_id,start_date,end_date,No_of_days,emp_id,project_id,message,tag) VALUES(?,?,?,?,?,?,?,?)";
		try {
			pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pStmt.setShort(1, leave.getLeaveTypeId());
			pStmt.setDate(2, leave.getStartdate());
			pStmt.setDate(3, leave.getEndDate());
			pStmt.setShort(4, leave.getNoOfDays());
			pStmt.setInt(5, leave.getEmpId());
			pStmt.setInt(6, leave.getProjectId());
			pStmt.setString(7, leave.getMessage());
			pStmt.setString(8, leave.getTag());
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
		String sql = "INSERT INTO " + Tables.EMPLOYEE_LEAVE_INSTANCE
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
		} finally {
			try {
				pStmt.close();
			} catch (NullPointerException ex) {
				logger.debug("PerparedStatement not opened : "
						+ ex.getMessage());
				logger.error("PerparedStatement not opened : "
						+ ex.getMessage());
			} catch (SQLException ex) {
				logger.debug("PerparedStatement has problem : "
						+ ex.getMessage());
				logger.error("PerparedStatement has problem : "
						+ ex.getMessage());
			}
		}

		return updatedRows;
	}

	public int updateLeaveStatus(LeaveInstance leaveInstance) {
		String sql = "UPDATE "
				+ Tables.EMPLOYEE_LEAVE_INSTANCE
				+ " SET leave_status_code = ? , last_modified_time = ? Where leave_Id = ?";
		int updatedRows = -1;
		PreparedStatement pStmt = null;
		try {
			logger.info("Levae dao");
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, leaveInstance.getLeaveStatusCode());
			pStmt.setTimestamp(2, leaveInstance.getLastModified());
			pStmt.setLong(3, leaveInstance.getLeaveId());
			updatedRows = pStmt.executeUpdate();
		} catch (NullPointerException ex) {
			logger.info("Connection not found.");
			logger.error("Connection not found");
		} catch (SQLException ex) {
			logger.info("SQLException : " + ex.getMessage());
			logger.error("SQLException : " + ex.getMessage());
		} finally {
			try {
				pStmt.close();
			} catch (NullPointerException ex) {
				logger.info("PreparedStatement is null.");
				logger.error("PreparedStatement is null.");
			} catch (SQLException ex) {
				logger.error("SQLException : " + ex.getMessage());
			}
		}

		return updatedRows;
	}

	public List<LeaveInstance> getPendingLeaveApproval(int empId,
			short leaveStatusCode) {
		String sql = "select * from " + Tables.EMPLOYEE_LEAVE_INSTANCE
				+ " where approver1_id = ? AND leave_status_code = ?";
		PreparedStatement pStmt = null;
		ResultSet approver1List = null;
		List<LeaveInstance> list = null;
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			pStmt.setShort(2, leaveStatusCode);
			approver1List = pStmt.executeQuery();
			list = LeaveBoUtility.getLeaveApprovalList(approver1List);
		} catch (NullPointerException ex) {
			logger.info("Connection not found.");
			logger.error("Connection not found");
		} catch (SQLException ex) {
			logger.info("SQLException : " + ex.getMessage());
			logger.error("Connection not found");
		} finally {
			if (pStmt != null) {
				try {
					pStmt.close();
				} catch (NullPointerException ex) {
					logger.info("preparedStatement is null.");
					logger.error("preparedStatement is null");
				} catch (SQLException ex) {
					logger.info("SQLException : " + ex.getMessage());
				}
			}
		}
		return list;
	}

	public List<EmployeeLeaveHistory> getAllLeaves(int empId) {
		String sql = "SELECT * FROM " + Tables.EMPLOYEE_LEAVE_INSTANCE + " a, "
				+ Tables.LEAVE_INSTANCE
				+ " b where a.leave_id = b.leave_id and a.emp_id=?";
		PreparedStatement pStmt = null;
		List<EmployeeLeaveHistory> list = new ArrayList<EmployeeLeaveHistory>();
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			ResultSet employeeLeaveHistory = pStmt.executeQuery();
			list = LeaveBoUtility.getEmployeeLeaveHistory(employeeLeaveHistory);
		} catch (NullPointerException ex) {
			logger.error("Something not correct : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch(Exception ex){
			logger.error("Uncaught Exception : " + ex.getMessage());
		}
		return list;
	}
	
	public LeaveBalance getLeaveBalance(int empId, int year){
		PreparedStatement pStmt = null;
		LeaveBalance leaveBalance = null;
		String sql = "SELECT * FROM  " +Tables.EMP_LEAVE + " where emp_id = ? and fin_year = ?";
		try{
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			pStmt.setInt(2,year);
			ResultSet empLeaveResultSet = pStmt.executeQuery();
			leaveBalance = LeaveBoUtility.getEmployeeBalance(empLeaveResultSet);
		} catch (NullPointerException ex) {
			logger.error("Something not correct : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch(Exception ex){
			logger.error("Uncaught Exception : " + ex.getMessage());
		}
		return leaveBalance;
	}
}
