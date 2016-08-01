package com.alacriti.leavemgmt.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.util.LogRecord;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.URLConstant;

//"/employee"
@Path(URLConstant.EMPLOYEE)
public class EmployeeResource {

	public static Logger logger = Logger.getLogger(EmployeeResource.class);
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.GET_EMPLOYEE_BY_ID)
	/* Resource Path : "/employee/{employeeId}" */
	
	public EmployeeInfo getEmp(@PathParam("employeeId") int id){
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		return Bo.getEmployeeInfo(id);
	}
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path(URLConstant.GET_EMPLOYEE_BY_ID)
	/*Resource Path : "/employee/{employeeId}" */
	
	public EmployeeProfile updateEmployeeInfo(@PathParam("employeeId") int id, EmployeeProfile employeeInfo){
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		Bo.updateEmployeeInfo(employeeInfo);
		return employeeInfo;
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.PROFILE)
	/*Resource Path : "/employee/profile/{employeeId}" */	
	public EmployeeProfile EmployeeProfile(@PathParam("employeeId") int empId){
		LogRecord.logger.warn("im warning");
		LogRecord.logger.info("i m Info");
		//LogRecord.logger.debug("i m debug");
		//LogRecord.logger.trace("i m trace");
		LogRecord.logger.fatal("i m fatal");
		LogRecord.logger.error("i m error");
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		return Bo.getEmployeeProfile(empId);
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	/* Resource Path : /employee*/
	public EmployeeInfo PostEmployee(EmployeeProfile employeeInfo){
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		Bo.addEmployee(employeeInfo);
		return employeeInfo;
	}
	
	
}
