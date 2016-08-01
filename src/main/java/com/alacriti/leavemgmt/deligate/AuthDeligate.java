package com.alacriti.leavemgmt.deligate;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Tempcred;

public class AuthDeligate {

	public static EmployeeProfile validateCredentials(Tempcred tempCred){
		EmployeeBOImplement employeeBOImplement = new EmployeeBOImplement();
		return employeeBOImplement.AutherizedAccess(tempCred.getUser(), tempCred.getPass());
	}
}
