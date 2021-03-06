package com.alacriti.leavemgmt.appfilter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.deligate.AuthDeligate;
import com.alacriti.leavemgmt.valueobject.UserSession;

@Provider
public class Filter implements ContainerRequestFilter {

	public static Logger logger = Logger.getLogger(Filter.class);

	@Context
	private HttpServletRequest httpServletRequest;

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
			throws IOException {

		HttpSession httpSession = httpServletRequest.getSession();
		logger.info("new session id : " + httpSession.getId());

		String requestForRescource = containerRequestContext.getUriInfo()
				.getPath();

		if (requestForRescource.equals("/auth/logout")
				&& containerRequestContext.getMethod().equals("POST")) {
			httpSession.invalidate();
		} else if (requestForRescource.equals("/auth/login")) {
			logger.info("login request");
		} else if (requestForRescource.equals("/auth/oauth")) {
			logger.info("oauth request");
		}else if (requestForRescource.equals("/employee")
				&& containerRequestContext.getMethod().equals("POST")) {
			logger.info("add new employee");
		} else {
			try {
				
				if ((int) httpSession.getAttribute("empId") > 0) {
					String sessionId = httpSession.getId();
					int empId = (int) httpSession.getAttribute("empId");
					logger.info("got the empId: " + empId);
					UserSession userSession = AuthDeligate.getSession(empId);
					if (userSession.getEmpSessionId().equals(sessionId)) {
						logger.info("Session Found");
						if(requestForRescource.contains("/admin/")){
							logger.info("Admin url requested ");
							if(userSession.getEmployeeType() != 964)
								throw new NullPointerException();
						}
					} else {
						containerRequestContext.abortWith(Response.status(
						Status.UNAUTHORIZED).build());
					}
				}
			} catch (NullPointerException ex) {
				logger.error("No Session null session " + ex.getMessage());
				containerRequestContext.abortWith(Response.status(
						Status.UNAUTHORIZED).build());
			} catch (IllegalStateException ex) {
				logger.info("Session not exist");
				containerRequestContext.abortWith(Response.status(
						Status.UNAUTHORIZED).build());
			} 
		}

	}

}
