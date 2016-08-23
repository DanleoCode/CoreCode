package com.alacriti.leavemgmt.deligate;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.bo.LeaveBOImplement;
import com.alacriti.leavemgmt.resource.EmployeeResource;
import com.alacriti.leavemgmt.util.AccountStatusCode;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.MasterTable;
import com.alacriti.leavemgmt.valueobject.Statistics;
import com.alacriti.leavemgmt.valueobject.Tables;

public class EmployeeDeligate {
	public static Logger logger = Logger.getLogger(EmployeeResource.class);

	public static EmployeeInfo createNewEmployee(EmployeeProfile profile) {

		Timestamp date = new Timestamp(new java.util.Date().getTime());
		profile.setEmployeeAccountStatus(AccountStatusCode.NOT_APPROVED);
		profile.setCreationTime(date);
		profile.setLastModifiedTime(date);
		profile.setProjectId(1);
		profile.setSecurityQuestionId(940);
		profile.setEmployeeType((short) 961);
		profile.setApprover1(19);
		profile.setApprover2(19);
		profile.setApprover3(19);

		EmployeeInfo employeeProfile = new EmployeeProfile();

		EmployeeBOImplement Bo = new EmployeeBOImplement();
		employeeProfile = Bo.addEmployee(profile);
		return employeeProfile;
	}

	public static List<EmployeeProfile> getProfiles(int offset, int limit) {
		EmployeeBOImplement employeeBOImplement = new EmployeeBOImplement();
		return employeeBOImplement.getProfiles(offset, limit);
	}

	public static Employee updateEmployee(int empId, Employee employee) {
		logger.info("In updateEmployee DEligate");
		Timestamp date = new Timestamp(new java.util.Date().getTime());
		EmployeeBOImplement boImplement = new EmployeeBOImplement();
		employee.getEmployeeProfile().setLastModifiedTime(date);
		boImplement.updateEmployeeInfo(employee.getEmployeeProfile());
		boImplement = new EmployeeBOImplement();
		EmployeeProfile profile = employee.getEmployeeProfile();
		if (profile != null) {
			boImplement = new EmployeeBOImplement();
			boImplement.updateEmployeeProfile(profile);
		}
		int year = Calendar.getInstance().get(Calendar.YEAR);
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		if (leaveBOImplement.getLeaveBalance(employee.getEmployeeProfile()
				.getEmpId(), year) == null) {
			if (employee.getLeaveBalance() != null) {
				leaveBOImplement = new LeaveBOImplement();
				employee.getLeaveBalance().setFinancialyear(year);
				leaveBOImplement.newLeaveBalance(employee.getLeaveBalance());
			}
		} else {
			leaveBOImplement = new LeaveBOImplement();
			employee.getLeaveBalance().setFinancialyear(year);
			leaveBOImplement.updateLeavebalance(employee.getLeaveBalance());
		}
		return employee;
	}

	public EmployeeProfile updatePassword(Employee employee) {
		if (employee != null) {
			EmployeeBOImplement boImplement = new EmployeeBOImplement();
			int result = boImplement.updatePassword(employee);
			if (result == 1) {
				employee.getEmployeeProfile().setLoginId("updated successfull");
				logger.info("updated successfull");
			} else if (result == 0) {
				employee.getEmployeeProfile().setLoginId("Not updated");
				logger.info("not updated");
			} else {
				employee.getEmployeeProfile().setLoginId("Wrong credential");
				logger.info("password not matched");
			}
		}
		return employee.getEmployeeProfile();
	}

	public static List<EmployeeProfile> searchProfile(String query) {
		EmployeeBOImplement employeeBOImplement = new EmployeeBOImplement();
		return employeeBOImplement.searchProfile(query);
	}

	public static Statistics getStatistics() {
		Statistics statistics = null;
		EmployeeBOImplement boImplement = new EmployeeBOImplement();
		try {
			statistics = boImplement.getEmpStatistics();
		} catch (NullPointerException ex) {
			logger.info("Connection Not Found");
		} catch (SQLException ex) {
			logger.error("SQLException " + ex.getMessage());
		}
		return statistics;
	}

	public static int updateSeurityQuestion(Employee employee) {
		int updatedRows = -1;
		int empId = employee.getEmployeeProfile().getEmpId();
		int questionId = employee.getEmployeeProfile().getSecurityQuestionId();
		String answer = employee.getEmployeeProfile().getSecurityAnswer();
		EmployeeBOImplement boImplement = new EmployeeBOImplement();
		updatedRows = boImplement.updateSecurityQuestion(empId, questionId, answer);
		return updatedRows;
	}

	public static List<MasterTable> getAllSecurityQuestion() {
		EmployeeBOImplement boImplement = new EmployeeBOImplement();
		return boImplement.getMasterTableAllRecord(Tables.SECURITY_QUESTION_MASTER);
	}
}
