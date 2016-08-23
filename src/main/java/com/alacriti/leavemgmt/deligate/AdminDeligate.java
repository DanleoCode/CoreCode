package com.alacriti.leavemgmt.deligate;

import java.sql.Connection;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.AdminBOImplement;
import com.alacriti.leavemgmt.bo.ConnectionHelper;

public class AdminDeligate {

	public static Logger logger = Logger.getLogger(AdminDeligate.class);

	public static Response PostQuestionDeligate(String question) {
		int updatedRows = -1;
		Connection con = ConnectionHelper.getConnection();
		AdminBOImplement implement = new AdminBOImplement(con);
		try {
			updatedRows = implement.addQuestion(question);
			if(updatedRows == 1){
				ConnectionHelper.commitConnection(con);
				return Response.status(Status.CREATED)
						.build();
			}
		} catch (Exception e) {
			logger.error("Exception Occured : " + e.getMessage());
		} finally{
			ConnectionHelper.finalizeConnection(con);
		}
		return Response.status(Status.NOT_MODIFIED).build();
	}
}
