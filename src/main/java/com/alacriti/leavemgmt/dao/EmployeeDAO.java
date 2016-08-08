package com.alacriti.leavemgmt.dao;

import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public interface EmployeeDAO {
	
	public int addEmployeeInfo(EmployeeProfile employeeProfile);
	
	public int addEmployeeProfile(EmployeeProfile employeeProfile);
	
	public EmployeeInfo selectEmployeeInfo(int empId);
	
	public EmployeeProfile selectEmployeeProfile(int empId);
	
	public int updateEmployeeProfile(EmployeeProfile profile);
	
	public int updateEmployeeInfo(EmployeeInfo employeeInfo);
}
