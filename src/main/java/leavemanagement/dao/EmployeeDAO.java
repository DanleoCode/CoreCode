package leavemanagement.dao;

import java.sql.ResultSet;

public interface EmployeeDAO {
	public void addEmployeeInfo();
	public void addEmployeeProfile();
	public ResultSet selectEmployeeInfo(int empId);
	public void selectEmployeeProfile(int empId);
	public void updateEmployeeProfile();
	public void updateEmployeeInfo();
}
