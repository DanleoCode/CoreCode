package com.alacriti.leavemgmt.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AllegalAccessExceptionMapper implements ExceptionMapper<IllegalAccessException>{

	@Override
	public Response toResponse(IllegalAccessException arg0) {
		
		return Response.status(Status.UNAUTHORIZED)
				.entity("not allowed")
				.build();
	}

}
