package com.alacriti.leavemgmt.valueobject;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserSession {
	private int empId;
	private String empLoginIp;
	private int employeeType;
	private String empPrivilegeType;
	private Timestamp sessionCreationTime;
	private String empSessionId;
	private Timestamp lastMOdifiedTime;
	
	public UserSession(){
		
	}

	public UserSession(int empId, String empLoginIp, int employeeType,
			String empPrivilegeType, Timestamp sessionCreationTime,
			String empSessionId, Timestamp lastMOdifiedTime) {
		super();
		this.empId = empId;
		this.empLoginIp = empLoginIp;
		this.employeeType = employeeType;
		this.empPrivilegeType = empPrivilegeType;
		this.sessionCreationTime = sessionCreationTime;
		this.empSessionId = empSessionId;
		this.lastMOdifiedTime = lastMOdifiedTime;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getEmpLoginIp() {
		return empLoginIp;
	}

	public void setEmpLoginIp(String empLoginIp) {
		this.empLoginIp = empLoginIp;
	}

	public int getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(int employeeType) {
		this.employeeType = employeeType;
	}

	public String getEmpPrivilegeType() {
		return empPrivilegeType;
	}

	public void setEmpPrivilegeType(String empPrivilegeType) {
		this.empPrivilegeType = empPrivilegeType;
	}

	public Timestamp getSessionCreationTime() {
		return sessionCreationTime;
	}

	public void setSessionCreationTime(Timestamp sessionCreationTime) {
		this.sessionCreationTime = sessionCreationTime;
	}

	public String getEmpSessionId() {
		return empSessionId;
	}

	public void setEmpSessionId(String empSessionId) {
		this.empSessionId = empSessionId;
	}

	public Timestamp getLastMOdifiedTime() {
		return lastMOdifiedTime;
	}

	public void setLastMOdifiedTime(Timestamp lastMOdifiedTime) {
		this.lastMOdifiedTime = lastMOdifiedTime;
	}
	
	@Override
	public String toString() {
		return "UserSession [empId=" + empId + ", employeeType=" + employeeType
				+ ", sessionCreationTime=" + sessionCreationTime
				+ ", empSessionId=" + empSessionId + ", lastMOdifiedTime="
				+ lastMOdifiedTime + "]";
	}

	
}
