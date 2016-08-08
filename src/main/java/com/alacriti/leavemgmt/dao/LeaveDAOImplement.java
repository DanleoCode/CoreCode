package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.ConnectionHelper;
import com.alacriti.leavemgmt.util.LeaveBoUtility;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveHistory;
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
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return generatedLeaveId;
	}

	public int addNewEmployeeLeaveInstance(LeaveHistory leaveInstance) {
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
			ConnectionHelper.closePreparedStatement(pStmt);
		}

		return updatedRows;
	}

	public int updateLeaveStatus(LeaveHistory leaveInstance) {
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
			ConnectionHelper.closePreparedStatement(pStmt);
		}

		return updatedRows;
	}

	public List<Employee> getPendingLeaveApproval(int empId,
			short leaveStatusCode, String approverLevel) {
		String sql = "select * from " + Tables.EMPLOYEE_LEAVE_INSTANCE
				+ " where " + approverLevel + " = ? AND leave_status_code = ?";
		PreparedStatement pStmt = null;
		ResultSet approver1List = null;
		List<Employee> list = null;
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			pStmt.setShort(2, leaveStatusCode);
			logger.info(pStmt);
			approver1List = pStmt.executeQuery();
			list = LeaveBoUtility.getLeaveApprovalList(approver1List);
			logger.info("got executed");
		} catch (NullPointerException ex) {
			logger.info("Connection not found.");
			logger.error("Connection not found");
		} catch (SQLException ex) {
			logger.info("SQLException : " + ex.getMessage());
			logger.error("Connection not found");
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
			;
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
		} catch (Exception ex) {
			logger.error("Uncaught Exception : " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return list;
	}

	public int newLeaveBalance(LeaveBalance leaveBalance){
		int updatedRows = -1;
		String sql = "INSERT INTO " + Tables.EMP_LEAVE
				+ "(emp_id, cl, pl, comp_off, met_leave, pet_leave, fin_year, sl)"
				+ " values (?,?,?,?,?,?,?,?)";
		PreparedStatement pStmt = null;
		try{
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, leaveBalance.getEmpId());
			pStmt.setShort(2, leaveBalance.getCasualLeave());
			pStmt.setShort(3, leaveBalance.getPrivilegeLeave());
			pStmt.setShort(4, leaveBalance.getCompOff());
			pStmt.setShort(5, leaveBalance.getMetLeave());
			pStmt.setShort(6, leaveBalance.getPetLeave());
			pStmt.setInt(7, leaveBalance.getFinancialyear());
			pStmt.setInt(8, leaveBalance.getSickLeave());
			
			logger.info(pStmt);
			updatedRows = pStmt.executeUpdate();
			
		} catch (NullPointerException ex) {
			logger.error("Something not correct in newLeaveBalance: " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("Uncaught Exception : " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return updatedRows;
	}
	
	public int updateLeaveBalance(LeaveBalance leaveBalance){
		int updatedRows = -1;
		String sql = "UPDATE " + Tables.EMP_LEAVE 
				+ " set cl=?, pl=?, comp_off=?, met_leave=?, pet_leave=?, fin_year=?, sl=? where emp_id=?";
		PreparedStatement pStmt = null;
		try{
			pStmt = con.prepareStatement(sql);
			pStmt.setShort(1, leaveBalance.getCasualLeave());
			pStmt.setShort(2, leaveBalance.getPrivilegeLeave());
			pStmt.setShort(3, leaveBalance.getCompOff());
			pStmt.setShort(4, leaveBalance.getMetLeave());
			pStmt.setShort(5, leaveBalance.getPetLeave());
			pStmt.setInt(6, leaveBalance.getFinancialyear());
			pStmt.setInt(7, leaveBalance.getSickLeave());
			pStmt.setInt(8, leaveBalance.getEmpId());
			
			logger.info(pStmt);
			updatedRows = pStmt.executeUpdate();
			
		} catch (NullPointerException ex) {
			logger.error("Something not correct updateLeaveBalance: " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("Uncaught Exception : " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return updatedRows;
	}
	
	public LeaveBalance getLeaveBalance(int empId, int year) {
		PreparedStatement pStmt = null;
		LeaveBalance leaveBalance = null;
		String sql = "SELECT * FROM  " + Tables.EMP_LEAVE
				+ " where emp_id = ? and fin_year = ?";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			pStmt.setInt(2, year);
			ResultSet empLeaveResultSet = pStmt.executeQuery();
			leaveBalance = LeaveBoUtility.getEmployeeBalance(empLeaveResultSet);
		} catch (NullPointerException ex) {
			logger.error("Something not correct getLeaveBalance : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("Uncaught Exception : " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return leaveBalance;
	}

	public Leave getLeaveById(long leaveId) {
		Leave leave = null;
		PreparedStatement pStmt = null;
		String sql = "SELECT * FROM " + Tables.LEAVE_INSTANCE
				+ " WHERE leave_id = ?;";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setLong(1, leaveId);
			ResultSet leaveResultSet = pStmt.executeQuery();
			leave = LeaveBoUtility.getLeaveInstanceData(leaveResultSet);
			leaveResultSet.close();
		} catch (NullPointerException ex) {
			logger.error("Something not correct in getLeaveById: " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("Uncaught Exception : " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return leave;
	}
	
	public LeaveHistory getLeaveHistoryById(long leaveId){
		LeaveHistory history = null;
		PreparedStatement pStmt = null;
		String sql = "SELECT * FROM " + Tables.EMPLOYEE_LEAVE_INSTANCE
				+ " WHERE leave_id = ?;";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setLong(1, leaveId);
			ResultSet leaveResultSet = pStmt.executeQuery();
			history = LeaveBoUtility.getLeaveHistoryById(leaveResultSet);
			leaveResultSet.close();
		} catch (NullPointerException ex) {
			logger.error("Something not correct in LeaveHistoryBYId: " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("SQLException : " + ex.getMessage());
		} catch (Exception ex) {
			logger.error("Uncaught Exception : " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return history;
	}
}
