package com.stackroute.keepnote.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.NoteService;
import com.stackroute.keepnote.service.ReminderService;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class ReminderController {

	@Autowired
	ReminderService reminderService;
	

	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement five functionalities regarding reminder. They are as
	 * following:
	 * 
	 * 1. Create a reminder 2. Delete a reminder 3. Update a reminder 2. Get all
	 * reminders by userId 3. Get a specific reminder by id.
	 * 
	 * we must also ensure that only a user who is logged in should be able to
	 * perform the functionalities mentioned above.
	 * 
	 */

	/*
	 * Autowiring should be implemented for the ReminderService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */

	public ReminderController(ReminderService reminderService) {
    this.reminderService = reminderService;
	}

	/*
	 * Define a handler method which will create a reminder by reading the
	 * Serialized reminder object from request body and save the reminder in
	 * reminder table in database. Please note that the reminderId has to be unique
	 * and the loggedIn userID should be taken as the reminderCreatedBy for the
	 * reminder. This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED - In case of successful
	 * creation of the reminder 2. 409(CONFLICT) - In case of duplicate reminder ID
	 * 3. 401(UNAUTHORIZED) - If the user trying to perform the action has not
	 * logged in.
	 * 
	 * This handler method should map to the URL "/reminder" using HTTP POST
	 * method".
	 */
	@RequestMapping(value ="/reminder",method = RequestMethod.POST)
	public ResponseEntity<?> createNote(@RequestBody Reminder reminder, HttpSession session) {
		try {
			if (session.getAttribute("loggedInUserId").equals(reminder.getReminderCreatedBy())) {
				if (reminderService.createReminder(reminder)) {
					return new ResponseEntity<String>("Created", HttpStatus.CREATED);
				} else {
					return new ResponseEntity<String>("Conflict", HttpStatus.CONFLICT);
				}

			} else {
				return new ResponseEntity<String>("user id didn't match", HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>("user id didn't match", HttpStatus.UNAUTHORIZED);
		}
	}
	/*
	 * Define a handler method which will delete a reminder from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the reminder deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the reminder with specified reminderId is
	 * not found. 3. 401(UNAUTHORIZED) - If the user trying to perform the action
	 * has not logged in.
	 * 
	 * This handler method should map to the URL "/reminder/{id}" using HTTP Delete
	 * method" where "id" should be replaced by a valid reminderId without {}
	 */
	@RequestMapping(value ="/reminder/{id}",method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteReminder(@PathVariable("id") int id, HttpSession session){
		try{
			if(session.getAttribute("loggedInUserId")!=null) {
			if(reminderService.deleteReminder(id)) {
				return new ResponseEntity<String>("Deleted", HttpStatus.OK);
			}else {
				return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
			}
			}
		}catch(Exception e){
			return new ResponseEntity<String>("Exception",HttpStatus.UNAUTHORIZED);
		}
		 return new ResponseEntity<String>("Exception",HttpStatus.UNAUTHORIZED);
	}
	/*
	 * Define a handler method which will update a specific reminder by reading the
	 * Serialized object from request body and save the updated reminder details in
	 * a reminder table in database handle ReminderNotFoundException as well. please
	 * note that the loggedIn userID should be taken as the reminderCreatedBy for
	 * the reminder. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the reminder updated
	 * successfully. 2. 404(NOT FOUND) - If the reminder with specified reminderId
	 * is not found. 3. 401(UNAUTHORIZED) - If the user trying to perform the action
	 * has not logged in.
	 * 
	 * This handler method should map to the URL "/reminder/{id}" using HTTP PUT
	 * method.
	 */
	@RequestMapping(value ="/reminder/{id}",method = RequestMethod.PUT)
	public ResponseEntity<?> updateNote(@PathVariable("id") int id,@RequestBody Reminder reminder,HttpSession session){
		//try {
			if(session.getAttribute("loggedInUserId")!= null) {
				try {
					Reminder r = reminderService.updateReminder(reminder,id);
					if(r!= null) {
					return new ResponseEntity<String>("Updated", HttpStatus.OK);
					}else {
					return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
					}
				}catch(ReminderNotFoundException rmnf) {
					return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
				}
			}else {
				return new ResponseEntity<String>("Exception",HttpStatus.UNAUTHORIZED);
			}			
		}
	/*
	 * Define a handler method which will get us the reminders by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the reminder found successfully. 2.
	 * 401(UNAUTHORIZED) -If the user trying to perform the action has not logged
	 * in.
	 * 
	 * 
	 * This handler method should map to the URL "/reminder" using HTTP GET method
	 */
	@RequestMapping(value ="/reminder",method = RequestMethod.GET)
	public ResponseEntity<?> getReminderByUserId(HttpSession session){
		try {
			String userId = (String) session.getAttribute("loggedInUserId");
			if(userId!= null) {
				List<Reminder> listOfReminder = reminderService.getAllReminderByUserId(userId);
				session.setAttribute("listNoteByUserId", listOfReminder);
				return new ResponseEntity<List<Reminder>>(listOfReminder, HttpStatus.OK);
			}else {
			return new ResponseEntity<String>("Exception",HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e) {
			return new ResponseEntity<String>("Exception",HttpStatus.UNAUTHORIZED);
		}
	}
	/*
	 * Define a handler method which will show details of a specific reminder handle
	 * ReminderNotFoundException as well. This handler method should return any one
	 * of the status messages basis on different situations: 1. 200(OK) - If the
	 * reminder found successfully. 2. 401(UNAUTHORIZED) - If the user trying to
	 * perform the action has not logged in. 3. 404(NOT FOUND) - If the reminder
	 * with specified reminderId is not found. This handler method should map to the
	 * URL "/reminder/{id}" using HTTP GET method where "id" should be replaced by a
	 * valid reminderId without {}
	 */
	@GetMapping("/reminder/{id}")
	public ResponseEntity<?> getUserById(@PathVariable() int id, HttpSession session) {
		ResponseEntity<?> responseEntity = null;
		if ((String) session.getAttribute("loggedInUserId") != null) {
			try {
				Reminder reminder = reminderService.getReminderById(id);
				if (reminder == null) {
					responseEntity = new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
				} else {
					responseEntity = new ResponseEntity<Reminder>(reminder, HttpStatus.OK);
				}
			} catch (ReminderNotFoundException e) {

				responseEntity = new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);

			}
		} else {
			responseEntity = new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}
		return responseEntity;

	}

}