package com.alacriti.leavemgmt.appfilter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.deligate.AuthDeligate;
import com.alacriti.leavemgmt.valueobject.UserSession;

@Provider
public class Filter implements ContainerRequestFilter{

	public static Logger logger = Logger.getLogger(Filter.class);

	@Context
	private HttpServletRequest httpServletRequest;

	@Override
	public void filter(ContainerRequestContext containerRequestContext)
			throws IOException{

		HttpSession httpSession = httpServletRequest.getSession();
		logger.info(httpSession.getId());

		String requestForRescource = containerRequestContext.getUriInfo()
				.getPath();
		boolean sessionExist = false;
		
		if (requestForRescource.equals("/auth/logout")
				&& containerRequestContext.getMethod().equals("POST")) {
			httpSession.invalidate();
		}
		try {
			String sessionId = httpSession.getId();
			logger.info("sessionId " + sessionId);
			int empId = (int) httpSession.getAttribute("empId");
			logger.info(empId);
			logger.info("empId" + empId);
			UserSession userSession = AuthDeligate.getSession(sessionId);
			logger.info("in filter");
			if (userSession == null) {
				logger.info("Session Not Found");
				
			} else {
				logger.info("Session Exist " + userSession.getEmpId());
			}
			logger.info("Session: " + empId);
			sessionExist = true;
		} catch (NullPointerException ex) {
			logger.error("No Session" + ex.getMessage());
			try {
				throw new IllegalAccessException("Please Login");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalStateException ex) {
			logger.info("Session not exist");
			try {
				throw new IllegalAccessException("Please Login");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			if (!sessionExist) {
				if (requestForRescource.equals("/auth/login")) {
					logger.info("login request");
				} else if (requestForRescource.equals("/employee")
						&& containerRequestContext.getMethod().equals("POST")) {
					logger.info("add new employee");
				} else if (requestForRescource.equals("/auth/logout")
						&& containerRequestContext.getMethod().equals("POST")) {
					// httpSession.invalidate();
				}
			}
		}

		// logger.info(session.isNew());
		// if (requestForRescource.equals("/auth/login")) {
		//
		// session.setAttribute("empId", "Hello");
		// logger.info(session);
		// logger.info("login request");
		// //return;
		// } else if (requestForRescource.equals("/employee")
		// && containerRequestContext.getMethod().equals("POST")) {
		// logger.info("add new employee");
		// } else if (requestForRescource.equals("/auth/logout")
		// && containerRequestContext.getMethod().equals("POST")) {
		// logger.info(session.getAttribute("Employee"));
		// session.invalidate();
		// logger.info("Logged Out" + session);
		// }
		// try{
		// String sessionId = session.getId();
		// String empId = (String)session.getAttribute("empId");
		// UserSession userSession = AuthDeligate.getSession(sessionId);
		// if(userSession == null){
		// logger.info("Session Not Found");
		// }
		// logger.info("Session: " + empId);
		// }catch(IllegalStateException ex){
		// logger.info("Session not exist");
		// }

	}

}
