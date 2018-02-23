/**
 * 
 */
package com.virkade.cms.graphql;

import java.util.Date;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.virkade.cms.hibernate.dao.UserDAO;
import com.virkade.cms.model.User;

/**
 * @author SIGMON-MAIN
 *
 */
public class Mutation implements GraphQLRootResolver {
		public User createUser(String firstName,String lastName,String username, String password, String emailAddress) {
			User user = new User();
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setUserName(username);
			user.setPassword(password);
			user.setAddressId(1);
			user.setAge(30);
			user.setCanContact(true);
			user.setCreatedAt(new Date());
			user.setCreatedBy("Zac");
			user.setEmailAddress(emailAddress);
			user.setEmailVerified(false);
			user.setGender("male");
			user.setHeight(68);
			user.setIdp((float)65.3);
			user.setLastLogin(new Date());
			user.setStatusId(1);
			user.setLiabilityAgree(false);
			user.setPlayedBefore(false);
			user.setReServices(false);
			user.setTcAgree(false);
			user.setTypeId(1);
			user.setUpdatedAt(new Date());
			user.setUpdatedBy("zac");
			user.setWeight(175);
			UserDAO.pushUser(user);
			
			return user;
			
		}
}
