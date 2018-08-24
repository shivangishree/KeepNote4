package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
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
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	SessionFactory sessionFactory; 

	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
    try {
    Note n = getNoteById(note.getNoteId());
    }catch(NoteNotFoundException nnfe) {
    	sessionFactory.getCurrentSession().save(note);
        sessionFactory.getCurrentSession().flush();
        return true;
    }    
    return false;
	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		Note note; 
		try {
			note = getNoteById(noteId);
		} catch (NoteNotFoundException e) {
			return false;
		} 
		sessionFactory.getCurrentSession().delete(note);
		sessionFactory.getCurrentSession().flush();
		return true;
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		String hql = "FROM Note where createdBy= :userId";
        Query query = getSessionFactory().openSession().createQuery(hql).setParameter("userId", userId);
        return query.getResultList();
	}

	/*
	 * Retrieve details of a specific note
	 */
	
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note = sessionFactory.getCurrentSession().get(Note.class,noteId);
        if(note == null) {
        	throw new NoteNotFoundException("Note not found");
        }
        else {
        	return note;
        }
	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		try {
			Note n = getNoteById(note.getNoteId());
		} catch (NoteNotFoundException e) {
			return false;
		}
		sessionFactory.getCurrentSession().save(note);
        sessionFactory.getCurrentSession().flush();
        return true;
	}

}
