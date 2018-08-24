package com.stackroute.keepnote.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.User;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	SessionFactory sessionFactory;

	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new user
	 */
	public boolean registerUser(User user) {
		if (getUserById(user.getUserId()) != null) {
			return false;
		} else {
			sessionFactory.getCurrentSession().save(user);
			sessionFactory.getCurrentSession().flush();
			return true;
		}
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		if (getUserById(user.getUserId()) == null) {
			return false;
		} else {
			sessionFactory.getCurrentSession().clear();
			sessionFactory.getCurrentSession().update(user);
			sessionFactory.getCurrentSession().flush();
			return true;
		}
	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String UserId) {
		User user = (User) sessionFactory.getCurrentSession().get(User.class, UserId);
		sessionFactory.getCurrentSession().flush();
		return user;
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, userId);
		if (user == null) {
			throw new UserNotFoundException("No user with " + userId + " id exists");
		} else {
			if (user.getUserPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		}

	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		if (getUserById(userId) == null) {
			return false;
		} else {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.delete(getUserById(userId));
			session.getTransaction().commit();
			session.close();
			return true;
		}
	}

}
