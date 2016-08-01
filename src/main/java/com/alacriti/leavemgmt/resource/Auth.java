package com.alacriti.leavemgmt.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.alacriti.leavemgmt.deligate.AuthDeligate;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Session;
import com.alacriti.leavemgmt.valueobject.Tempcred;

@Path("/auth")
public class Auth {

	@Context
	private HttpServletRequest httpServletRequest;
	
	@POST
	@Produces("application/json")
	@Path("/login")
	
	public EmployeeProfile auth(Tempcred tempCred){
		Session empSession = new Session();
		HttpSession session = httpServletRequest.getSession();
		EmployeeProfile employeeProfile = AuthDeligate.validateCredentials(tempCred);
		empSession.setSessionId(session.getId());
		empSession.setEmpId(employeeProfile.getEmpId());
		session.setAttribute("Employee", empSession);
		return employeeProfile;
	}
	
	@POST
	@Produces("text/plain")
	@Path("/logout")
	public String logOut(){
		return "LOGGED OUT";
	}
}
