package com.alacriti.leavemgmt.valueobject;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployeeProfile extends EmployeeInfo{
	private String loginId;
	
	private String password;
	
	private int securityQuestionId;
	private String securityAnswer;
	private Timestamp lastModifiedTime;
	private Timestamp creationTime;
	private short employeeAccountStatus;
	private short employeeType;
	private int approver1;
	private int approver2;
	private int approver3;
	
	public EmployeeProfile(){
		super();
	}

	public EmployeeProfile(String loginId, String password,
			int securityQuestionId, String securityAnswer,
			Timestamp lastModifiedTime, Timestamp creationTime,
			short employeeAccountStatus, short employeeType, int approver1,
			int approver2, int approver3) {
		super();
		this.loginId = loginId;
		this.password = password;
		this.securityQuestionId = securityQuestionId;
		this.securityAnswer = securityAnswer;
		this.lastModifiedTime = lastModifiedTime;
		this.creationTime = creationTime;
		this.employeeAccountStatus = employeeAccountStatus;
		this.employeeType = employeeType;
		this.approver1 = approver1;
		this.approver2 = approver2;
		this.approver3 = approver3;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public int getSecurityQuestionId() {
		return securityQuestionId;
	}

	public void setSecurityQuestionId(int securityQuestionId) {
		this.securityQuestionId = securityQuestionId;
	}

	public String getSecurityAnswer() {
		return securityAnswer;
	}

	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}

	public Timestamp getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Timestamp lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public short getEmployeeAccountStatus() {
		return employeeAccountStatus;
	}

	public void setEmployeeAccountStatus(short employeeAccountStatus) {
		this.employeeAccountStatus = employeeAccountStatus;
	}

	public short getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(short employeeType) {
		this.employeeType = employeeType;
	}

	public int getApprover1() {
		return approver1;
	}

	public void setApprover1(int approver1) {
		this.approver1 = approver1;
	}

	public int getApprover2() {
		return approver2;
	}

	public void setApprover2(int approver2) {
		this.approver2 = approver2;
	}

	public int getApprover3() {
		return approver3;
	}

	public void setApprover3(int approver3) {
		this.approver3 = approver3;
	}

	@Override
	public String toString() {
		return "EmployeeProfile [loginId=" + loginId + ", password=" + password
				+ ", securityQuestionId=" + securityQuestionId
				+ ", securityAnswer=" + securityAnswer + ", lastModifiedTime="
				+ lastModifiedTime + ", creationTime=" + creationTime
				+ ", employeeAccountStatus=" + employeeAccountStatus
				+ ", employeeType=" + employeeType + ", approver1=" + approver1
				+ ", approver2=" + approver2 + ", approver3=" + approver3 + ",empId="+super.getEmpId()+"]";
	}

	
	
	
}
