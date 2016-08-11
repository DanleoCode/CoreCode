package com.alacriti.leavemgmt.resource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.deligate.AuthDeligate;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.LoginCredential;
import com.alacriti.leavemgmt.valueobject.UserSession;

@Path("/auth")
public class Auth {

	public static Logger logger = Logger.getLogger(AuthDeligate.class);
	@Context
	private HttpServletRequest httpServletRequest;
	
	
	@POST
	@Produces("application/json")
	@Path("/login")
	
	public List<EmployeeProfile> auth(LoginCredential tempCred){
		HttpSession session = httpServletRequest.getSession();
		List<EmployeeProfile> list = new ArrayList<EmployeeProfile>();
		logger.info("hit the auth resource");
		UserSession userSession = new UserSession();
		userSession.setEmpSessionId(session.getId());
		
		list = AuthDeligate.validateCredentials(tempCred,userSession);
		try{
		userSession.setEmpId(list.get(0).getEmpId());
		userSession.setEmployeeType(list.get(0).getEmployeeType());
		session.setAttribute("empId", list.get(0).getEmpId());
		session.setAttribute("empType", list.get(0).getEmployeeType());
		} catch(NullPointerException ex){
			logger.info("not authenticated");
		} catch(IndexOutOfBoundsException ex){
			logger.info("not authenticated");
		}
		return list;
	}
	
	@POST
	@Produces("text/plain")
	@Path("/logout")
	public String logOut(){
		return "LOGGED OUT";
	}
	
	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain")
	@Path("/oauth")
	public String oAuth(@FormParam("token_id") String token){
		logger.info(token);
		try {
			AuthDeligate.verifyToken(token);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace();
		}
		return token;
	}
}
