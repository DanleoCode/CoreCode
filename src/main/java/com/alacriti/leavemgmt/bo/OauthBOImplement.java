package com.alacriti.leavemgmt.bo;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.dao.EmployeeDAOImplement;
import com.alacriti.leavemgmt.deligate.AuthDeligate;
import com.alacriti.leavemgmt.util.AccountStatusCode;
import com.alacriti.leavemgmt.valueobject.EmployeeInfo;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.UserSession;

public class OauthBOImplement {
	
		private Connection con;
		public static Logger logger = Logger.getLogger(OauthBOImplement.class);
		
		public OauthBOImplement(){
			this.con = ConnectionHelper.getConnection();
		}
		
		public EmployeeInfo ProcessRequest(EmployeeProfile empProfile, UserSession session){
			EmployeeInfo employeeInfo = null;
			EmployeeProfile employeeProfile = null;
			EmployeeBOImplement employeeBOImplement = null;
			EmployeeDAOImplement employeeDAOImplement = new EmployeeDAOImplement(con);
			employeeInfo = employeeDAOImplement.getInfoByAttribute("email", empProfile.getEmail());
			
			if(employeeInfo == null){
				logger.info("User does not exist");
				employeeBOImplement = new EmployeeBOImplement();
				
				Timestamp date = new Timestamp(new java.util.Date().getTime());
				empProfile.setEmployeeAccountStatus(AccountStatusCode.NOT_APPROVED);
				empProfile.setCreationTime(date);
				empProfile.setLastModifiedTime(date);
				empProfile.setProjectId(1);
				empProfile.setSecurityQuestionId(940);
				empProfile.setEmployeeType((short) 961);
				empProfile.setApprover1(19);
				empProfile.setApprover2(19);
				empProfile.setApprover3(19);
				empProfile.setPassword(empProfile.getEmail());
				employeeBOImplement.addEmployee(empProfile);
									
				EmployeeBOImplement implement = new EmployeeBOImplement();
				List<EmployeeProfile> list = implement.AutherizedAccess(empProfile.getEmpCode(), empProfile.getPassword());
				if(list.size() > 0)
					employeeProfile = list.get(0);
			} else {
				logger.info("User Exist");
				EmployeeDAOImplement empDaoImplement = new EmployeeDAOImplement(con);
				List<EmployeeProfile> list1 = empDaoImplement.employeeDetail(employeeInfo.getEmpId(), "emp_Id");
				if(list1.size() > 0){
					employeeProfile = list1.get(0);
					logger.info("passed the test");
					int empId = list1.get(0).getEmpId();
					short empType = list1.get(0).getEmployeeType();
					Timestamp lastMOdifiedTime = new Timestamp(
							new java.util.Date().getTime());

					session.setLastMOdifiedTime(lastMOdifiedTime);
					session.setEmployeeType(empType);
					session.setEmpId(empId);

					SessionBOImplement sessionBOImplement = new SessionBOImplement();
					if (AuthDeligate.getSession(empId).getEmpId() == list1.get(0).getEmpId()) {
						logger.info("empId exist");
						sessionBOImplement.updateSessionBO(session);
					} else {
						logger.info("empId does not exist in session table");
						sessionBOImplement.createUserSession(session, list1.get(0));
					}
					
				}
				

					

	
				
				
				
				logger.info("got the info");
				logger.info(employeeProfile);
			}
			ConnectionHelper.finalizeConnection(con);
			logger.info("end of OAuth BO");
			return employeeProfile;
		}
}
