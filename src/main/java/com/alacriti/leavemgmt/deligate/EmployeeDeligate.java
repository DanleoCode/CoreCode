package com.alacriti.leavemgmt.deligate;

import java.sql.Timestamp;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.util.AccountStatusCode;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public class EmployeeDeligate {
	
	public static EmployeeInfo createNewEmployee(EmployeeProfile profile){
		
		Timestamp date = new Timestamp(new java.util.Date().getTime());
		profile.setEmployeeAccountStatus(AccountStatusCode.NOT_APPROVED);
		profile.setCreationTime(date);
		profile.setLastModifiedTime(date);
		profile.setProjectId(1);
		profile.setSecurityQuestionId(940);
		profile.setEmployeeType((short)961);
		profile.setApprover1(19);
		profile.setApprover2(19);
		profile.setApprover3(19);
		
		EmployeeInfo employeeProfile = new EmployeeProfile();
		
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		employeeProfile = Bo.addEmployee(profile);
		return employeeProfile;
	}
}
