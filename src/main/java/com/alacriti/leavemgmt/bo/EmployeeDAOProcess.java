package com.alacriti.leavemgmt.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.util.LogRecord;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public class EmployeeDAOProcess implements EmployeeResultProcess {

	public static Logger logger = Logger.getLogger(EmployeeDAOProcess.class);
	@Override
	public EmployeeInfo getSelectEmployeeInfoData(ResultSet employeeInfoRecord) {
		EmployeeInfo employeeInfo = new EmployeeInfo();
		try {
			if (employeeInfoRecord.next()) {
				employeeInfo.setEmpId(employeeInfoRecord.getInt("emp_id"));
				employeeInfo.setEmpCode(employeeInfoRecord.getString("emp_code"));
				employeeInfo.setFirstName(employeeInfoRecord.getString("first_name"));
				employeeInfo.setLastName(employeeInfoRecord.getString("last_name"));
				employeeInfo.setGender(employeeInfoRecord.getBoolean("gender"));
				employeeInfo.setEmail(employeeInfoRecord.getString("email"));
				employeeInfo.setMobile(employeeInfoRecord.getString("mobile"));
				employeeInfo.setProjectId(employeeInfoRecord.getInt("project_id"));
			}
		} catch (NullPointerException ex) {
			LogRecord.logger.debug("EmployeeInfoRecord ResultSet is Null for id provided : " + ex.getMessage());
		} catch (SQLException ex) {
			LogRecord.logger.debug("Error While Parsing the data from EmployeeInfoRecord resultset: " + ex.getMessage());
		} catch(Exception ex){
			logger.error("Uncaught : " + ex.getMessage());
		}
		return employeeInfo;

	}

	@Override
	public EmployeeProfile getSelectEmployeeProfileData(ResultSet employeeProfileRecord) {
		EmployeeProfile employeeProfile = new EmployeeProfile();
		try{
			if(employeeProfileRecord.next()){
				employeeProfile.setEmpId(employeeProfileRecord.getInt("emp_id"));
				employeeProfile.setLoginId(employeeProfileRecord.getString("login_id"));
				employeeProfile.setPassword(employeeProfileRecord.getString("passwd"));
				employeeProfile.setSecurityQuestionId(employeeProfileRecord.getInt("security_question_id"));
				employeeProfile.setSecurityAnswer(employeeProfileRecord.getString("security_answer"));
				employeeProfile.setLastModifiedTime(employeeProfileRecord.getTimestamp("last_modified"));
				employeeProfile.setCreationTime(employeeProfileRecord.getTimestamp("creation_time"));
				employeeProfile.setEmployeeAccountStatus(employeeProfileRecord.getShort("emp_account_status"));
				employeeProfile.setEmployeeType(employeeProfileRecord.getShort("emp_type"));
				employeeProfile.setApprover1(employeeProfileRecord.getInt("approver1_id"));
				employeeProfile.setApprover2(employeeProfileRecord.getInt("approver2_id"));
				employeeProfile.setApprover3(employeeProfileRecord.getInt("approver3_id"));
			}
		} catch (NullPointerException ex) {
			LogRecord.logger.debug("EmployeeProfileRecord ResultSet is Null for id provided : " + ex.getMessage());
		} catch (SQLException ex) {
			LogRecord.logger.debug("Error While Parsing the data from EmployeeProfileRecord resultset: " + ex.getMessage());
		}
		return employeeProfile;
	}
	@Override
	public List<EmployeeInfo> parseResultSetToList(ResultSet rs) {
		
		return null;
	}



	

}
