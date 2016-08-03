package com.alacriti.leavemgmt.bo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.dao.LeaveDAOImplement;
import com.alacriti.leavemgmt.deligate.LeaveDeligate;
import com.alacriti.leavemgmt.util.LeaveStatus;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveInstance;

public class LeaveBOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(LeaveDeligate.class);

	public LeaveBOImplement() {
		con = ConnectionHelper.getConnection();
	}

	public void AddNewLeave(Leave leave) {
		logger.info("it has this id :" + leave.getEmpId());
		LeaveDAOImplement leaveDAOImplement = new LeaveDAOImplement(this.con);
		long generatedLeaveId = leaveDAOImplement.AddLeaveInstance(leave);

		logger.info("IN BO method ADDLEAVEINSTANCE executed : " + generatedLeaveId);
		if (generatedLeaveId > 0L) {
			
			EmployeeBOImplement employeeBO = new EmployeeBOImplement();
			EmployeeProfile employeeProfile = new EmployeeProfile();
			logger.info("employee id is " + leave.getEmpId());
			employeeProfile = employeeBO.getEmployeeProfile(leave.getEmpId());
			
			LeaveDeligate leaveDeligate = new LeaveDeligate();
			logger.info("empId is in BOIMPLEMENTED " + employeeProfile);
			LeaveInstance leaveInstance = leaveDeligate.createNewLeaveInstance(
					employeeProfile, generatedLeaveId, leave);

			int numOfRowsUpdated = leaveDAOImplement
					.addNewEmployeeLeaveInstance(leaveInstance);
			if (numOfRowsUpdated == 1) {
				ConnectionHelper.commitConnection(con);
			} else 
				ConnectionHelper.rollbackConnection(con);
		}else{
			logger.info("in else part");
			ConnectionHelper.rollbackConnection(con);
		}
		ConnectionHelper.finalizeConnection(con);
	}

	public LeaveInstance updateLeaveStatus(LeaveInstance leaveInstance) {

		LeaveDAOImplement leaveDAOImplement = new LeaveDAOImplement(con);
		int updatedRows = leaveDAOImplement.updateLeaveStatus(leaveInstance);
		logger.info("number Of rows updated : " + updatedRows);
		ConnectionHelper.commitConnection(con);
		ConnectionHelper.finalizeConnection(con);
		return leaveInstance;
	}

	
	
	public List<LeaveInstance> getLeaveApprovalList(int employeeId){
		List<LeaveInstance> list = new ArrayList<LeaveInstance>();
		LeaveDAOImplement leaveDAOImplement = new LeaveDAOImplement(con);
		short leaveStatusCode = LeaveStatus.inProgress;
		list = leaveDAOImplement.getPendingLeaveApproval(employeeId, leaveStatusCode);
		ConnectionHelper.finalizeConnection(con);
		return list;
	}
	
	public List<EmployeeLeaveHistory> getEmployeeLeaveHistory(int empId){
		List<EmployeeLeaveHistory> list = new ArrayList<EmployeeLeaveHistory>();
		LeaveDAOImplement leaveDAOImplement  =new LeaveDAOImplement(con);
		list = leaveDAOImplement.getAllLeaves(empId);
		ConnectionHelper.finalizeConnection(con);
		return list;
	}
	
	public LeaveBalance getLeaveBalance(int empId, int year){
		LeaveDAOImplement leaveDAOImplement = new LeaveDAOImplement(con);
		return leaveDAOImplement.getLeaveBalance(empId, year);
	}
}
