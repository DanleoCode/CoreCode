package com.alacriti.leavemgmt.valueobject;

public class Employee {
	private EmployeeInfo employeeInfo;
	private EmployeeProfile employeeProfile;
	private LeaveBalance leaveBalance;
	private Leave leave;
	private LeaveHistory leaveHistory;
	private LoginCredential loginCredential;
	
	public Employee(){
		
	}

	public Employee(EmployeeInfo employeeInfo, EmployeeProfile employeeProfile,
			LeaveBalance leaveBalance, Leave leave, LeaveHistory leaveHistory, LoginCredential credential) {
		super();
		this.employeeInfo = employeeInfo;
		this.employeeProfile = employeeProfile;
		this.leaveBalance = leaveBalance;
		this.leave = leave;
		this.leaveHistory = leaveHistory;
		this.setLoginCredential(credential);
	}

	public EmployeeInfo getEmployeeInfo() {
		return employeeInfo;
	}

	public void setEmployeeInfo(EmployeeInfo employeeInfo) {
		this.employeeInfo = employeeInfo;
	}

	public EmployeeProfile getEmployeeProfile() {
		return employeeProfile;
	}

	public void setEmployeeProfile(EmployeeProfile employeeProfile) {
		this.employeeProfile = employeeProfile;
	}

	public LeaveBalance getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(LeaveBalance leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public LeaveHistory getLeaveHistory() {
		return leaveHistory;
	}

	public void setLeaveHistory(LeaveHistory leaveHistory) {
		this.leaveHistory = leaveHistory;
	}

	public LoginCredential getLoginCredential() {
		return loginCredential;
	}

	public void setLoginCredential(LoginCredential loginCredential) {
		this.loginCredential = loginCredential;
	}
	
	
}
