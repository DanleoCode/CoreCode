package com.alacriti.leavemgmt.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.LeaveBOImplement;
import com.alacriti.leavemgmt.deligate.LeaveDeligate;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveInstance;
import com.alacriti.leavemgmt.valueobject.URLConstant;

@Path(URLConstant.LEAVE)
/*URL : employee/{employeeId}/leave */
public class LeaveResource {

	public static Logger logger = Logger.getLogger(LeaveDeligate.class);
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	/*URL : /employee/{employeeId}/leave */
	public Response postLeave(@PathParam("employeeId") int employeeId ,Leave leave){
		LeaveDeligate leaveDeligate = new LeaveDeligate();
		return leaveDeligate.validateLeave(employeeId,leave);
	}
	
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	
	public LeaveInstance updatedLeaveStatus(@PathParam("employeeId") int employeeId ,LeaveInstance leaveInstance){
		LeaveDeligate leaveDeligate = new LeaveDeligate();
		return leaveDeligate.updateLeaveStatus(leaveInstance);
	}
	
	@GET
	@Consumes("application/json")
	@Produces("application/json")
	@Path("approval")
	public List<LeaveInstance> getListForApprover(@PathParam("employeeId") int employeeId){
		logger.info("in resiurce");
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		return leaveBOImplement.getLeaveApprovalList(employeeId);
	}
	
	@GET
	@Produces("application/json")
	public Response getEmployeeLeaveHistory(@PathParam("employeeId") int employeeId){
		LeaveDeligate leaveDeligate = new LeaveDeligate();
		EmployeeProfile profile = new EmployeeProfile();
		profile.setEmpId(employeeId);
		return leaveDeligate.getEmployeeLeaveHistory(profile);
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.LEAVE_BALANCE)
	/*URL : employee/{employeeId}/leavebalance */
	public LeaveBalance getLeaveBalance(@PathParam("employeeId") int employeeId){
		LeaveDeligate deligate = new LeaveDeligate();
		return deligate.getLeaveBalance(employeeId);
	}
}
