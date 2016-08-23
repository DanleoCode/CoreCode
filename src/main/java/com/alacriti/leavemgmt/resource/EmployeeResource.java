package com.alacriti.leavemgmt.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.deligate.EmployeeDeligate;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.MasterTable;
import com.alacriti.leavemgmt.valueobject.URLConstant;

/* Resource Path : /employee */
@Path(URLConstant.EMPLOYEE)
public class EmployeeResource {

	public static Logger logger = Logger.getLogger(EmployeeResource.class);
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	/* Resource Path : /employee */
	
	public EmployeeInfo createEmployee(EmployeeProfile employeeInfo){
		EmployeeInfo empProfile = new EmployeeProfile();
		empProfile = EmployeeDeligate.createNewEmployee(employeeInfo); 
		return empProfile;
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.GET_EMPLOYEE_BY_ID)
	/* Resource Path : "/employee/{employeeId}" */
	
	public EmployeeInfo getEmployeeInfo(@PathParam(URLConstant.EMPLOYEEID) int id){
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		return Bo.getEmployeeInfo(id);
	}
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path(URLConstant.GET_EMPLOYEE_BY_ID)
	/*Resource Path : "/employee/{employeeId}" */
	
	public EmployeeProfile updateEmployeeInfo(@PathParam(URLConstant.EMPLOYEEID) int id, EmployeeProfile employeeInfo){
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		Bo.updateEmployeeInfo(employeeInfo);
		return employeeInfo;
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.PROFILE)
	/*Resource Path : "/employee/profile/{employeeId}" */	
	public EmployeeProfile getEmployeeProfile(@PathParam(URLConstant.EMPLOYEEID) int empId){
		EmployeeBOImplement Bo = new EmployeeBOImplement();
		return Bo.getEmployeeProfile(empId);
	}
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path(URLConstant.PROFILE)
	/*Resource Path : "employee/profile/{employeeId}" */
	
	public Employee updateEmployeeProfile(@PathParam(URLConstant.EMPLOYEEID) int empId, Employee employee){
		logger.info("Hello update");
		return EmployeeDeligate.updateEmployee(empId, employee);
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.PROFILES)
	/*Resource Path : "employee/profiles" */
	
	public List<EmployeeProfile> getProfiles(@DefaultValue("0") @QueryParam("offset") int offset,
											@DefaultValue("10")@QueryParam("limit") int limit){
		logger.info("start " + offset);
		logger.info("limit " + limit);
		return EmployeeDeligate.getProfiles(offset, limit);
	}
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path(URLConstant.UPDATE)
	/*Resource Path : "employee/update" */
	
	public EmployeeProfile updatePassword(Employee employee){
		EmployeeDeligate employeeDeligate = new EmployeeDeligate();
		return employeeDeligate.updatePassword(employee);
		
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.SEARCH)
	/*Resource Path : "employee/search"*/
	
	public List<EmployeeProfile> searchProfile(@QueryParam("q") String query){
		return EmployeeDeligate.searchProfile(query);
	}
	
	@PUT
	@Consumes("application/json")
	@Path(URLConstant.UPDATE_QUESTION)
	/*Resource Path : "employee/update/question"*/
	
	public int updateQuestion(Employee employee){
		int updatedRows = -1;
		EmployeeDeligate.updateSeurityQuestion(employee);
		return updatedRows;
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.QUESTION)
	/*Resource Path : "employee/question"*/
	
	public List<MasterTable> getQuestions(){
		return EmployeeDeligate.getAllSecurityQuestion();
	}
}
