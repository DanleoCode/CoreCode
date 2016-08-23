package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.ConnectionHelper;
import com.alacriti.leavemgmt.util.AccountStatusCode;
import com.alacriti.leavemgmt.util.EmployeeBOUtility;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.MasterTable;
import com.alacriti.leavemgmt.valueobject.Tables;

public class EmployeeDAOImplement implements EmployeeDAO {

	private Connection con;
	public static Logger logger = Logger.getLogger(EmployeeDAOImplement.class);

	public EmployeeDAOImplement(Connection con) {
		this.con = con;
	}

	@Override
	public int addEmployeeInfo(EmployeeProfile employeeInfo) 
			throws SQLException, NullPointerException, Exception{
		int generatedEmpId = -1;
		String sql = "INSERT INTO "
				+ Tables.EMPLOYEE_INFO
				+ "(emp_code,first_name,last_name,email,mobile,gender,project_id)"
				+ "VALUES(?,?,?,?,?,?,?)";
	
		PreparedStatement pStmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
	
		ConnectionHelper.closePreparedStatement(pStmt);
		
		return generatedEmpId;
	}

	@Override
	public int addEmployeeProfile(EmployeeProfile employeeProfile) 
			throws SQLException, NullPointerException, Exception{
		int UpdatedRows = -1;
		
		String sql = "INSERT INTO "
				+ Tables.EMPLOYEE_PROFILE
				+ "(emp_id,login_id,passwd,security_question_id,security_answer,"
				+ "last_modified,creation_time,emp_account_status,emp_type,"
				+ "approver1_id,approver2_id,approver3_id)"
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
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

		ConnectionHelper.closePreparedStatement(pStmt);

		return UpdatedRows;
	}

	@Override
	public EmployeeInfo selectEmployeeInfo(int empId) 
			throws SQLException, NullPointerException, Exception{
		EmployeeInfo employeeInfo = null;

		String sql = "SELECT * FROM " + Tables.EMPLOYEE_INFO
				+ " where emp_id = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, empId);
		ResultSet employeeInfoRecord = pStmt.executeQuery();
		List<EmployeeInfo> list = EmployeeBOUtility.getSelectEmployeeInfoData(employeeInfoRecord);
		if (list.size() > 0)
			employeeInfo = list.get(0);
		ConnectionHelper.closePreparedStatement(pStmt);
		return employeeInfo;
	}

	@Override
	public EmployeeProfile selectEmployeeProfile(int empId)
			throws SQLException, NullPointerException, Exception{
		
		String sql = "SELECT * FROM " + Tables.EMPLOYEE_PROFILE
				+ " where emp_id = ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, empId);
		ResultSet employeeProfileRecord = pStmt.executeQuery();
		EmployeeProfile employeeProfile = EmployeeBOUtility
				.getSelectEmployeeProfileData(employeeProfileRecord);
	
		ConnectionHelper.closePreparedStatement(pStmt);
		
		return employeeProfile;

	}

	@Override
	public int updateEmployeeProfile(EmployeeProfile profile)
			throws SQLException, NullPointerException, Exception{
		
		int updatedRows = -1;
		String sql = "UPDATE "
				+ Tables.EMPLOYEE_PROFILE
				+ " set last_modified = ?, emp_account_status = ?, "
				+ "emp_type = ?, approver1_id = ?, approver2_id =?, approver3_id = ?"
				+ " where emp_id=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setTimestamp(1, profile.getLastModifiedTime());
		pStmt.setShort(2, profile.getEmployeeAccountStatus());
		pStmt.setShort(3, profile.getEmployeeType());
		pStmt.setInt(4, profile.getApprover1());
		pStmt.setInt(5, profile.getApprover2());
		pStmt.setInt(6, profile.getApprover3());
		pStmt.setInt(7, profile.getEmpId());
		logger.info(pStmt);
		updatedRows = pStmt.executeUpdate();
	
		ConnectionHelper.closePreparedStatement(pStmt);
	
		return updatedRows;
	}

