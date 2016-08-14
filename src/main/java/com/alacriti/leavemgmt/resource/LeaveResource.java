package com.alacriti.leavemgmt.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.deligate.LeaveDeligate;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveHistory;
import com.alacriti.leavemgmt.valueobject.URLConstant;

@Path(URLConstant.LEAVE)
/*URL : employee/{employeeId}/leave */
public class LeaveResource {

	@Context
	private HttpServletRequest request;
	
	public static Logger logger = Logger.getLogger(LeaveDeligate.class);
	
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	/*URL : /employee/{employeeId}/leave */
	public Response postLeave(@PathParam(URLConstant.EMPLOYEEID) int employeeId ,Leave leave){
		return LeaveDeligate.validateLeave(employeeId,leave);
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	
	public LeaveHistory updatedLeaveStatus(@PathParam(URLConstant.EMPLOYEEID) int employeeId ,LeaveHistory leaveInstance){
		HttpSession httpSession = request.getSession();
		logger.info("hello typw: " + httpSession.getAttribute("empType"));
		logger.info("hello Id	: " + httpSession.getAttribute("empId"));
		return LeaveDeligate.updateLeaveStatus(employeeId, leaveInstance);
	}
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path(URLConstant.APPROVAL)
	
	public List<Employee> getListForApprover(@PathParam(URLConstant.EMPLOYEEID) int employeeId){
		return LeaveDeligate.getLeaveApprovalList(employeeId);
	}
	
	@GET
	@Produces("application/json")
	public Response getEmployeeLeaveHistory(@PathParam(URLConstant.EMPLOYEEID) int employeeId){
		EmployeeProfile profile = new EmployeeProfile();
		profile.setEmpId(employeeId);
		return LeaveDeligate.getEmployeeLeaveHistory(profile);
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.LEAVE_BALANCE)
	/*URL : employee/{employeeId}/leave/balance */
	public LeaveBalance getLeaveBalance(@PathParam(URLConstant.EMPLOYEEID) int employeeId){
		return LeaveDeligate.getLeaveBalance(employeeId);
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.APPROVE)
	/*URL : employee/{employeeId}/leave/approve */
	public List<Employee> getApprovedLeaves(@PathParam(URLConstant.EMPLOYEEID) int empId){
		logger.info("in getApprovedLeaves Resource");
		return LeaveDeligate.getApprovedLeaves(empId);
	}
	
	
	
	
}
