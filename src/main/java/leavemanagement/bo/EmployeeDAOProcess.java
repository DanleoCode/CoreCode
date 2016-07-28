package leavemanagement.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import leavemanagement.util.LogRecord;
import leavemanagement.valueobject.EmployeeInfo;
import leavemanagement.valueobject.EmployeeProfile;

public class EmployeeDAOProcess implements EmployeeResultProcess {

	@Override
	public EmployeeInfo parseEmployeeInfoResultSet(ResultSet employeeRecord) {
		EmployeeInfo employeeInfo = new EmployeeInfo();
		try {
			if (employeeRecord.next()) {
				employeeInfo.setEmpId(employeeRecord.getInt("emp_id"));
				employeeInfo.setEmpCode(employeeRecord.getString("emp_code"));
				employeeInfo.setFirstName(employeeRecord.getString("first_name"));
				employeeInfo.setLastName(employeeRecord.getString("last_name"));
				employeeInfo.setGender(employeeRecord.getBoolean("gender"));
				employeeInfo.setEmail(employeeRecord.getString("email"));
				employeeInfo.setMobile(employeeRecord.getString("mobile"));
				employeeInfo.setProjectId(employeeRecord.getInt("project_id"));
			}
		} catch (NullPointerException ex) {
			LogRecord.logger.debug("ResultSet is Null for id provided : " + ex.getMessage());
		} catch (SQLException ex) {
			LogRecord.logger.debug("Error While Parsing the data from resultset: " + ex.getMessage());
		}
		return employeeInfo;

	}

	@Override
	public EmployeeProfile parseEmployeeProfileResultSet(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<EmployeeInfo> parseResultSetToList(ResultSet rs) {
		
		return null;
	}

	

}