	@Override
	public int updateEmployeeInfo(EmployeeInfo employeeInfo)
			throws SQLException, NullPointerException, Exception{
		int UpdatedRows = 0;
		
		String sql = "UPDATE "
				+ Tables.EMPLOYEE_INFO
				+ " set first_name=?,last_name=?,email=?,mobile=?,gender=?,project_id=? "
				+ "where emp_id=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, employeeInfo.getFirstName());
		pStmt.setString(2, employeeInfo.getLastName());
		pStmt.setString(3, employeeInfo.getEmail());
		pStmt.setString(4, employeeInfo.getMobile());
		pStmt.setBoolean(5, employeeInfo.isGender());
		pStmt.setInt(6, employeeInfo.getProjectId());
		pStmt.setInt(7, employeeInfo.getEmpId());
		UpdatedRows = pStmt.executeUpdate();
		ConnectionHelper.closePreparedStatement(pStmt);
		return UpdatedRows;
	}

	@Override
	public int authLogin(String loginId, String passwd) 
			throws SQLException, NullPointerException, Exception{
		String sql = "SELECT emp_id FROM " + Tables.EMPLOYEE_PROFILE
				+ " WHERE login_id=? and passwd=?";// and emp_account_status =
													// ?;";
		PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, loginId);
			pStmt.setString(2, passwd);
			// pStmt.setShort(3, (short) 901);
			ResultSet empProfile = pStmt.executeQuery();
			logger.info(pStmt);
			if (empProfile.next()) {
				return empProfile.getInt("emp_id");
			}
			ConnectionHelper.closePreparedStatement(pStmt);
		return -1;
	}

	@Override
	public List<EmployeeProfile> employeeDetail(int value, String column) 
			throws SQLException, NullPointerException, Exception {
		String sql = "select * from " + Tables.EMPLOYEE_INFO + " a,"
				+ Tables.EMPLOYEE_PROFILE + " b where a.emp_id=b.emp_id and a."
				+ column + "= ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, value);
		ResultSet empProfile = pStmt.executeQuery();
		List<EmployeeProfile> list = EmployeeBOUtility.getEmployeeDetail(empProfile);

		ConnectionHelper.closePreparedStatement(pStmt);
		return list;
	}

	@Override
	public List<EmployeeProfile> getProfiles(int offset, int limit)
			throws SQLException, NullPointerException, Exception {
		logger.info("in daoimplement");
		String sql = "select * from " + Tables.EMPLOYEE_INFO + " a,"
				+ Tables.EMPLOYEE_PROFILE
				+ " b where a.emp_id=b.emp_id LIMIT ?, ?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, offset);
		pStmt.setInt(2, limit);
		logger.info(pStmt);
		ResultSet empProfile = pStmt.executeQuery();
		List<EmployeeProfile> list = EmployeeBOUtility.getEmployeeDetail(empProfile);

		ConnectionHelper.closePreparedStatement(pStmt);

		return list;
	}

	@Override
	public int createEmployeeBalanceRecord(int empId) throws SQLException,
			NullPointerException, Exception {
		int updatedRows = -1;
		String sql = "INSERT INTO "
				+ Tables.EMP_LEAVE
				+ "(emp_id,cl,pl,sl,comp_off,met_leave,pet_leave,year) Values (?,6,10,6,0,0,0,?)";
		int year = Calendar.getInstance().get(Calendar.YEAR);
		logger.info("current year is : " + year);

		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, empId);
		pStmt.setInt(2, year);
		updatedRows = pStmt.executeUpdate();

		ConnectionHelper.closePreparedStatement(pStmt);

		return updatedRows;
	}

	@Override
	public int updateEmployeeProfileAttribute(int empId, String atr,
			String value) throws SQLException, NullPointerException, Exception {
		int updatedRows = -1;
		String sql = "UPDATE " + Tables.EMPLOYEE_PROFILE + " SET " + atr
				+ " = ?, last_modified=? where emp_Id = ?";
		Timestamp date = new Timestamp(new java.util.Date().getTime());

		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, value);
		pStmt.setTimestamp(2, date);
		pStmt.setInt(3, empId);
		logger.info(pStmt);
		updatedRows = pStmt.executeUpdate();

		ConnectionHelper.closePreparedStatement(pStmt);
		return updatedRows;
	}

	@Override
	public int updateEmployeeProfileNumberAttribute(int empId, String atr,
			int value) throws SQLException, NullPointerException, Exception {
		int updatedRows = -1;
		String sql = "UPDATE " + Tables.EMPLOYEE_PROFILE + " SET " + atr
				+ " = ?, last_modified=? where emp_Id = ?";
		Timestamp date = new Timestamp(new java.util.Date().getTime());
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, value);
		pStmt.setTimestamp(2, date);
		pStmt.setInt(3, empId);
		logger.info(pStmt);
		updatedRows = pStmt.executeUpdate();

		ConnectionHelper.closePreparedStatement(pStmt);
		return updatedRows;
	}

	@Override
	public List<EmployeeInfo> getInfoByAttribute(String column, String value)
			throws NullPointerException, SQLException, Exception {
		List<EmployeeInfo> list = null;
		String sql = "SELECT * FROM " + Tables.EMPLOYEE_INFO + " where "
				+ column + " = ?";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, value);
		ResultSet employeeResult = pStmt.executeQuery();
		list = EmployeeBOUtility.getSelectEmployeeInfoData(employeeResult);
		ConnectionHelper.closePreparedStatement(pStmt);
		return list;
	}

	public List<EmployeeInfo> searchInfo(String column, String value)
			throws NullPointerException, SQLException, Exception {
		List<EmployeeInfo> list = null;
		String sql = "SELECT * FROM " + Tables.EMPLOYEE_INFO + " where "
				+ column + " LIKE '%" + value + "%'";
		PreparedStatement pStmt = con.prepareStatement(sql);
		logger.info(pStmt);
		ResultSet employeeResult = pStmt.executeQuery();
		list = EmployeeBOUtility.getSelectEmployeeInfoData(employeeResult);

		ConnectionHelper.closePreparedStatement(pStmt);

		return list;
	}

	public int getTotalEmployeeCount() throws NullPointerException,
			SQLException, Exception {
		int count = -1;
		String sql = "SELECT COUNT(*) FROM " + Tables.EMPLOYEE_INFO;
		PreparedStatement pStmt = null;

		pStmt = con.prepareStatement(sql);
		logger.info(pStmt);
		ResultSet employeeCount = pStmt.executeQuery();
		if (employeeCount.next())
			count = employeeCount.getInt(1);
		ConnectionHelper.closePreparedStatement(pStmt);
		return count;
	}

	public int getTotalNewUserCount() throws NullPointerException,
			SQLException, Exception {
		int count = -1;
		String sql = "SELECT COUNT(*) FROM " + Tables.EMPLOYEE_PROFILE
				+ " where emp_account_status = ?";
		PreparedStatement pStmt = null;
		pStmt = con.prepareStatement(sql);
		pStmt.setShort(1, AccountStatusCode.NOT_APPROVED);
		logger.info(pStmt);
		ResultSet employeeCount = pStmt.executeQuery();
		if (employeeCount.next())
			count = employeeCount.getInt(1);
		ConnectionHelper.closePreparedStatement(pStmt);
		return count;
	}

	public List<MasterTable> getMaterTableAllRecord(String tableName)
			throws NullPointerException, SQLException, Exception {
		List<MasterTable> list = new ArrayList<MasterTable>();
		String sql = "SELECT * FROM " + tableName;

		PreparedStatement pStmt = null;
		pStmt = con.prepareStatement(sql);
		ResultSet masterTableResultSet = pStmt.executeQuery();
		list = EmployeeBOUtility.getMasterTableData(masterTableResultSet);
		logger.info(pStmt);
		ConnectionHelper.closePreparedStatement(pStmt);
		return list;
	}
}
