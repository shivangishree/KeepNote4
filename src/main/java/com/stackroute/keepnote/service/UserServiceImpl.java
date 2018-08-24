package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.UserDAO;
import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import java.lang.Exception;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the userDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */
     UserDAO userDao;
     
     @Autowired
     public UserServiceImpl(UserDAO userDao) { 		
 		this.userDao = userDao;
 	}
	/*
	 * This method should be used to save a new user.
	 */

	public boolean registerUser(User user) throws UserAlreadyExistException {
		if(userDao.registerUser(user)) {
			return true;
		}
		else {
			throw new UserAlreadyExistException("User already exixts");
		}
		/*User u = userDao.getUserById(user.getUserId());
        if(u!= null) {
        	throw new UserAlreadyExistException("User Already exists");
        }
        else {
        	userDao.registerUser(u);
        	return true;
        }*/
	}

	/*
	 * This method should be used to update a existing user.
	 */

	public User updateUser(User user, String userId) throws Exception {
		User u = getUserById(userId);
		if(!userDao.updateUser(user)) {
			throw  new Exception("user not found");
		}
		else {
			return user;
		}
	}

	/*
	 * This method should be used to get a user by userId.
	 */

	public User getUserById(String UserId) throws UserNotFoundException {
		User user = userDao.getUserById(UserId);
		if(user == null) {
			throw new UserNotFoundException("user not found");
		}else {
			return user;
		}

	}

	/*
	 * This method should be used to validate a user using userId and password.
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		Boolean isValidUser = userDao.validateUser(userId, password);
		if(isValidUser) {
			return true;
		}
		else {
			throw new UserNotFoundException("user not found");			
		}
	}

	/* This method should be used to delete an existing user. */
	public boolean deleteUser(String UserId) {
		if(userDao.deleteUser(UserId)) {
			return true;
		}else {
			return false;
		}

	}

}
