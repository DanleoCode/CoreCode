package leavemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import leavemanagement.bo.ConnectionHelper;
import leavemanagement.util.LogRecord;

public class EmployeeService implements EmployeeDAO {

	//public static Logger logger = Logger.getLogger(EmployeeService.class);

	@Override
	public void addEmployeeInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addEmployeeProfile() {
		// TODO Auto-generated method stub

	}

	@Override
	public ResultSet selectEmployeeInfo(int empId) {
		ResultSet EmployeeInfoRecord = null;
		Connection con = ConnectionHelper.getConnection();
		String sql = "SELECT * FROM 185_emp_info_table where emp_id = ?";
		try {
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			EmployeeInfoRecord = pStmt.executeQuery();
		} catch (NullPointerException ex) {
			LogRecord.logger.error("Connection Not Created : " + ex.getMessage());
		} catch (SQLException ex) {
			LogRecord.logger.error("Query Didn't executed properly : " + ex.getMessage());
		}
		return EmployeeInfoRecord;
	}

	@Override
	public void selectEmployeeProfile(int empId) {
		ResultSet EmployeeInfoRecord = null;
		Connection con = ConnectionHelper.getConnection();
		String sql = "SELECT * FROM 185_emp_profile_table where emp_id = ?";
		try {
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setInt(1, empId);
			EmployeeInfoRecord = pStmt.executeQuery();
		} catch (NullPointerException ex) {
			LogRecord.logger.error("Connection Not Created : " + ex.getMessage());
		} catch (SQLException ex) {
			LogRecord.logger.error("Query Didn't executed properly : " + ex.getMessage());
		}
		return;

	}

	@Override
	public void updateEmployeeProfile() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateEmployeeInfo() {
		// TODO Auto-generated method stub

	}

}
