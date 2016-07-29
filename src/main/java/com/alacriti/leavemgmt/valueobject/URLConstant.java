package com.alacriti.leavemgmt.valueobject;

public class URLConstant {

	public static final String EMPLOYEE = "/employee"; // "leavenamagement/api/employee"
	public static final String EMPLOYEEID = "employeeId";
	public static final String GET_EMPLOYEE_BY_ID = "/{" + EMPLOYEEID + "}"; // "leavenamagement/api/employee/{employeeId}"
	public static final String PROFILE = "/profile/{" + EMPLOYEEID + "}"; // "leavenamagement/api/employee/profile/{employeeId}"
	public static final String UPDATE_EMPLOYEE_INFO = "/{" + EMPLOYEEID
			+ "}/update";					//"leavenamagement/api/employee/{employeeId}/update"
}
