package com.alacriti.leavemgmt.valueobject;

import java.sql.Date;

public class Leave {
	private int empId;
	private short leaveTypeId;
	private Date startdate;
	private Date endDate;
	private short noOfDays;
	private int projectId;
	private String message;
	private String tag; 
	
	public Leave(){
		
	}

	public Leave(int empId, short leaveTypeId, Date startdate, Date endDate,
			short noOfDays, int projectId,
			String message, String tag) {
		super();
		this.empId = empId;
		this.leaveTypeId = leaveTypeId;
		this.startdate = startdate;
		this.endDate = endDate;
		this.noOfDays = noOfDays;
		this.projectId = projectId;
		this.message = message;
		this.tag = tag;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public short getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(short leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public short getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(short noOfDays) {
		this.noOfDays = noOfDays;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
}
