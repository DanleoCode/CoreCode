package com.alacriti.leavemgmt.bo;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.dao.LeaveDAOImplement;
import com.alacriti.leavemgmt.deligate.LeaveDeligate;
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
			EmployeeBO employeeBO = new EmployeeBO();
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
		if(commitFlag){
			ConnectionHelper.commitConnection(con);
			
		}
		else
			ConnectionHelper.rollbackConnection(con);
	}
}
