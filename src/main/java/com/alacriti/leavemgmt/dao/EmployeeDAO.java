package com.alacriti.leavemgmt.dao;

import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public interface EmployeeDAO {
	
	public int addEmployeeInfo(EmployeeProfile employeeProfile);
	
	public int addEmployeeProfile(EmployeeProfile employeeProfile);
	
	public EmployeeInfo selectEmployeeInfo(int empId);
	
	public EmployeeProfile selectEmployeeProfile(int empId);
	
	public void updateEmployeeProfile();
	
	public int updateEmployeeInfo(EmployeeInfo employeeInfo);
}
