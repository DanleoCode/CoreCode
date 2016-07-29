package com.alacriti.leavemgmt.dao;

import java.sql.ResultSet;

import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public interface EmployeeDAO {
	
	public int addEmployeeInfo(EmployeeProfile employeeProfile);
	
	public int addEmployeeProfile(EmployeeProfile employeeProfile);
	
	public ResultSet selectEmployeeInfo(int empId);
	
	public ResultSet selectEmployeeProfile(int empId);
	
	public void updateEmployeeProfile();
	
	public int updateEmployeeInfo(EmployeeInfo employeeInfo);
}
