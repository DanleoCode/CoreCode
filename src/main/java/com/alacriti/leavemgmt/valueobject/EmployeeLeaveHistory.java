package com.alacriti.leavemgmt.valueobject;

import java.sql.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployeeLeaveHistory extends Leave{
	private short leaveStatusCode;
	private Date applicationDate;
	private long leaveId;
	
	public EmployeeLeaveHistory() {
		super();
	}
	
	public EmployeeLeaveHistory(int empId, short leaveTypeId, Date startdate,
			Date endDate, short noOfDays, int projectId, String message,
			String tag) {
		super(empId, leaveTypeId, startdate, endDate, noOfDays, projectId, message, tag);
	}

	public short getLeaveStatusCode() {
		return leaveStatusCode;
	}

	public void setLeaveStatusCode(short leaveStatusCode) {
		this.leaveStatusCode = leaveStatusCode;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicatonDate) {
		this.applicationDate = applicatonDate;
	}

	public long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(long leaveId) {
		this.leaveId = leaveId;
	}
	
	
}
