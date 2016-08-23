package com.alacriti.leavemgmt.dao;

import java.sql.SQLException;
import java.util.List;

import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;

public interface EmployeeDAO {

	/*
	 * Create a new entry in employee table with given EmployeeProfile Object
	 * and returns generated Employee Id
	 */

	public int addEmployeeInfo(EmployeeProfile employeeProfile)
			throws SQLException, NullPointerException, Exception;

	/*
	 * Create a new entry in employee table with given EmployeeProfile Object
	 * and return the number of updated rows
	 */
	public int addEmployeeProfile(EmployeeProfile employeeProfile)
			throws SQLException, NullPointerException, Exception;
	/*
	 * return EmployeeInfo Object if employee table has an entry for empId
	 * otherwise null
	 */
	public EmployeeInfo selectEmployeeInfo(int empId)
			throws SQLException, NullPointerException, Exception;

	/*
	 * return EmployeeProfile Object if employee table has an entry for empId
	 * otherwise null
	 */
	public EmployeeProfile selectEmployeeProfile(int empId)
			throws SQLException, NullPointerException, Exception;

	/*
	 * Update employeeProfile table with EmployeeProfile Object and returns
	 * number of updated rows
	 */
	public int updateEmployeeProfile(EmployeeProfile profile)
			throws SQLException, NullPointerException, Exception;

	/*
	 * Update employeeInfo table with EmployeeInfo Object and returns number of
	 * updated rows
	 */
	public int updateEmployeeInfo(EmployeeInfo employeeInfo)
			throws SQLException, NullPointerException, Exception;

	/*
	 * Return employee_id if credential matched in database
	 */
	public int authLogin(String loginId, String passwd)
			throws SQLException, NullPointerException, Exception;

	/*
	 * Generic method return List of Employee (Profile + Info) Where 'column' =
	 * 'value' --limited to string type columns--
	 */
	public List<EmployeeProfile> employeeDetail(int value, String column)
			throws SQLException, NullPointerException, Exception;

	/*
	 * Method to get List of Employee(Profile + Info) with offset and limit
	 */
	public List<EmployeeProfile> getProfiles(int offset, int limit)
			throws SQLException, NullPointerException, Exception;

	/*
	 * Create an entry in 185_employee_balance_table
	 */
	public int createEmployeeBalanceRecord(int empId) throws SQLException,
			NullPointerException, Exception;

	/*
	 * update `column` with `value` for profile table for given emp_id --limited
	 * to string type column--
	 */
	public int updateEmployeeProfileAttribute(int empId, String atr,
			String value) throws SQLException, NullPointerException, Exception;

	/*
	 * get Profile for number attribute for an empId
	 */
	public int updateEmployeeProfileNumberAttribute(int empId, String atr,
			int value) throws SQLException, NullPointerException, Exception;

	/*
	 * Generic Method to get record from info table where `column` = `value' --
	 * limited to String type Columns --
	 */
	public List<EmployeeInfo> getInfoByAttribute(String column, String value)
			throws SQLException, NullPointerException, Exception;
}
