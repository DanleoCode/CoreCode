package leavemanagement.bo;

import java.sql.ResultSet;
import java.util.List;

import leavemanagement.valueobject.EmployeeInfo;
import leavemanagement.valueobject.EmployeeProfile;

public interface EmployeeResultProcess {
	public EmployeeInfo parseEmployeeInfoResultSet(ResultSet rs);
	public EmployeeProfile parseEmployeeProfileResultSet(ResultSet rs);
	public List<EmployeeInfo> parseResultSetToList(ResultSet rs);
	
}
