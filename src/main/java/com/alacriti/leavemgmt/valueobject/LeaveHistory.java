package com.alacriti.leavemgmt.valueobject;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LeaveHistory {
	private long leaveId;
	private short leaveStatusCode;
	private Timestamp creationTime;
	private Timestamp lastModified;
	private EmployeeProfile employeeProfile;

	public LeaveHistory() {

	}

	public LeaveHistory(long leaveId, short leaveStatusCode,
			Timestamp creationTime, Timestamp lastModified,
			EmployeeProfile employeeProfile, short leaveType) {
		super();
		this.leaveId = leaveId;
		this.leaveStatusCode = leaveStatusCode;
		this.creationTime = creationTime;
		this.lastModified = lastModified;
		this.employeeProfile = employeeProfile;
	}

	public long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(long leaveId) {
		this.leaveId = leaveId;
	}

	public short getLeaveStatusCode() {
		return leaveStatusCode;
	}

	public void setLeaveStatusCode(short leaveStatusCode) {
		this.leaveStatusCode = leaveStatusCode;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

	public EmployeeProfile getEmployeeProfile() {
		return employeeProfile;
	}

	public void setEmployeeProfile(EmployeeProfile employeeProfile) {
		this.employeeProfile = employeeProfile;
	}

}
