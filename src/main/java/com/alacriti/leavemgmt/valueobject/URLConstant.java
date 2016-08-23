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
	public static final String  APPROVAL = "/approval";
											
	public static final String PROFILES = "/profiles";
	public static final String UPDATE = "/update";
	public static final String SEARCH = "/search";
	public static final String APPROVE = "/approved";
	public static final String AVAIL = "/avail";
	public static final String ALL = "/all";
	public static final String ADMIN = "/admin";
	public static final String STATISTICS = "/stats";
	public static final String LEAVE_HISTORY = "leavehistory";
	public static final String QUESTION = "/question";
	public static final String UPDATE_QUESTION = UPDATE + QUESTION;
}