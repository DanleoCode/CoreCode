package com.alacriti.leavemgmt.valueobject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tempcred {
	private String user;
	private String pass;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Tempcred() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
