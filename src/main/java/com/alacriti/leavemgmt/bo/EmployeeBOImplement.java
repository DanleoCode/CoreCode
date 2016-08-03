package com.alacriti.leavemgmt.bo;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.dao.EmployeeService;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public class EmployeeBOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(EmployeeBOImplement.class);
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
			logger.info("number of affacted rows : " + updatedRow);
			if (updatedRow == 1) {
				employeeProfile.setEmpId(generatedEmpId);
				try {
					this.con.commit();
				} catch(NullPointerException ex){
					logger.error("Connection Not Found : " + ex.getMessage());
				} catch (SQLException ex) {
					logger.error("SQLException : " +ex.getMessage());
				}
			}else{
				employeeProfile.setEmpId(0);
				try {
					this.con.rollback();
				} catch (SQLException e) {
					
				}
			}
		}
		
		ConnectionHelper.finalizeConnection(con);
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
		logger.info("Emp id is : "  + empId);
		if(empId > 0){
			employeeProfile = employeeService.employeeDetail(empId);
			logger.info("id is greater then 0");
		}
		else{
			logger.info("will not create session");
		}
		ConnectionHelper.finalizeConnection(con);
		logger.info(employeeProfile);
		return employeeProfile;
	}
}
