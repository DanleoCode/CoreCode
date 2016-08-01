package com.alacriti.leavemgmt.appfilter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.valueobject.Session;

@Provider
public class Filter implements ContainerRequestFilter {

	public static Logger logger = Logger.getLogger(Filter.class);

	@Context
	private HttpServletRequest httpServletRequest;

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
			throws IOException {

		HttpSession session = httpServletRequest.getSession();

		String requestForRescource = containerRequestContext.getUriInfo().getPath();
		
		if (requestForRescource.equals("/auth/login")) {
			
			session.setAttribute("empId", "");
			logger.info("login request");
			return;
		} else if (requestForRescource.equals("/employee")
				&& containerRequestContext.getMethod().equals("POST")) {
			logger.info("add new employee");
		} else if (requestForRescource.equals("/auth/logout")
				&& containerRequestContext.getMethod().equals("POST")) {
			logger.info(session.getAttribute("Employee"));
			session.invalidate();
			logger.info("Logged Out");
		}
		try{
			String loginId = (String)session.getAttribute("empId");
			
		}catch(IllegalStateException ex){
			logger.info("Session not exist");
		}
		
	}

}
