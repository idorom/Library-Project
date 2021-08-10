package Model;

import java.io.Serializable;


import Utils.AcademicBook;
import Utils.BookSize;
import Utils.Topic;

public class Book extends LibraryItem implements Serializable{
	
	private static final long serialVersionUID = 2001L;
	private BookSize bookSize;
	private AcademicBook academicBook;
	
	
	/**
     * Full constructor.
     *
     * @param name the Item name.
     * @param id the Item id.
     * @param ReleaseDate The release date of the Book. 
     * @param author The Item's author.
     * @param BookSize.
     * AcademicBook
     */
    public Book(String name, Topic topic, Author author,BookSize size,AcademicBook academicBook) {
    	super(name,topic,author);
    	this.bookSize = size;
    	this.academicBook=academicBook;
    	
    }
    
 // partial contractor
    public Book(String name) {
    	super(name);
    }
    

    // seters and geters
    public void setBookSize(BookSize size) {
    	this.bookSize=size;
    }
    
    
    public BookSize getBookSize() {
    	return this.bookSize;
    }
    
    public void setAcadmemicBook(AcademicBook acadmemicBook) {
    	this.academicBook=acadmemicBook; 	
    }
    
    public AcademicBook getAcadmemicBook() {
    	return this.academicBook;
    	
    }
    
    @Override
    public String toString() {
    	return getName(); 
    }

}
