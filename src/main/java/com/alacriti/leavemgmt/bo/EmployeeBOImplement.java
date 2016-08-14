package com.alacriti.leavemgmt.bo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.dao.EmployeeDAOImplement;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
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
		logger.info("Calling addEmployeeInfo of emp dao");
		generatedEmpId = employeeService.addEmployeeInfo(employeeProfile);
		logger.info("Generated ID is : " + generatedEmpId);
		employeeProfile.setEmpId(generatedEmpId);
		if (generatedEmpId > 0) {
			int updatedRow = employeeService.addEmployeeProfile(employeeProfile);
			logger.info("number of affacted rows : " + updatedRow);
			if (updatedRow == 1) {
				employeeProfile.setEmpId(generatedEmpId);
				logger.info("committing connection");
				ConnectionHelper.commitConnection(con);
			}else{
				employeeProfile.setEmpId(0);
				ConnectionHelper.rollbackConnection(con);
				logger.info("connectrion rolled back");
			}
		}
		
		ConnectionHelper.finalizeConnection(con);
		logger.info("employeeBOIMplment finished");
		return employeeProfile;
	}
	
	public EmployeeInfo getEmployeeInfo(int empId){
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		EmployeeInfo employeeInfo = employeeDAOImplement.selectEmployeeInfo(empId);
		ConnectionHelper.finalizeConnection(con);
		return employeeInfo;
	}
	
	public EmployeeProfile getEmployeeProfile(int empId){
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		EmployeeProfile employeeProfile = employeeDAOImplement.selectEmployeeProfile(empId);
		ConnectionHelper.finalizeConnection(con);
		return employeeProfile;
	}
	
	public EmployeeProfile updateEmployeeInfo(EmployeeProfile employeeProfile){
		EmployeeDAOImplement empSrv = new EmployeeDAOImplement(con);
		int updatedRows = empSrv.updateEmployeeInfo(employeeProfile);
		logger.info("Updated Rows : " + updatedRows);
		ConnectionHelper.commitConnection(con);
		ConnectionHelper.finalizeConnection(con);
		return employeeProfile;
	}
	
	public List<EmployeeProfile> AutherizedAccess(String loginId, String passwd){
		EmployeeDAOImplement employeeService  = new EmployeeDAOImplement(con);
		List<EmployeeProfile> list = new ArrayList<EmployeeProfile>();
		int empId = employeeService.authLogin(loginId, passwd);
		logger.info("Emp id is : "  + empId);
		if(empId > 0){
			logger.info("id is greater then 0");
			list = employeeService.employeeDetail(empId,"emp_id");
		}
		else{
			logger.info("will not create session");
		}
		ConnectionHelper.finalizeConnection(con);
		return list;
	}
	
	public List<EmployeeProfile> getProfiles(int offset, int limit){
		logger.info("in BOIMPLEMENT");
		EmployeeDAOImplement employeeDAOImplement  = new EmployeeDAOImplement(con);
		return employeeDAOImplement.getProfiles(offset, limit);
	}
	
	public int createEmployeeBalanceRecord(EmployeeProfile profile){
		int createdRecords = -1;
		EmployeeDAOImplement employeeDAOImplement  = new EmployeeDAOImplement(con);
		createdRecords = employeeDAOImplement.createEmployeeBalanceRecord(profile.getEmpId());
		return createdRecords;
	}
	
	public int updateEmployeeProfile(EmployeeProfile profile){
		int updatedRows = -1;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		updatedRows = employeeDAOImplement.updateEmployeeProfile(profile);
		ConnectionHelper.commitConnection(con);
		ConnectionHelper.finalizeConnection(con);
		return updatedRows;
	}
	
	public int updateEmployeeProfileAttribute(int empId, String atr, String value){
		int updatedRows = -1;
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		updatedRows = employeeDAOImplement.updateEmployeeProfileAttribute(empId, atr, value);
		return updatedRows;
	}
	
	public int updatePassword(Employee employee){
		int updatedrows = -1;
		String loginId = employee.getLoginCredential().getUser();
		String password = employee.getLoginCredential().getPass();
		String newPassword = employee.getEmployeeProfile().getLoginId();
		int empId = employee.getEmployeeProfile().getEmpId();
		
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		if(employeeDAOImplement.authLogin(loginId, password) >= 1){
			if(employeeDAOImplement.updateEmployeeProfileAttribute(empId, "passwd", newPassword) == 1){
				ConnectionHelper.commitConnection(con);
				updatedrows = 1;
			} else
				updatedrows = 0;
		}
		ConnectionHelper.finalizeConnection(con);
		return updatedrows;
	}
	
	public List<EmployeeProfile> searchProfile(String query){
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		
		List<EmployeeProfile> profileList = new ArrayList<EmployeeProfile>();
		HashSet<EmployeeInfo> empInfoSet = new HashSet<EmployeeInfo>();
		List<EmployeeInfo> employeeInfoList = new ArrayList<EmployeeInfo>();
		
		employeeInfoList = employeeDAOImplement.searchInfo("last_name", query);
		employeeInfoList.addAll(employeeDAOImplement.searchInfo("emp_id", query));
		employeeInfoList.addAll(employeeDAOImplement.searchInfo("email", query));
		
		Iterator<EmployeeInfo> ListIterator = employeeInfoList.iterator();
		while(ListIterator.hasNext())
			empInfoSet.add(ListIterator.next());
		
		
		Iterator<EmployeeInfo> iterator = empInfoSet.iterator();
		while(iterator.hasNext()){
			EmployeeInfo empInfo = iterator.next();	
			profileList.addAll(employeeDAOImplement.employeeDetail(empInfo.getEmpId(), "emp_Id"));
		}
		ConnectionHelper.finalizeConnection(con);
		return profileList;
	}
	
	public Statistics getEmpStatistics(){
		Statistics statistics = new Statistics();
		EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
		statistics.setTotalUsers(employeeDAOImplement.getTotalEmployeeCount());
		statistics.setNewUserRequest(employeeDAOImplement.getTotalNewUserCount());
		ConnectionHelper.finalizeConnection(con);
		return statistics;
	}
}
