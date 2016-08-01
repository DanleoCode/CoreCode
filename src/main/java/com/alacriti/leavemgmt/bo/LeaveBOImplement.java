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
import com.alacriti.leavemgmt.valueobject.LeaveInstance;

public class LeaveBOImplement {
	private Connection con;
	public static Logger logger = Logger.getLogger(LeaveDeligate.class);

	public LeaveBOImplement() {
		con = ConnectionHelper.getConnection();
	}

	public void AddNewLeave(Leave leave) {
		boolean commitFlag = false;
		LeaveDAOImplement leaveDAOImplement = new LeaveDAOImplement(this.con);
		long generatedLeaveId = leaveDAOImplement.AddLeaveInstance(leave);

		logger.info("IN BO method");
		if (generatedLeaveId > 0) {
			commitFlag = true;
			EmployeeBOImplement employeeBO = new EmployeeBOImplement();
			EmployeeProfile employeeProfile = new EmployeeProfile();
			employeeProfile = employeeBO.getEmployeeProfile(leave.getEmpId());

			LeaveDeligate leaveDeligate = new LeaveDeligate();
			LeaveInstance leaveInstance = leaveDeligate.createNewLeaveInstance(
					employeeProfile, generatedLeaveId, leave);

			int numOfRowsUpdated = leaveDAOImplement
					.addNewEmployeeLeaveInstance(leaveInstance);
			if (numOfRowsUpdated != 1) {
				commitFlag = false;
			}
		}
		if (commitFlag) {
			ConnectionHelper.commitConnection(con);

		} else
			ConnectionHelper.rollbackConnection(con);
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
}
