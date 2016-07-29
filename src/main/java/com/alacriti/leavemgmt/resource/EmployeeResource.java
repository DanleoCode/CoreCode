package com.alacriti.leavemgmt.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.alacriti.leavemgmt.bo.EmployeeBO;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.URLConstant;

@Path(URLConstant.EMPLOYEE)
public class EmployeeResource {

	
	@GET
	@Produces("application/json")
	@Path(URLConstant.GET_EMPLOYEE_BY_ID)
	public EmployeeInfo getEmp(@PathParam("employeeId") int id){
		EmployeeBO Bo = new EmployeeBO();
		return Bo.getEmployeeInfo(id);
	}
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path(URLConstant.GET_EMPLOYEE_BY_ID)
	public EmployeeProfile updateEmployeeInfo(@PathParam("employeeId") int id, EmployeeProfile employeeInfo){
		EmployeeBO Bo = new EmployeeBO();
		Bo.updateEmployeeInfo(employeeInfo);
		return employeeInfo;
	}
	
	@GET
	@Path(URLConstant.PROFILE)
	@Produces("application/json")
	public EmployeeProfile EmployeeProfile(@PathParam("employeeId") int empId){
		EmployeeBO Bo = new EmployeeBO();
		return Bo.getEmployeeProfile(empId);
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public EmployeeInfo PostEmployee(EmployeeProfile employeeInfo){
		EmployeeBO Bo = new EmployeeBO();
		Bo.addEmployee(employeeInfo);
		return employeeInfo;
	}
	
	
}
