package com.alacriti.leavemgmt.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.alacriti.leavemgmt.deligate.AdminDeligate;
import com.alacriti.leavemgmt.deligate.EmployeeDeligate;
import com.alacriti.leavemgmt.deligate.LeaveDeligate;
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.LeaveHistory;
import com.alacriti.leavemgmt.valueobject.Statistics;
import com.alacriti.leavemgmt.valueobject.URLConstant;

@Path(URLConstant.ADMIN)
public class Admin {
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.STATISTICS)
	
	public Statistics getStatistics(){
		Statistics statistics = new Statistics();
		statistics = EmployeeDeligate.getStatistics();
		return statistics;
	}
	
	@GET
	@Produces("application/json")
	@Path(URLConstant.LEAVE_HISTORY)
	/*URL : admin/leaves */
	
	public List<Employee> getAllLeavesHistory(){
		return LeaveDeligate.getAllLeavesHistory();
	}
	
	@PUT
	@Produces("application/json")
	@Consumes("application/json")
	@Path(URLConstant.AVAIL)
	/*URL : admin/avail */
	
	public LeaveHistory availLeaveRequest(Employee employee){
		return LeaveDeligate.availLeavRequest(employee);
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	@Path(URLConstant.QUESTION)
	/*URL : admin/question */
	
	public Response AddSecurityQuestion(@FormParam("question") String question){
		return AdminDeligate.PostQuestionDeligate(question);
	}
	
	
}
