package com.alacriti.leavemgmt.bo;

import java.sql.Connection;
import java.sql.SQLException;

import com.alacriti.leavemgmt.dao.AdminDAOImplement;

public class AdminBOImplement {

	private Connection con;

	public AdminBOImplement(Connection con) {
		this.con = con;
	}

	public int addQuestion(String question) throws SQLException,
			NullPointerException, Exception {
		int updatedRows = -1;
		AdminDAOImplement daoImplement = new AdminDAOImplement(con);
		updatedRows = daoImplement.addQuestionDAO(question);
		return updatedRows;
	}
}
