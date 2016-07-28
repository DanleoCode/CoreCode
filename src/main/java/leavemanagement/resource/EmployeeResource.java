package leavemanagement.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import leavemanagement.bo.EmployeeDAOProcess;
import leavemanagement.dao.EmployeeService;
import leavemanagement.util.LogRecord;
import leavemanagement.valueobject.EmployeeInfo;
import leavemanagement.valueobject.URLConstant;

@Path(URLConstant.EMPLOYEE)
public class EmployeeResource {

	
	@GET
	@Produces("application/json")
	@Path(URLConstant.GET_EMPLOYEE_BY_ID)
	public EmployeeInfo getEmp(@PathParam("employeeId") int id){
		
		LogRecord.logger.error("resource called");
		EmployeeService empSrv = new EmployeeService();
		EmployeeDAOProcess empDAOPro = new EmployeeDAOProcess();
		return empDAOPro.parseEmployeeInfoResultSet(empSrv.selectEmployeeInfo(id));
		//logger.info(id);
		//return null;
	}
}
