package com.alacriti.leavemgmt.deligate;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.log4j.Logger;

@Provider
public class Filter implements ContainerRequestFilter {

	public static Logger logger = Logger.getLogger(Filter.class);
	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {

		System.out.println("hello");
		
	}

	
}
