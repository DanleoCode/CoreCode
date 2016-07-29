package com.alacriti.leavemgmt.bo;

import java.sql.ResultSet;
import java.util.List;

import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public interface EmployeeResultProcess {
	
	public EmployeeInfo getSelectEmployeeInfoData(ResultSet rs);
	
	public EmployeeProfile getSelectEmployeeProfileData(ResultSet rs);
	
	public List<EmployeeInfo> parseResultSetToList(ResultSet rs);

	
}
