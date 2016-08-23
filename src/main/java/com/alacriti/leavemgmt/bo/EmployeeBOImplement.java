package com.alacriti.leavemgmt.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.dao.EmployeeDAOImplement;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.MasterTable;
import com.alacriti.leavemgmt.valueobject.Statistics;

public class EmployeeBOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(EmployeeBOImplement.class);

	public EmployeeBOImplement() {
		con = ConnectionHelper.getConnection();
	}

	public EmployeeInfo addEmployee(EmployeeProfile employeeProfile) {
		EmployeeDAOImplement employeeService = new EmployeeDAOImplement(con);
		int generatedEmpId = 0;
		int updatedRow  = -1;
		logger.info("Calling addEmployeeInfo of emp dao");
		try {
			generatedEmpId = employeeService.addEmployeeInfo(employeeProfile);
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		logger.info("Generated ID is : " + generatedEmpId);
		employeeProfile.setEmpId(generatedEmpId);
		if (generatedEmpId > 0) {
			try {
				updatedRow = employeeService.addEmployeeProfile(employeeProfile);
			} catch (Exception e) {
				logger.error("Exception Occured : " + e.getMessage());
			}
			logger.info("number of affacted rows : " + updatedRow);
			if (updatedRow == 1) {
				employeeProfile.setEmpId(generatedEmpId);
				logger.info("committing connection");
				ConnectionHelper.commitConnection(con);
			} else {
				employeeProfile.setEmpId(0);
				ConnectionHelper.rollbackConnection(con);
				logger.info("connectrion rolled back");
			}
		}

		ConnectionHelper.finalizeConnection(con);
		logger.info("employeeBOIMplment finished");
		return employeeProfile;
	}

	public EmployeeInfo getEmployeeInfo(int empId) {
		EmployeeInfo employeeInfo = null;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			employeeInfo = employeeDAOImplement.selectEmployeeInfo(empId);
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		ConnectionHelper.finalizeConnection(con);
		return employeeInfo;
	}

	public EmployeeProfile getEmployeeProfile(int empId) {
		EmployeeProfile employeeProfile = null;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			employeeProfile = employeeDAOImplement.selectEmployeeProfile(empId);
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		ConnectionHelper.finalizeConnection(con);
		return employeeProfile;
	}

	public EmployeeProfile updateEmployeeInfo(EmployeeProfile employeeProfile) {
		EmployeeDAOImplement empSrv = new EmployeeDAOImplement(con);
		int updatedRows = -1;
		try {
			updatedRows = empSrv.updateEmployeeInfo(employeeProfile);
			logger.info("Updated Rows : " + updatedRows);
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		
		ConnectionHelper.commitConnection(con);
		ConnectionHelper.finalizeConnection(con);
		return employeeProfile;
	}

	public List<EmployeeProfile> AutherizedAccess(String loginId, String passwd) {
		EmployeeDAOImplement employeeService = new EmployeeDAOImplement(con);
		List<EmployeeProfile> list = new ArrayList<EmployeeProfile>();
		int empId = -1;
		try {
			empId = employeeService.authLogin(loginId, passwd);
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		logger.info("Emp id is : " + empId);
		if (empId > 0) {
			logger.info("id is greater then 0");
			try {
				list = employeeService.employeeDetail(empId, "emp_id");
			} catch (Exception e) {
				logger.error("Exception Occured : " + e.getMessage());
			}
		} else {
			logger.info("will not create session");
		}
		ConnectionHelper.finalizeConnection(con);
		return list;
	}

	public List<EmployeeProfile> getProfiles(int offset, int limit) {
		logger.info("in BOIMPLEMENT");
		List<EmployeeProfile> list = new ArrayList<EmployeeProfile>();
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			list = employeeDAOImplement.getProfiles(offset, limit);
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		return list;
	}

	public int createEmployeeBalanceRecord(EmployeeProfile profile) {
		int createdRecords = -1;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			createdRecords = employeeDAOImplement.createEmployeeBalanceRecord(profile.getEmpId());
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		return createdRecords;
	}

	public int updateEmployeeProfile(EmployeeProfile profile) {
		int updatedRows = -1;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			updatedRows = employeeDAOImplement.updateEmployeeProfile(profile);
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		}
		ConnectionHelper.commitConnection(con);
		ConnectionHelper.finalizeConnection(con);
		return updatedRows;
	}

	public int updateEmployeeProfileAttribute(int empId, String atr,
			String value) {
		int updatedRows = -1;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			updatedRows = employeeDAOImplement.updateEmployeeProfileAttribute(empId, atr, value);
		} catch (Exception e) {
			logger.error("Exceptoin occured : " + e.getMessage());
		}
		return updatedRows;
	}

	public int updatePassword(Employee employee) {
		int updatedrows = -1;
		String loginId = employee.getLoginCredential().getUser();
		String password = employee.getLoginCredential().getPass();
		String newPassword = employee.getEmployeeProfile().getLoginId();
		int empId = employee.getEmployeeProfile().getEmpId();

		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			if (employeeDAOImplement.authLogin(loginId, password) >= 1) {
				if (employeeDAOImplement.updateEmployeeProfileAttribute(empId,
						"passwd", newPassword) == 1) {
					ConnectionHelper.commitConnection(con);
					updatedrows = 1;
				} else
					updatedrows = 0;
			}
		} catch (Exception e) {
			logger.error("Exceptoin occured : " + e.getMessage());
		}
		ConnectionHelper.finalizeConnection(con);
		return updatedrows;
	}

	public List<EmployeeProfile> searchProfile(String query) {
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(
				con);

		List<EmployeeProfile> profileList = new ArrayList<EmployeeProfile>();
		HashSet<EmployeeInfo> empInfoSet = new HashSet<EmployeeInfo>();
		List<EmployeeInfo> employeeInfoList = new ArrayList<EmployeeInfo>();
		try {
			employeeInfoList = employeeDAOImplement.searchInfo("last_name",
					query);
			employeeInfoList.addAll(employeeDAOImplement.searchInfo(
					"first_name", query));
			employeeInfoList.addAll(employeeDAOImplement.searchInfo("emp_code",
					query));
			employeeInfoList.addAll(employeeDAOImplement.searchInfo("email",
					query));
		} catch (Exception e) {
			logger.error("Exceptoin Occured : " + e);
		}

		Iterator<EmployeeInfo> ListIterator = employeeInfoList.iterator();
		while (ListIterator.hasNext())
			empInfoSet.add(ListIterator.next());

		Iterator<EmployeeInfo> iterator = empInfoSet.iterator();
		while (iterator.hasNext()) {
			EmployeeInfo empInfo = iterator.next();
			try {
				profileList.addAll(employeeDAOImplement.employeeDetail(
						empInfo.getEmpId(), "emp_Id"));
			} catch (Exception e) {
				logger.error("Exception Occured: " + e.getMessage());
			}
		}
		ConnectionHelper.finalizeConnection(con);
		return profileList;
	}

	public Statistics getEmpStatistics() throws NullPointerException,
			SQLException {
		Statistics statistics = new Statistics();
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			statistics.setTotalUsers(employeeDAOImplement.getTotalEmployeeCount());
			statistics.setNewUserRequest(employeeDAOImplement.getTotalNewUserCount());
		} catch (Exception e) {
			logger.error("Exception Occurred : " + e.getMessage());
		}
		ConnectionHelper.finalizeConnection(con);
		return statistics;
	}

	public int updateSecurityQuestion(int empId, int questionId, String answer) {
		int updatedRows = -1;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		try {
			updatedRows = employeeDAOImplement.updateEmployeeProfileNumberAttribute(empId,
							"security_question_id", questionId);
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
		}
		if (updatedRows == 1) {
			try {
				updatedRows = employeeDAOImplement.updateEmployeeProfileAttribute(empId,
								"security_answer", answer);
			} catch (Exception e) {
				logger.error("Exception Occurred : " + e.getMessage());
			}
			if (updatedRows == 1)
				ConnectionHelper.commitConnection(con);
			else
				ConnectionHelper.rollbackConnection(con);
		}
		ConnectionHelper.finalizeConnection(con);
		return updatedRows;
	}

	public List<MasterTable> getMasterTableAllRecord(String tableName) {
		EmployeeDAOImplement daoImplement = new EmployeeDAOImplement(con);
		try {
			return daoImplement.getMaterTableAllRecord(tableName);
		} catch (Exception e) {
			logger.error("Exception Occurred : " + e.getMessage());
		}
		return null;
	}
}
