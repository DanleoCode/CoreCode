package com.alacriti.leavemgmt.bo;

import java.sql.Connection;
import java.sql.SQLException;

import com.alacriti.leavemgmt.dao.EmployeeService;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public class EmployeeBOImplement {
	private Connection con;

	public EmployeeBOImplement() {
		con = ConnectionHelper.getConnection();
	}

	public EmployeeInfo addEmployee(EmployeeProfile employeeProfile) {
		EmployeeService employeeService = new EmployeeService(con);
		int generatedEmpId = 0;
		generatedEmpId = employeeService.addEmployeeInfo(employeeProfile);
		employeeProfile.setEmpId(generatedEmpId);
		if (generatedEmpId > 0) {
			int updatedRow = employeeService.addEmployeeProfile(employeeProfile);
			if (updatedRow == 1) {
				employeeProfile.setEmpId(generatedEmpId);
			}
		}
		else{
			//rollback
		}
		try {
			this.con.commit();
			ConnectionHelper.finalizeConnection(con);
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return employeeProfile;
	}
	
	public EmployeeInfo getEmployeeInfo(int empId){
		EmployeeService empSrv = new EmployeeService(con);
		EmployeeDAOProcess empDAOProcess = new EmployeeDAOProcess();
		EmployeeInfo employeeInfo = empDAOProcess.getSelectEmployeeInfoData(empSrv.selectEmployeeInfo(empId));
		ConnectionHelper.finalizeConnection(con);
		return employeeInfo;
	}
	
	public EmployeeProfile getEmployeeProfile(int empId){
		EmployeeService empSrv = new EmployeeService(con);
		EmployeeDAOProcess empDAOProcess = new EmployeeDAOProcess();
		EmployeeProfile employeeProfile = empDAOProcess.getSelectEmployeeProfileData(empSrv.selectEmployeeProfile(empId));
		ConnectionHelper.finalizeConnection(con);
		return employeeProfile;
	}
	
	public EmployeeProfile updateEmployeeInfo(EmployeeProfile employeeProfile){
		EmployeeService empSrv = new EmployeeService(con);
		int updatedRows = empSrv.updateEmployeeInfo(employeeProfile);
		System.out.println(updatedRows);
		try {
			con.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		ConnectionHelper.finalizeConnection(con);
		return employeeProfile;
	}
	
	public EmployeeProfile AutherizedAccess(String loginId, String passwd){
		EmployeeService employeeService  = new EmployeeService(con);
		EmployeeProfile employeeProfile = new EmployeeProfile();
		int empId = employeeService.authLogin(loginId, passwd);
		if(empId > 0){
			employeeProfile = employeeService.employeeDetail(empId);
		}
		else{
			
		}
		return employeeProfile;
	}
}
