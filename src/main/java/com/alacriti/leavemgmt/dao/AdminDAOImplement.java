package com.alacriti.leavemgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.ConnectionHelper;
import com.alacriti.leavemgmt.valueobject.Tables;

public class AdminDAOImplement {

	private Connection con;
	public static Logger logger = Logger.getLogger(AdminDAOImplement.class);
	
	public AdminDAOImplement() {

	}

	public AdminDAOImplement(Connection con) {
		this.con = con;
	}

	public int addQuestionDAO(String questionString) throws SQLException,
			NullPointerException, Exception {
		int updatedRows = -1;
		String sql = "INSERT INTO " + Tables.SECURITY_QUESTION_MASTER
				+ "(question_desc) values (?)";
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, questionString);
		logger.info(pStmt);
		updatedRows = pStmt.executeUpdate();
		ConnectionHelper.closePreparedStatement(pStmt);
		return updatedRows;
	}
}
