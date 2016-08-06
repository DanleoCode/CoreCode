package com.alacriti.leavemgmt.deligate;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.resource.EmployeeResource;
import com.alacriti.leavemgmt.util.AccountStatusCode;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public class EmployeeDeligate {
	public static Logger logger = Logger.getLogger(EmployeeResource.class);
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
	
	public static List<EmployeeProfile> getProfiles(){
		logger.info("in EmployeeDeligate");
		EmployeeBOImplement employeeBOImplement = new EmployeeBOImplement();
		return employeeBOImplement.getProfiles();
	}
}
