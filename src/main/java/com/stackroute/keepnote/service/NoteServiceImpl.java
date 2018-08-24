package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.CategoryDAO;
import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.dao.UserDAO;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;

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
public class NoteServiceImpl implements NoteService {
	@Autowired
	NoteDAO noteDao;
	
	@Autowired
    ReminderDAO reminderDao;
	
	@Autowired
	CategoryDAO categoryDao;
	
	
//	public NoteServiceImpl(NoteDAO noteDao) {
//		this.noteDao = noteDao;
//	}
//
//	public NoteDAO getNoteDao() {
//		return noteDao;
//	}
//
//	public void setNoteDao(NoteDAO noteDao) {
//		this.noteDao = noteDao;
//	}
//	
//	public ReminderDAO getReminderDao() {
//		return reminderDao;
//	}
//
//	public void setReminderDao(ReminderDAO reminderDao) {
//		this.reminderDao = reminderDao;
//	}
//
//	public CategoryDAO getCategoryDao() {
//		return categoryDao;
//	}
//
//	public void setCategoryDao(CategoryDAO categoryDao) {
//		this.categoryDao = categoryDao;
//	}
//	
	/*
	 * Autowiring should be implemented for the NoteDAO,CategoryDAO,ReminderDAO.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException {
		 Reminder reminder=note.getReminder();
		 Category category=note.getCategory();
		 if(reminder!=null) {
			 try {
			 reminderDao.getReminderById(reminder.getReminderId());
			 }catch(ReminderNotFoundException r) {
				 throw new ReminderNotFoundException("reminder is not there");
			 }
		 }
		 if(category!=null) {
			 try {
				 categoryDao.getCategoryById(category.getCategoryId());
			 }catch(CategoryNotFoundException r) {
				 throw new CategoryNotFoundException("category is not there");
			 }
		 }
		return noteDao.createNote(note);
	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(int noteId) {
		if (noteDao.deleteNote(noteId)) {
			return true;
		} else {
			return false;
		}

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		List<Note> list = noteDao.getAllNotesByUserId(userId);
		return list;
	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note = noteDao.getNoteById(noteId);
		if (note == null) {
        throw new NoteNotFoundException("Note not found");
		}
		return note;
	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException {
		if (note != null) {
			try {
				noteDao.getNoteById(note.getNoteId());
			} catch (NoteNotFoundException r) {
				throw new NoteNotFoundException("NoteNotFoundException");
			}
		}
		Reminder reminder=note.getReminder();
		 Category category=note.getCategory();
		 if(reminder!=null) {
			 try {
			 reminderDao.getReminderById(reminder.getReminderId());
			 }catch(ReminderNotFoundException r) {
				 throw new ReminderNotFoundException("reminder is not there");
			 }
		 }
		 if(category!=null) {
			 try {
				 categoryDao.getCategoryById(category.getCategoryId());
			 }catch(CategoryNotFoundException r) {
				 throw new CategoryNotFoundException("category is not there");
			 }
		 }
		noteDao.UpdateNote(note);
		return note;
	}

}
