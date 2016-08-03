package com.alacriti.leavemgmt.deligate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.LeaveBOImplement;
import com.alacriti.leavemgmt.util.LeaveStatus;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveInstance;

public class LeaveDeligate {

	public static Logger logger = Logger.getLogger(LeaveDeligate.class);

	public Response validateLeave(int empId, Leave leave) {
		if (leave.getEmpId() == empId) {
			LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
			leaveBOImplement.AddNewLeave(leave);
			return Response.status(Status.OK).entity(leave).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	public LeaveInstance createNewLeaveInstance(
			EmployeeProfile employeeProfile, long generatedLeaveId, Leave leave) {
		
		LeaveInstance leaveInstance = new LeaveInstance();
		leaveInstance.setEmployeeProfile(employeeProfile);
		leaveInstance.setLeaveId(generatedLeaveId);
		leaveInstance.setLeaveStatusCode(LeaveStatus.inProgress);
		Timestamp creationTime = new Timestamp(new java.util.Date().getTime());
		leaveInstance.setCreationTime(creationTime);
		leaveInstance.setLastModified(creationTime);
		return leaveInstance;
	}

	public LeaveInstance updateLeaveStatus(LeaveInstance leaveInstance) {
		logger.info("Ind leavedeligate");
		logger.error("Ind leavedeligate");
		
		Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
		
		leaveInstance.setLastModified(currentTime);
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		leaveBOImplement.updateLeaveStatus(leaveInstance);
		
		return leaveInstance;
	}
	
	public Response getEmployeeLeaveHistory(EmployeeProfile employeeProfile){
		List<EmployeeLeaveHistory> list = new ArrayList<EmployeeLeaveHistory>();
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		list = leaveBOImplement.getEmployeeLeaveHistory(employeeProfile.getEmpId());
		if(list.size() == 0){
			return Response.status(Status.NO_CONTENT)
							.entity("Record not found")
							.build();
		} else{
			return Response.status(Status.OK)
					.entity(list)
					.build();
		}
	}
	
	public LeaveBalance getLeaveBalance(int empId){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		return leaveBOImplement.getLeaveBalance(empId, year);
	}
}
