package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.ConnectionHelper;
import com.alacriti.leavemgmt.util.EmployeeBOUtility;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Tables;

public class EmployeeDAOImplement implements EmployeeDAO {

	private Connection con;
	public static Logger logger = Logger.getLogger(EmployeeDAOImplement.class);

	public EmployeeDAOImplement(Connection con) {
		this.con = con;
	}

	@Override
	public int addEmployeeInfo(EmployeeProfile employeeInfo) {
		int generatedEmpId = 0;
		PreparedStatement pStmt = null;
		String sql = "INSERT INTO "
				+ Tables.EMPLOYEE_INFO
				+ "(emp_code,first_name,last_name,email,mobile,gender,project_id)"
				+ "VALUES(?,?,?,?,?,?,?)";
		try {
			pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pStmt.setString(1, employeeInfo.getEmpCode());
			pStmt.setString(2, employeeInfo.getFirstName());
			pStmt.setString(3, employeeInfo.getLastName());
			pStmt.setString(4, employeeInfo.getEmail());
			pStmt.setString(5, employeeInfo.getMobile());
			pStmt.setBoolean(6, employeeInfo.isGender());
			pStmt.setInt(7, employeeInfo.getProjectId());
			pStmt.executeUpdate();
			ResultSet addEmployeeInfoResult = pStmt.getGeneratedKeys();
			addEmployeeInfoResult.next();
			generatedEmpId = addEmployeeInfoResult.getInt(1);
			addEmployeeInfoResult.close();
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return generatedEmpId;
	}

	@Override
	public int addEmployeeProfile(EmployeeProfile employeeProfile) {
		int UpdatedRows = 0;
		PreparedStatement pStmt = null;
		String sql = "INSERT INTO "
				+ Tables.EMPLOYEE_PROFILE
				+ "(emp_id,login_id,passwd,security_question_id,security_answer,"
				+ "last_modified,creation_time,emp_account_status,emp_type,"
				+ "approver1_id,approver2_id,approver3_id)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, employeeProfile.getEmpId());
			pStmt.setString(2, employeeProfile.getEmpCode());
			pStmt.setString(3, employeeProfile.getPassword());
			pStmt.setInt(4, employeeProfile.getSecurityQuestionId());
			pStmt.setString(5, employeeProfile.getSecurityAnswer());
			pStmt.setTimestamp(6, employeeProfile.getLastModifiedTime());
			pStmt.setTimestamp(7, employeeProfile.getCreationTime());
			pStmt.setInt(8, employeeProfile.getEmployeeAccountStatus());
			pStmt.setInt(9, employeeProfile.getEmployeeType());
			pStmt.setInt(10, employeeProfile.getApprover1());
			pStmt.setInt(11, employeeProfile.getApprover2());
			pStmt.setInt(12, employeeProfile.getApprover3());
			UpdatedRows = pStmt.executeUpdate();

		} catch(NullPointerException ex){
			logger.error("Connection Not Found" + ex.getMessage());
		} catch (SQLException ex) {
			logger.error(ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return UpdatedRows;
	}

	@Override
	public EmployeeInfo selectEmployeeInfo(int empId) {
		PreparedStatement pStmt = null;
		ResultSet employeeInfoRecord = null;
				EmployeeInfo employeeInfo = new EmployeeInfo();
		
		String sql = "SELECT * FROM " + Tables.EMPLOYEE_INFO
				+ " where emp_id = ?";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			employeeInfoRecord = pStmt.executeQuery();
			employeeInfo = EmployeeBOUtility.getSelectEmployeeInfoData(employeeInfoRecord);
		} catch (NullPointerException ex) {
			logger.error("Connection Not Created : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("Query Didn't executed properly : " + ex.getMessage());
		} finally{
			ConnectionHelper.closePreparedStatement(pStmt);
		}

		return employeeInfo;
	}

	@Override
	public EmployeeProfile selectEmployeeProfile(int empId) {
		ResultSet employeeProfileRecord = null;
		EmployeeProfile employeeProfile = null;
		PreparedStatement pStmt = null;
		String sql = "SELECT * FROM " + Tables.EMPLOYEE_PROFILE
				+ " where emp_id = ?";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			employeeProfileRecord = pStmt.executeQuery();
			employeeProfile = EmployeeBOUtility.getSelectEmployeeProfileData(employeeProfileRecord);
		} catch (NullPointerException ex) {
			logger.error("Connection Not Created : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.error("Query Didn't executed properly : " + ex.getMessage());
		} finally{
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return employeeProfile;

	}

	@Override
	public void updateEmployeeProfile() {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateEmployeeInfo(EmployeeInfo employeeInfo) {
		int UpdatedRows = 0;
		PreparedStatement pStmt = null;
		String sql = "UPDATE "
				+ Tables.EMPLOYEE_INFO
				+ " set first_name=?,last_name=?,email=?,mobile=?,gender=?,project_id=? "
				+ "where emp_id=?";
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setString(1, employeeInfo.getFirstName());
			pStmt.setString(2, employeeInfo.getLastName());
			pStmt.setString(3, employeeInfo.getEmail());
			pStmt.setString(4, employeeInfo.getMobile());
			pStmt.setBoolean(5, employeeInfo.isGender());
			pStmt.setInt(6, employeeInfo.getProjectId());
			pStmt.setInt(7, employeeInfo.getEmpId());
			UpdatedRows = pStmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return UpdatedRows;
	}

	public int authLogin(String loginId, String passwd) {
		String sql = "SELECT emp_id FROM " + Tables.EMPLOYEE_PROFILE
				+ " WHERE login_id=? and passwd=? and emp_account_status = ?;";
		PreparedStatement pStmt = null;
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, passwd);
			pStmt.setShort(3, (short) 901);
			ResultSet empProfile = pStmt.executeQuery();
			logger.info("retrieved id : " );
			if (empProfile.next()){
				return empProfile.getInt("emp_id");
			}
		} catch (NullPointerException ex) {
			logger.info("Connection Not Found");
		} catch (SQLException ex) {
			logger.error("SQLException " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return -1;
	}

	public EmployeeProfile employeeDetail(int empId) {				//returns a full profile a Employee id = empId
		String sql = "select * from " + Tables.EMPLOYEE_INFO + " a,"
				+ Tables.EMPLOYEE_PROFILE
				+ " b where a.emp_id=b.emp_id and a.emp_id= ?";
		PreparedStatement pStmt = null;
		try {
			pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			ResultSet empProfile = pStmt.executeQuery();
			return EmployeeBOUtility.getEmployeeDetail(empProfile);
		} catch (NullPointerException ex) {
			logger.info("Connection Not Found");
		} catch (SQLException ex) {
			logger.error("SQLException " + ex.getMessage());
		} finally {
			ConnectionHelper.closePreparedStatement(pStmt);
		}
		return null;
	}
}
