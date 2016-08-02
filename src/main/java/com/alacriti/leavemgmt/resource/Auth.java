package com.alacriti.leavemgmt.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.alacriti.leavemgmt.deligate.AuthDeligate;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.LoginCredential;
import com.alacriti.leavemgmt.valueobject.UserSession;

@Path("/auth")
public class Auth {

	@Context
	private HttpServletRequest httpServletRequest;
	
	@POST
	@Produces("application/json")
	@Path("/login")
	
	public EmployeeProfile auth(LoginCredential tempCred){
		HttpSession session = httpServletRequest.getSession();
		
		UserSession userSession = new UserSession();
		userSession.setEmpSessionId(session.getId());
		
		EmployeeProfile employeeProfile = AuthDeligate.validateCredentials(tempCred,userSession);
		
		userSession.setEmpId(employeeProfile.getEmpId());
		userSession.setEmployeeType(employeeProfile.getEmployeeType());
		session.setAttribute("empId", employeeProfile.getEmpId());
		session.setAttribute("empType", employeeProfile.getEmpId());
		return employeeProfile;
	}
	
	@POST
	@Produces("text/plain")
	@Path("/logout")
	public String logOut(){
		return "LOGGED OUT";
	}
}
