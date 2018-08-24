package com.stackroute.keepnote.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.Reminder;

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
public class ReminderDAOImpl implements ReminderDAO {
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
    
	@Autowired
    SessionFactory sessionFactory;
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory ;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) {
		if(reminder == null) {
			return false;
		}
		else {
			sessionFactory.getCurrentSession().save(reminder);
			sessionFactory.getCurrentSession().flush();
			return true;
		}

	}
	
	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		try {
		if(getReminderById(reminder.getReminderId())== null) {
			return false;
		}
		}
		catch(ReminderNotFoundException unfe) {
			return false;
		}
			sessionFactory.getCurrentSession().update(reminder);
			return true;
		}

	/*
	 * Remove an existing reminder
	 */
	
	public boolean deleteReminder(int reminderId) {
		Reminder reminder; 
		try {
			reminder = getReminderById(reminderId);
		} catch (ReminderNotFoundException e) {
			return false;
		} 
		sessionFactory.getCurrentSession().delete(reminder);
		sessionFactory.getCurrentSession().flush();
		return true;
	}

	/*
	 * Retrieve details of a specific reminder
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Reminder reminder = sessionFactory.getCurrentSession().get(Reminder.class,reminderId);
		if(reminder== null) {
			throw new ReminderNotFoundException("Reminder not found");
		}
		return reminder;

	}

	/*
	 * Retrieve details of all reminders by userId
	 */
	
	public List<Reminder> getAllReminderByUserId(String userId) {
		String hql = "FROM Reminder reminder";
		Query query = getSessionFactory().openSession().createQuery(hql);
		return query.getResultList();
	}

}
