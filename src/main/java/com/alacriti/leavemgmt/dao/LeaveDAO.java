package com.alacriti.leavemgmt.dao;

import java.util.List;

import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveHistory;

public interface LeaveDAO {
	
	/*
	 * create a new entry in leave_instance_table
	 * with Leave object and returns generated leaveId on long type
	 */
	public long AddLeaveInstance(Leave leave);
	
	/*
	 * create a new Entry in emp_leave_instance_table and
	 * and returns number of updated rows 
	 */
	public int addNewEmployeeLeaveInstance(LeaveHistory leaveHistory);
	
	/*
	 * update employee_leave_instance_table for LeaveHistory Object
	 * and returns the number of affected rows 
	 */
	public int updateLeaveStatus(LeaveHistory leaveInstance);
	
	/*
	 * returns list of leave where empId, status and approverLevel
	 * each leavHistory Object is contained in Employee Class 
	 */
	public List<Employee> getPendingLeaveApproval(int empId,
			short leaveStatusCode, String approverLevel);
	
	/*
	 * Get all Records from emp_leave_instance_table
	 * and return list of Employee class
	 */
	public List<Employee> getAllLeavesHistory();
	
	/*
	 * A full history of leaves performs join operation on tables 
	 * and return list of EmployeeLeaveHistory
	 */
	public List<EmployeeLeaveHistory> getAllLeaves(int empId);
	
	/*
	 * Updates the employee leave Balance
	 * and return the number of affected rows in table
	 */
	public int updateLeaveBalance(LeaveBalance leaveBalance);
	
	/*
	 * get the leave balance of a employee for `year`
	 */
	public LeaveBalance getLeaveBalance(int empId, int year);
	
	/*
	 * return a Leave Object for `leaveId`
	 */
	public Leave getLeaveById(long leaveId);
	
	/*
	 * returns the leaveHistory for `leaveId`
	 */
	public LeaveHistory getLeaveHistoryById(long leaveId);
}
