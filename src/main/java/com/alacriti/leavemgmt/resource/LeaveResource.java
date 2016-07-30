package com.alacriti.leavemgmt.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.alacriti.leavemgmt.deligate.LeaveDeligate;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.URLConstant;

@Path(URLConstant.LEAVE)
public class LeaveResource {

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	/*URL : /employee/{employeeId}/leave */
	public Response postLeave(@PathParam("employeeId") int employeeId ,Leave leave){
		LeaveDeligate leaveDeligate = new LeaveDeligate();
		return leaveDeligate.validateLeave(employeeId,leave);
	}
}
