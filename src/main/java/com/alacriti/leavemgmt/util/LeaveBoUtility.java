package com.alacriti.leavemgmt.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.deligate.LeaveDeligate;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveHistory;

public class LeaveBoUtility {
	public static Logger logger = Logger.getLogger(LeaveDeligate.class);
	
	public static List<Employee> getLeaveApprovalList(ResultSet rs) {
		List<Employee> list = new ArrayList<Employee>();
		try{
			while (rs.next()) {
				Employee employee = new Employee();
				EmployeeProfile employeeProfile = new EmployeeProfile();
				LeaveHistory leaveHistory = new LeaveHistory();
				
				//leaveHistory.setEmployeeProfile(employeeProfile);
				int empId = rs.getInt("emp_id");
				employeeProfile.setEmpId(empId);;
				
				leaveHistory.setLeaveId(rs.getLong("leave_id"));
				leaveHistory.setLeaveStatusCode(rs.getShort("leave_status_code"));
				
				
				//leaveHistory.setEmployeeProfile(employeeProfile);
				Timestamp timeStamp = rs.getTimestamp("creation_time");
				leaveHistory.setCreationTime(timeStamp);
				
				employee.setLeaveHistory(leaveHistory);
				employee.setEmployeeProfile(employeeProfile);
				
				logger.info("here is my date" + Timestamp.valueOf(timeStamp.toString()));
				list.add(employee);
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
				employeeLeaveHistory.setLeaveId(empLeaveResultSet.getLong("leave_id"));
				employeeLeaveHistory.setLeaveStatusCode(empLeaveResultSet.getShort("leave_status_code"));
				employeeLeaveHistory.setLeaveTypeId(empLeaveResultSet.getShort("leave_type_id"));
				employeeLeaveHistory.setStartdate(empLeaveResultSet.getDate("start_date"));
				employeeLeaveHistory.setEndDate(empLeaveResultSet.getDate("end_date"));
				employeeLeaveHistory.setNoOfDays(empLeaveResultSet.getShort("No_of_days"));
				employeeLeaveHistory.setMessage(empLeaveResultSet.getString("message"));
				employeeLeaveHistory.setTag(empLeaveResultSet.getString("tag"));
				employeeLeaveHistory.setApplicationDate(empLeaveResultSet.getDate("creation_time"));
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
	
	public static Leave getLeaveInstanceData(ResultSet rs){
		Leave leave = new Leave();
		try{
			if(rs.next()){
				leave.setEmpId(rs.getInt("emp_id"));
				leave.setEndDate(rs.getDate("end_date"));
				leave.setLeaveTypeId(rs.getShort("leave_type_id"));
				leave.setMessage(rs.getString("message"));
				leave.setNoOfDays(rs.getShort("no_of_days"));
				leave.setProjectId(rs.getInt("project_id"));
				leave.setStartdate(rs.getDate("start_date"));
				leave.setTag(rs.getString("tag"));
			}
		} catch(NullPointerException ex){
			logger.info("Resultset empty : " + ex.getMessage());
		} catch(SQLException ex){
			logger.error("SQLException : "+ ex.getMessage());
		}
		return leave;
	}
	
	public static LeaveHistory getLeaveHistoryById(ResultSet rs){
		LeaveHistory history = new LeaveHistory();
		try{
			if(rs.next()){
				history.setLeaveStatusCode(rs.getShort("leave_status_code"));
			}
			return history;
		} catch(NullPointerException ex){
			logger.info("Resultset empty : " + ex.getMessage());
		} catch(SQLException ex){
			logger.error("SQLException : "+ ex.getMessage());
		}
		return history;
	}
}
