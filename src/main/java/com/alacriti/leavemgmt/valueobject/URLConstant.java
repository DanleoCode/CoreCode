package com.alacriti.leavemgmt.valueobject;

public class URLConstant {

	public static final String EMPLOYEE = "/employee"; // "/employee"
	public static final String EMPLOYEEID = "employeeId";
	public static final String GET_EMPLOYEE_BY_ID = "/{" + EMPLOYEEID + "}"; 
	public static final String PROFILE = "/profile/{" + EMPLOYEEID + "}"; 		// "URL : profile/{employeeId}"
	public static final String UPDATE_EMPLOYEE_INFO = "/{" + EMPLOYEEID	+ "}/update";	
												/*"URL : {employeeId}/update */
	public static final String LEAVE = EMPLOYEE + "/{employeeId}/leave";
											/*URL : /employee/{employeeId}/leave */
	public static final String LEAVE_BALANCE = "balance";
											/*URL : /employee/{employeeId}/leave/balance */
}
