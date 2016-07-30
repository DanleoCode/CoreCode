package com.alacriti.leavemgmt.deligate;

import java.sql.Timestamp;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.alacriti.leavemgmt.bo.LeaveBOImplement;
import com.alacriti.leavemgmt.util.LeaveStatus;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveInstance;

public class LeaveDeligate {
	
	//public static Logger logger = Logger.getLogger(LeaveDeligate.class);
	
	public Response validateLeave(int empId,Leave leave){
		if(leave.getEmpId() == empId){
			//logger.info("IN DEligate method");
			System.out.println("IN DEligate method");
			LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
			leaveBOImplement.AddNewLeave(leave);
			return Response.status(Status.OK)
					.entity(leave)
					.build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	public LeaveInstance createNewLeaveInstance(EmployeeProfile employeeProfile,long generatedLeaveId,Leave leave){
		LeaveInstance leaveInstance = new LeaveInstance();
		leaveInstance.setEmployeeProfile(employeeProfile);
		leaveInstance.setLeaveId(generatedLeaveId);
		leaveInstance.setLeaveStatusCode(LeaveStatus.inProgress);
		Timestamp creationTime = new Timestamp(new java.util.Date().getTime());
		leaveInstance.setCreationTime(creationTime);
		leaveInstance.setLastModified(creationTime);
		return leaveInstance;
	}
}
