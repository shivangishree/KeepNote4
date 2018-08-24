package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		try {
			Category c = getCategoryById(category.getCategoryId());
		} catch (CategoryNotFoundException nnfe) {
			sessionFactory.getCurrentSession().save(category);
			sessionFactory.getCurrentSession().flush();
			return true;
		}
		return true;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		Category category;
		try {
			category = getCategoryById(categoryId);
		} catch (CategoryNotFoundException e) {
			return false;
		}
		sessionFactory.getCurrentSession().delete(category);
		sessionFactory.getCurrentSession().flush();
		return true;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		Category c;
		try {
			c = getCategoryById(category.getCategoryId());
		} catch (CategoryNotFoundException cnfe) {
			return false;
		}
		sessionFactory.getCurrentSession().update(category);
		sessionFactory.getCurrentSession().flush();
		return true;
	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Category category = sessionFactory.getCurrentSession().get(Category.class, categoryId);
		sessionFactory.getCurrentSession().flush();
		if (category == null) {
			throw new CategoryNotFoundException("category not found");
		}
		return category;
	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		String hql = "FROM Category where createdBy= :userId";
        Query query = sessionFactory.openSession().createQuery(hql).setParameter("userId", userId);
        return query.getResultList();
	}

}
