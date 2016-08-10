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
import com.alacriti.leavemgmt.valueobject.Employee;
import com.alacriti.leavemgmt.valueobject.EmployeeLeaveHistory;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.Leave;
import com.alacriti.leavemgmt.valueobject.LeaveBalance;
import com.alacriti.leavemgmt.valueobject.LeaveHistory;

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

	public LeaveHistory createNewLeaveInstance(EmployeeProfile employeeProfile,
			long generatedLeaveId, Leave leave) {

		LeaveHistory leaveHistory = new LeaveHistory();
		leaveHistory.setEmployeeProfile(employeeProfile);
		leaveHistory.setLeaveId(generatedLeaveId);
		leaveHistory.setLeaveStatusCode(LeaveStatus.inProgress);
		Timestamp creationTime = new Timestamp(new java.util.Date().getTime());
		leaveHistory.setCreationTime(creationTime);
		leaveHistory.setLastModified(creationTime);
		return leaveHistory;
	}

	public LeaveHistory updateLeaveStatus(int employeeId,
			LeaveHistory leaveInstance) {

		Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
		leaveInstance.setLastModified(currentTime);
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		LeaveHistory history = leaveBOImplement
				.getLeaveHistoryById(leaveInstance.getLeaveId());
		long currentLeaveStatus = history.getLeaveStatusCode();
		logger.info("current leave status is: " + currentLeaveStatus);
		if (leaveInstance.getLeaveStatusCode() == 1) {
			if (currentLeaveStatus == LeaveStatus.inProgress)
				leaveInstance
						.setLeaveStatusCode(LeaveStatus.APPROVER1_APPROVED);
			else if (currentLeaveStatus == LeaveStatus.APPROVER1_APPROVED)
				leaveInstance
						.setLeaveStatusCode(LeaveStatus.APPROVER2_APPROVED);
			else if (currentLeaveStatus == LeaveStatus.APPROVER2_APPROVED)
				leaveInstance
						.setLeaveStatusCode(LeaveStatus.APPROVER3_APPROVED);
		} else if (leaveInstance.getLeaveStatusCode() == 0) {
			if (currentLeaveStatus == LeaveStatus.inProgress)
				leaveInstance
						.setLeaveStatusCode(LeaveStatus.APPROVER1_REJECTED);
			else if (currentLeaveStatus == LeaveStatus.APPROVER1_APPROVED)
				leaveInstance
						.setLeaveStatusCode(LeaveStatus.APPROVER2_REJECTED);
			else if (currentLeaveStatus == LeaveStatus.APPROVER2_APPROVED)
				leaveInstance
						.setLeaveStatusCode(LeaveStatus.APPROVER3_REJECTED);
		}
		logger.info("new status will be : "
				+ leaveInstance.getLeaveStatusCode());
		leaveBOImplement.updateLeaveStatus(leaveInstance);

		return leaveInstance;
	}

	public Response getEmployeeLeaveHistory(EmployeeProfile employeeProfile) {
		List<EmployeeLeaveHistory> list = new ArrayList<EmployeeLeaveHistory>();
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		list = leaveBOImplement.getEmployeeLeaveHistory(employeeProfile
				.getEmpId());
		if (list.size() == 0) {
			return Response.status(Status.NO_CONTENT)
					.entity("Record not found").build();
		} else {
			return Response.status(Status.OK).entity(list).build();
		}
	}

	public LeaveBalance getLeaveBalance(int empId) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		LeaveBalance leaveBalance = leaveBOImplement.getLeaveBalance(empId,
				year);
		return leaveBalance;
	}

	public List<Employee> getLeaveApprovalList(int empId) {
		List<Employee> list = new ArrayList<Employee>();
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		short leaveStatusCode = LeaveStatus.inProgress;

		List<Employee> list1 = new ArrayList<Employee>();
		list1 = leaveBOImplement.getLeaveApprovalList(empId, leaveStatusCode,
				"approver1_id");

		List<Employee> list2 = new ArrayList<Employee>();
		LeaveBOImplement BOImplementLevel2 = new LeaveBOImplement();
		leaveStatusCode = LeaveStatus.APPROVER1_APPROVED;
		list2 = BOImplementLevel2.getLeaveApprovalList(empId, leaveStatusCode,
				"approver2_id");
		list.addAll(list1);
		list.addAll(list2);

		LeaveBOImplement BOImplementLevel3 = new LeaveBOImplement();
		leaveStatusCode = LeaveStatus.APPROVER2_APPROVED;
		list1 = BOImplementLevel3.getLeaveApprovalList(empId, leaveStatusCode,
				"approver3_id");
		list.addAll(list1);
		return list;
	}

	public LeaveBalance updateLeaveBalance(LeaveBalance leaveBalance) {
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		int updatedRows = -1;
		int empId = leaveBalance.getEmpId();
		LeaveBalance balance = getLeaveBalance(empId);

		if (balance == null) {
			logger.info("Leave record does not exist");
			updatedRows = leaveBOImplement.newLeaveBalance(leaveBalance);
		} else {
			logger.info("Leave record exist");
			updatedRows = leaveBOImplement.updateLeavebalance(leaveBalance);
		}

		if (updatedRows == 1)
			return leaveBalance;
		return null;
	}

	public List<Employee> getApprovedLeaves(int empId) {
		List<Employee> list = new ArrayList<Employee>();
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		list = leaveBOImplement.getLeaveApprovalList(empId,
				LeaveStatus.APPROVER3_APPROVED, "approver3_id");
		return list;
	}

	public LeaveHistory availLeavRequest(Employee employee) {
		Timestamp lastModified = new Timestamp(new java.util.Date().getTime());
		LeaveHistory history = employee.getLeaveHistory();
		history.setLastModified(lastModified);
		LeaveBalance leaveBalance = employee.getLeaveBalance();
		logger.info("in availLeaverequest deligate");
		if (history != null || leaveBalance != null) {
			LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
			if (leaveBOImplement.AvailLeaveRequest(leaveBalance, history)){
				return history;
			}
			logger.info("deligate got nothing");
		}
		return null;
	}
	
	public List<Employee> getAllLeavesHistory() {
		List<Employee> list = new ArrayList<Employee>();
		LeaveBOImplement leaveBOImplement = new LeaveBOImplement();
		list = leaveBOImplement.getAllLeaves();
		return list;
	}
	
}
