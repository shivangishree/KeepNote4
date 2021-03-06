package com.stackroute.keepnote.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.istack.internal.Nullable;

/*
 * The class "Note" will be acting as the data model for the Note Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
public class Note {
	/*
	 * This class should have eight fields
	 * (noteId,noteTitle,noteContent,noteStatus,createdAt,
	 * category,reminder,createdBy). Out of these eight fields, the field noteId
	 * should be primary key and auto-generated. This class should also contain the
	 * getters and setters for the fields along with the no-arg , parameterized
	 * constructor and toString method. The value of createdAt should not be
	 * accepted from the user but should be always initialized with the system date.
	 * annotate category and reminder field with @ManyToOne.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int noteId;

	@Column
	String noteTitle;

	@Column
	String noteContent;

	@Column
	String noteStatus;

	@Column
	Date NoteCreatedAt;

	@ManyToOne
	@Nullable
	Category category;

	@ManyToOne
	@Nullable
	Reminder reminder;

	@Column
	String createdBy;

	public Note() {

	}

	public Note(int noteId, String noteTitle, String noteContent, String noteStatus, Date noteCreatedAt,
			Category category, Reminder reminder, String createdBy) {
		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteStatus = noteStatus;
		NoteCreatedAt = noteCreatedAt;
		this.category = category;
		this.reminder = reminder;
		this.createdBy = createdBy;
	}

	/*public Note(int Int, String string, String string1, String string2, Date date, Category category, Reminder reminder,
			String string3) {
		noteId = Int;
		noteTitle = string;
		noteContent = string1;
		noteStatus = string2;
		NoteCreatedAt = date;
		this.category = category;
		this.reminder = reminder;
		createdBy = string3;
	}
*/
	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public String getNoteStatus() {
		return noteStatus;
	}

	public void setNoteStatus(String noteStatus) {
		this.noteStatus = noteStatus;
	}

	public Date getNoteCreatedAt() {
		return NoteCreatedAt;
	}

	public void setNoteCreatedAt(Date date) {
		this.NoteCreatedAt = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Reminder getReminder() {
		return reminder;
	}

	public void setReminder(Reminder reminder) {
		this.reminder = reminder;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/*@Override
	public boolean equals(Object o) {
		if (!(o instanceof Note)) {
			return false;
		}
		Note note = (Note) o;
		return getNoteId() == (note.getNoteId()) && getNoteTitle().equals(note.getNoteTitle())
				&& getNoteContent().equals(note.getNoteContent()) && getNoteStatus().equals(note.getNoteStatus())
				&& getCategory().equals(note.getCategory()) && getReminder().equals(note.getReminder())
				&& getCreatedBy().equals(note.getCreatedBy());
	}
*/
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", noteTitle=" + noteTitle + ", noteContent=" + noteContent + ", noteStatus="
				+ noteStatus + ", NoteCreatedAt=" + NoteCreatedAt + ", category=" + category + ", reminder=" + reminder
				+ ", createdBy=" + createdBy + "]";
	}

}
