package com.alacriti.leavemgmt.valueobject;

public class Session {
	private String sessionId;
	private int empId;
	
	public Session(){
		
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", empId=" + empId+ "]";
	}
	
	
}
