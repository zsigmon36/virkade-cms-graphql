/**
 * 
 */
package com.virkade.cms.graphql;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.auth.AuthData;
import com.virkade.cms.auth.AuthToken;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.User;

/**
 * @author SIGMON-MAIN
 *
 */
public class Mutation implements GraphQLRootResolver {
			
		public User createNewUser(String emailAddress, AuthData authData, String firstName, String lastName, String requestor) throws Exception {
			
			List<String> missingData = new ArrayList<String>();
			
			if (authData == null) {
				throw new Exception("Creation of user not allowed with null AuthData");
			}
			
			if (firstName == null || firstName.equalsIgnoreCase("")) {
				missingData.add("FirstName");
			}
		
			if (lastName == null || lastName.equalsIgnoreCase("")) {
				missingData.add("LastName");
			}
			if (emailAddress == null || emailAddress.equalsIgnoreCase("")) {
				missingData.add("EmailAddress");
			}
			if (authData.getUserName() == null || authData.getUserName().equalsIgnoreCase("")) {
				missingData.add("Username");
			}
			if (authData.getPassword() == null ||authData.getPassword().equalsIgnoreCase("")) {
				missingData.add("Password");
			}
			if (!missingData.isEmpty()) {
				throw new Exception("Creation of user not allowed with the following missing data ["+missingData.toString()+"]");
			}
			User user = new User(authData);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUserName(authData.getUserName());
			user.setEmailAddress(emailAddress);
			user.setPassword(authData.getPassword());
			user.setStatusId(1);
			user.setTypeId(1);
			user.getAudit().setCreatedAt(new Date());
			user.getAudit().setCreatedBy(requestor);
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(requestor);
			UserDAO.pushUser(user);
			
			return user;
			
		}
		
		public AuthToken signIn(AuthData authData) throws Exception {
			return ClientSessionTracker.clientSignIn(authData);
		}
//		
//		public PlaySession createSessions(String username, Date startDate, Date endDate, long locationId) throws Exception {
//			PlaySession playSession = new PlaySession();
//			SessionFactory hsf = HibernateUtilities.getSessionFactory();
//			Session hs = hsf.openSession();
//			hs.beginTransaction();
//
//			org.hibernate.Query query = hs.createQuery("from user where username = :username").setString("username", username);
//			List<User> users = query.list();
//			if (users.size() > 1) {
//				throw new Exception("more than one user found for username="+username+" this should not be possible");
//			}
//			User user = users.get(0);
//			playSession.setLocationId(locationId);
//			playSession.setStartDate(startDate);
//			playSession.setEndDate(endDate);
//		
//			hs.save(playSession);
//			hs.getTransaction().commit();
//			hs.close();
//			return playSession;
//		}
}
