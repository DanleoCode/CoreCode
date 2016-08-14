package com.alacriti.leavemgmt.valueobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Statistics {
	private int totalUsers;
	private int newUserRequest;
	private int totlaLeave;
	private int newLeaveRequest;
	public Statistics(int totalUsers, int newUserRequest, int totlaLeave,
			int newLeaveRequest) {
		super();
		this.totalUsers = totalUsers;
		this.newUserRequest = newUserRequest;
		this.totlaLeave = totlaLeave;
		this.newLeaveRequest = newLeaveRequest;
	}
	
	public Statistics() {
	
	}
	
	public int getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}
	public int getNewUserRequest() {
		return newUserRequest;
	}
	public void setNewUserRequest(int newUserRequest) {
		this.newUserRequest = newUserRequest;
	}
	public int getTotlaLeave() {
		return totlaLeave;
	}
	public void setTotlaLeave(int totlaLeave) {
		this.totlaLeave = totlaLeave;
	}
	public int getNewLeaveRequest() {
		return newLeaveRequest;
	}
	public void setNewLeaveRequest(int newLeaveRequest) {
		this.newLeaveRequest = newLeaveRequest;
	}
}
