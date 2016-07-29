package com.alacriti.leavemgmt.deligate;

import java.sql.Connection;

import com.alacriti.leavemgmt.bo.ConnectionHelper;

public class Deligate {
	private Connection con; 
	
	public Deligate(){
		this.con = ConnectionHelper.getConnection();
	}
	
	
}
