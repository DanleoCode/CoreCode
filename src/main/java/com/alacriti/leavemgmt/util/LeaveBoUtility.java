package com.alacriti.leavemgmt.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.deligate.LeaveDeligate;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveInstance;

public class LeaveBoUtility {
	public static Logger logger = Logger.getLogger(LeaveDeligate.class);
	
	public static List<LeaveInstance> getLeaveApprovalList(ResultSet rs) {
		List<LeaveInstance> list = new ArrayList<LeaveInstance>();
		try{
			while (rs.next()) {
				EmployeeProfile employeeProfile = new EmployeeProfile();
				LeaveInstance leaveInstance = new LeaveInstance();
				leaveInstance.setEmployeeProfile(employeeProfile);
				leaveInstance.setLeaveId(rs.getLong("leave_id"));
				
				leaveInstance.getEmployeeProfile().setEmpId(rs.getInt("emp_id"));
				Timestamp timeStamp = rs.getTimestamp("creation_time");
				leaveInstance.setCreationTime(timeStamp);
				logger.info("here is my date" + Timestamp.valueOf(timeStamp.toString()));
				list.add(leaveInstance);
			}
		}catch(NullPointerException ex){
			logger.error("ResultSet Empty : " + ex.getMessage());
		} catch(SQLException ex){
			logger.error("SQLException : " + ex.getMessage());
		} catch(Exception ex){
			logger.error("Something  Unexcepted : " + ex.getMessage());
		}
		return list;
	}
	
	public static List<EmployeeLeaveHistory> getEmployeeLeaveHistory(ResultSet empLeaveResultSet){
		List<EmployeeLeaveHistory> list = new ArrayList<EmployeeLeaveHistory>();
		try{
			while(empLeaveResultSet.next()){
				EmployeeLeaveHistory employeeLeaveHistory = new EmployeeLeaveHistory();
				employeeLeaveHistory.setEmpId(empLeaveResultSet.getInt("emp_id"));
				employeeLeaveHistory.setLeaveStatusCode(empLeaveResultSet.getShort("leave_status_code"));
				employeeLeaveHistory.setStartdate(empLeaveResultSet.getDate("start_date"));
				employeeLeaveHistory.setEndDate(empLeaveResultSet.getDate("end_date"));
				employeeLeaveHistory.setNoOfDays(empLeaveResultSet.getShort("No_of_days"));
				employeeLeaveHistory.setMessage(empLeaveResultSet.getString("message"));
				employeeLeaveHistory.setTag(empLeaveResultSet.getString("tag"));
				list.add(employeeLeaveHistory);
			}
		}catch(NullPointerException ex){
			logger.error("ResultSet Empty " + ex.getMessage());
		}catch(SQLException ex){
			logger.error("SQLException : " + ex.getMessage());
		} catch(Exception ex){
			logger.error("Something  Unexcepted : " + ex.getMessage());
		}
		return list;
	}
	
	public static LeaveBalance getEmployeeBalance(ResultSet employeeLeaveBalance){
		LeaveBalance leaveBalance = new LeaveBalance();
		try{
			if(employeeLeaveBalance.next()){
				leaveBalance.setEmpId(employeeLeaveBalance.getShort("emp_id"));
				leaveBalance.setCasualLeave(employeeLeaveBalance.getShort("cl"));
				leaveBalance.setCompOff(employeeLeaveBalance.getShort("comp_off"));
				leaveBalance.setMetLeave(employeeLeaveBalance.getShort("met_leave"));
				leaveBalance.setPetLeave(employeeLeaveBalance.getShort("pet_leave"));
				leaveBalance.setPrivilegeLeave(employeeLeaveBalance.getShort("pl"));
				leaveBalance.setFinancialyear(employeeLeaveBalance.getInt("fin_year"));
				leaveBalance.setSickLeave(employeeLeaveBalance.getShort("sl"));
			}
		} catch(NullPointerException ex){
			logger.error("Empty EmployeeLeaveBalance ResultSet : " + ex.getMessage());
		} catch(SQLException ex){
			logger.error("SQLException : "+ ex.getMessage());
		}
		return leaveBalance;
	}
	
}
