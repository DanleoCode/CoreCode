package com.alacriti.leavemgmt.deligate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.alacriti.leavemgmt.bo.EmployeeBOImplement;
import com.alacriti.leavemgmt.bo.SessionBOImplement;
import com.alacriti.leavemgmt.valueobject.EmployeeProfile;
import com.alacriti.leavemgmt.valueobject.LoginCredential;
import com.alacriti.leavemgmt.valueobject.UserSession;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class AuthDeligate {

	public static Logger logger = Logger.getLogger(AuthDeligate.class);

	public static List<EmployeeProfile> validateCredentials(
			LoginCredential tempCred, UserSession session) {
		List<EmployeeProfile> list = new ArrayList<EmployeeProfile>();
		EmployeeBOImplement employeeBOImplement = new EmployeeBOImplement();
		String loginId = tempCred.getUser();
		String passwd = tempCred.getPass();
		list = employeeBOImplement.AutherizedAccess(loginId, passwd);
		logger.info("retuened back to deligete");
		if (list.size() == 1) {
			logger.info("passed the test");
			int empId = list.get(0).getEmpId();
			short empType = list.get(0).getEmployeeType();
			Timestamp lastMOdifiedTime = new Timestamp(
					new java.util.Date().getTime());

			session.setLastMOdifiedTime(lastMOdifiedTime);
			session.setEmployeeType(empType);
			session.setEmpId(empId);

			logger.info("emp id in auth deligate  " + list.get(0).getEmpId());
			if (list.get(0).getEmpId() > 0) {
				logger.info("valid user");
				SessionBOImplement sessionBOImplement = new SessionBOImplement();
				if (getSession(empId).getEmpId() == empId) {
					logger.info("empId exist");
					sessionBOImplement.updateSessionBO(session);
				} else {
					logger.info("empId does not exist");
					sessionBOImplement.createUserSession(session,
							list.get(0));
				}
			}
		} else{
			logger.info("failed");
		}
		return list;
	}

	public static UserSession getSession(int empId) {
		logger.info("In getsession DEloigate");
		UserSession session = new UserSession();
		session.setEmpId(empId);
		SessionBOImplement sessionBOImplement = new SessionBOImplement();
		return sessionBOImplement.getSession(session);

	}
	
	public static void verifyToken(String token) throws GeneralSecurityException, IOException{
		
		
		HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
	   	JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
		String CLIENT_ID = "966604447762-njrilh0aqctrlh5dgkn4483qammp3ghd.apps.googleusercontent.com";
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
	    .setAudience(Arrays.asList(CLIENT_ID))
	    .setIssuer("accounts.google.com")
	    .build();

	GoogleIdToken idToken = verifier.verify(token);
	if (idToken != null) {
	  Payload payload = idToken.getPayload();

	  // Print user identifier
	  String userId = payload.getSubject();
	  System.out.println("User ID: " + userId);

	  // Get profile information from payload
	  String email = payload.getEmail();
	  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
	  String name = (String) payload.get("name");
	  String pictureUrl = (String) payload.get("picture");
	  String locale = (String) payload.get("locale");
	  String familyName = (String) payload.get("family_name");
	  String givenName = (String) payload.get("given_name");
	  
	  // Use or store profile information
	  logger.info("Name " + name);
	  logger.info("email " + email);
	  logger.info("email varified : " + emailVerified);
	  logger.info("pic_url " + pictureUrl);
	  logger.info("some locale " + locale);
	  logger.info("family name " + familyName);
	  logger.info("given Name " + givenName);

	} else {
	  logger.info("Invalid ID token.");
	}
	}
}
