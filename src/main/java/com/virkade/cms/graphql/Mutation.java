/**
 * 
 */
package com.virkade.cms.graphql;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.hibernate.utilities.HibernateUtilities;
import com.virkade.cms.model.PlaySession;
import com.virkade.cms.model.User;

/**
 * @author SIGMON-MAIN
 *
 */
public class Mutation implements GraphQLRootResolver {
		public User createUser(User user, String requestor) throws Exception {
			Map<String, String> missingData = new HashMap<String, String>();
			if (user.getFirstName() == null || user.getFirstName().equalsIgnoreCase("")) {
				missingData.put("FirstName", user.getFirstName());
			}
			if (user.getLastName() == null || user.getLastName().equalsIgnoreCase("")) {
				missingData.put("LastName", user.getLastName());
			}
			if (user.getEmailAddress() == null || user.getEmailAddress().equalsIgnoreCase("")) {
				missingData.put("EmailAddress", user.getEmailAddress());
			}
			if (user.getUserName() == null || user.getUserName().equalsIgnoreCase("")) {
				missingData.put("Username", user.getUserName());
			}
			if (user.getPassword() == null || user.getPassword().equalsIgnoreCase("")) {
				missingData.put("Password", user.getPassword());
			}
			if (user.getStatusId() == 0) {
				user.setStatusId(1);
			}
			if (user.getTypeId() == 0) {
				user.setTypeId(1);
			}
			user.getAudit().setCreatedAt(new Date());
			user.getAudit().setCreatedBy(requestor);
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(requestor);
			
			if (!missingData.isEmpty()) {
				throw new Exception("Creation of user not allowed with the following missing data ["+missingData.toString()+"]");
			}
			UserDAO.pushUser(user);
			
			return user;
			
		}
		
		public User updateUser(User user, String requestor) throws Exception {
			
			User userToModify = UserDAO.getUser(user);
			if (userToModify == null) {
				throw new Exception("No user found with the userName:"+user.getUserName());
			}
			Map<String, String> missingData = new HashMap<String, String>();
			if ((user.getFirstName() == null || user.getFirstName().equalsIgnoreCase("")) && user.getFirstName().equals(userToModify.getFirstName())) {
				userToModify.setFirstName(user.getFirstName());
			}
			if ((user.getLastName() == null || user.getLastName().equalsIgnoreCase("")) && user.getLastName().equals(userToModify.getLastName())) {
				userToModify.setLastName(user.getLastName());
			}
			if (user.getEmailAddress() == null || user.getEmailAddress().equalsIgnoreCase("") && user.getEmailAddress().equals(userToModify.getEmailAddress())) {
				userToModify.setEmailAddress(user.getEmailAddress());
			}
			if (user.getUserName() == null || user.getUserName().equalsIgnoreCase("") && user.getUserName().equals(userToModify.getUserName())) {
				userToModify.setUserName(user.getUserName());
			}
			if (user.getPassword() == null || user.getPassword().equalsIgnoreCase("") && user.getPassword().equals(userToModify.getPassword())) {
				userToModify.setPassword(user.getPassword());
			}
			user.getAudit().setUpdatedAt(new Date());
			user.getAudit().setUpdatedBy(requestor);
			
			if (!missingData.isEmpty()) {
				throw new Exception("Creation of user not allowed with the following missing data ["+missingData.toString()+"]");
			}
			UserDAO.pushUser(userToModify);
			
			return userToModify;
			
		}
		
		public PlaySession createSessions(String username, Date startDate, Date endDate, long locationId) throws Exception {
			PlaySession playSession = new PlaySession();
			SessionFactory hsf = HibernateUtilities.getSessionFactory();
			Session hs = hsf.openSession();
			hs.beginTransaction();

			org.hibernate.Query query = hs.createQuery("from user where username = :username").setString("username", username);
			List<User> users = query.list();
			if (users.size() > 1) {
				throw new Exception("more than one user found for username="+username+" this should not be possible");
			}
			User user = users.get(0);
			playSession.setLocationId(locationId);
			playSession.setStartDate(startDate);
			playSession.setEndDate(endDate);
		
			hs.save(playSession);
			hs.getTransaction().commit();
			hs.close();
			return playSession;
		}
}
