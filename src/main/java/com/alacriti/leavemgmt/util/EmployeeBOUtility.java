package com.alacriti.leavemgmt.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.MasterTable;

public class EmployeeBOUtility  {

	public static Logger logger = Logger.getLogger(EmployeeBOUtility.class);
	
	public static List<EmployeeInfo> getSelectEmployeeInfoData(ResultSet employeeInfoRecord) {
		List<EmployeeInfo> list = new ArrayList<EmployeeInfo>();
		EmployeeInfo employeeInfo = null;
		try {
			while(employeeInfoRecord.next()) {
				employeeInfo = new EmployeeInfo();
				employeeInfo.setEmpId(employeeInfoRecord.getInt("emp_id"));
				employeeInfo.setEmpCode(employeeInfoRecord.getString("emp_code"));
				employeeInfo.setFirstName(employeeInfoRecord.getString("first_name"));
				employeeInfo.setLastName(employeeInfoRecord.getString("last_name"));
				employeeInfo.setGender(employeeInfoRecord.getBoolean("gender"));
				employeeInfo.setEmail(employeeInfoRecord.getString("email"));
				employeeInfo.setMobile(employeeInfoRecord.getString("mobile"));
				employeeInfo.setProjectId(employeeInfoRecord.getInt("project_id"));
				list.add(employeeInfo);
			}
		} catch (NullPointerException ex) {
			LogRecord.logger.debug("EmployeeInfoRecord ResultSet is Null for id provided : " + ex.getMessage());
		} catch (SQLException ex) {
			LogRecord.logger.debug("Error While Parsing the data from EmployeeInfoRecord resultset: " + ex.getMessage());
		} catch(Exception ex){
			logger.error("Uncaught : " + ex.getMessage());
		}
		return list;

	}

	public static EmployeeProfile getSelectEmployeeProfileData(ResultSet employeeProfileRecord) {
		EmployeeProfile employeeProfile = null;
		try{
			if(employeeProfileRecord.next()){
				employeeProfile = new EmployeeProfile();
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
			logger.debug("EmployeeProfileRecord ResultSet is Null for id provided : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.debug("Error While Parsing the data from EmployeeProfileRecord resultset: " + ex.getMessage());
		}
		return employeeProfile;
	}
	
	public static List<EmployeeProfile> getEmployeeDetail(ResultSet employeeProfileRecord) {
		List<EmployeeProfile> list = new ArrayList<EmployeeProfile>();
		try{
			while(employeeProfileRecord.next()){
				EmployeeProfile employeeProfile = new EmployeeProfile();
				employeeProfile.setEmpId(employeeProfileRecord.getInt("emp_id"));
				employeeProfile.setFirstName(employeeProfileRecord.getString("first_name"));
				employeeProfile.setLastName(employeeProfileRecord.getString("last_Name"));
				employeeProfile.setEmail(employeeProfileRecord.getString("email"));
				employeeProfile.setMobile(employeeProfileRecord.getString("mobile"));
				employeeProfile.setEmpCode(employeeProfileRecord.getString("emp_code"));
				employeeProfile.setGender(employeeProfileRecord.getBoolean("gender"));
				employeeProfile.setProjectId(employeeProfileRecord.getInt("project_id"));
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
				list.add(employeeProfile);
			}
		} catch (NullPointerException ex) {
			logger.debug("EmployeeProfileRecord ResultSet is Null for id provided : " + ex.getMessage());
		} catch (SQLException ex) {
			logger.debug("Error While Parsing the data from EmployeeProfileRecord resultset: " + ex.getMessage());
		} catch(Exception ex){
			logger.info("Uncaught Exception : " + ex.getMessage());
		}
		return list;
	}
	
	public static List<MasterTable> getMasterTableData(ResultSet rs) 
		throws NullPointerException, SQLException, Exception{
		List<MasterTable> list = new ArrayList<MasterTable>();
		MasterTable masterTable = null;
		while(rs.next()){
			masterTable = new MasterTable();
			Integer key = rs.getInt(1);
			masterTable.setKey(key.toString());
			masterTable.setValue(rs.getString(2));
			list.add(masterTable);
		}
		rs.close();
		return list;
	}


	

}
